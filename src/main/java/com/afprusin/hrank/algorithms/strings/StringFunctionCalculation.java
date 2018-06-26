package com.afprusin.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringFunctionCalculation {

	private class SubstringGroup {
		List<Integer> indexes;
		private int startingOffset;

		SubstringGroup(List<Integer> indexes) {
			this.indexes = indexes;
			this.startingOffset = 0;
		}

		SubstringGroup(List<Integer> indexes, int startingOffset) {
			this.indexes = indexes;
			this.startingOffset = startingOffset;
		}

		List<Integer> getIndexes() {
			return indexes;
		}

		int getStartingOffset() {
			return startingOffset;
		}
	}

	public static void main(String[] args) {
		new StringFunctionCalculation().performSolution();
	}

	public void performSolution() {
		String input = getInput();
		ArrayDeque<SubstringGroup> nodes = new ArrayDeque<>(getInitialGroups(input));

		int bestScore = 0;
		while( ! nodes.isEmpty()) {
			SubstringGroup currentGroup = nodes.pop();

			int currentOffset = currentGroup.getStartingOffset();
			while(isGroupValidAtOffset(currentGroup, input, currentOffset)) {
				currentOffset++;
			}

			final int groupScore = getGroupScore(currentGroup, currentOffset);
			if(groupScore > bestScore) {
				bestScore = groupScore;
			}

			List<SubstringGroup> splitNodes = getGroupsSeparatedByCharacterAtOffset(currentGroup, input, currentOffset);
			for(SubstringGroup splitNode : splitNodes) {
				if(getMaximumPotentialGroupScore(splitNode, input.length()) > bestScore) {
					nodes.push(splitNode);
				}
			}
		}

		System.out.println(bestScore);
	}

	private String getInput() {
		String inputLine;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			inputLine = reader.readLine().trim();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return inputLine;
	}

	private List<SubstringGroup> getInitialGroups(String toIndex) {
		return IntStream.range(0, toIndex.length() - 1)
				.boxed()
				.collect(Collectors.groupingBy(toIndex::charAt)).values().stream()
				.map(SubstringGroup::new)
				.collect(Collectors.toList());
	}

	private int getGroupScore(SubstringGroup group, int substringLength) {
		return group.getIndexes().size() * substringLength;
	}

	// The group's (or a subset group's) maximum score, assuming it could match the remaining text
	private int getMaximumPotentialGroupScore(SubstringGroup group, int searchTextLength) {
		final List<Integer> indexes = group.getIndexes();
		int bestScore = 0;
		for(int i = 0; i < indexes.size(); i++) {
			final int maxLength = searchTextLength - indexes.get(i);
			final int comboScore = (i + 1) * maxLength;
			if(comboScore > bestScore) {
				bestScore = comboScore;
			}
		}
		return bestScore;
	}

	private boolean isGroupValidAtOffset(SubstringGroup group, String textSearch, int indexOffset) {
		return areIndexesValid(group, textSearch.length(), indexOffset)  &&
				areCharactersEqual(group, textSearch, indexOffset);

	}

	private boolean areIndexesValid(SubstringGroup group, int textLength, int indexOffset) {
		boolean isValid = true;
		for(Integer index : group.getIndexes()) {
			if(index + indexOffset >= textLength) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}

	private boolean areCharactersEqual(SubstringGroup group, String textSearch, int indexOffset) {
		boolean isEqual = true;
		char firstChar = textSearch.charAt(group.getIndexes().get(0) + indexOffset);
		for(Integer index : group.getIndexes()) {
			if(textSearch.charAt(index + indexOffset) != firstChar) {
				isEqual = false;
				break;
			}
		}
		return isEqual;
	}

	private List<SubstringGroup> getGroupsSeparatedByCharacterAtOffset(
			SubstringGroup group, String searchText, int indexOffset) {
		return group.getIndexes().stream()
				.filter(index -> (index + indexOffset) < searchText.length())
				.collect(Collectors.groupingBy(index ->
						searchText.charAt(index + indexOffset))).values().stream()
				.map(indexes -> new SubstringGroup(indexes, indexOffset))
				.collect(Collectors.toList());
	}
}
