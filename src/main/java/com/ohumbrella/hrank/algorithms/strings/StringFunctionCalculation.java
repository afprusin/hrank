package com.ohumbrella.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringFunctionCalculation {

	// Representative of the last character in a traversal for a given group;
	private class LexicalNode {
		List<Integer> indexes;
		private int startingOffset;

		public LexicalNode(List<Integer> indexes) {
			this.indexes = indexes;
			this.startingOffset = 0;
		}

		public LexicalNode(List<Integer> indexes, int startingOffset) {
			this.indexes = indexes;
			this.startingOffset = startingOffset;
		}

		public boolean isValidAtOffset(String textSearch, int indexOffset) {
			return areIndexesValid(textSearch.length(), indexOffset)  &&
					areCharactersEqual(textSearch, indexOffset);

		}

		private boolean areIndexesValid(int textLength, int indexOffset) {
			boolean isValid = true;
			for(Integer index : indexes) {
				if(index + indexOffset >= textLength) {
					isValid = false;
					break;
				}
			}
			return isValid;
		}

		private boolean areCharactersEqual(String textSearch, int indexOffset) {
			boolean isEqual = true;
			char firstChar = textSearch.charAt(indexes.get(0) + indexOffset);
			for(Integer index : indexes) {
				if(textSearch.charAt(index + indexOffset) != firstChar) {
					isEqual = false;
					break;
				}
			}
			return isEqual;
		}

		public List<LexicalNode> getNodeSplitAtOffset(String searchText, int indexOffset) {
			return indexes.stream()
					.filter(index -> index + indexOffset < searchText.length())
					.collect(Collectors.groupingBy(index -> searchText.charAt(index + indexOffset))).values().stream()
					.map(indexes -> new LexicalNode(indexes, indexOffset))
					.collect(Collectors.toList());
		}

		// Determine the potential maximum score of this group, and determine if it can exceed the current best
		// TODO: Verify this assumption that this computation isn't particularly burdensome
		public int getMaximumPotentialGroupScore(int searchTextLength) {
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

		public int getStartingOffset() {
			return startingOffset;
		}
	}

	public static void main(String[] args) {
		new StringFunctionCalculation().performSolution();
	}

	//TODO: Pretty this up
	public void performSolution() {
		String input = getInput();
		ArrayDeque<LexicalNode> nodes = new ArrayDeque<>(getInitialNodes(input));

		int bestScore = 0;
		while( ! nodes.isEmpty()) {
			//System.out.println(nodes.size());
			LexicalNode currentNode = nodes.pop();

			int currentOffset = currentNode.getStartingOffset();
			//System.out.println(currentOffset);
			while(currentNode.isValidAtOffset(input, currentOffset)) {
				currentOffset++;
			}
			final int currentScore = (currentOffset) * currentNode.indexes.size();
			if(currentScore > bestScore) {
				bestScore = currentScore;
			}

			//System.out.println(currentOffset);

			List<LexicalNode> splitNodes = currentNode.getNodeSplitAtOffset(input, currentOffset);
			for(LexicalNode splitNode : splitNodes) {
				if(splitNode.getMaximumPotentialGroupScore(input.length()) > bestScore) {
					nodes.push(splitNode);
				}
			}
		}

		System.out.println(bestScore);
	}

	private String getInput() {
		String inputLine;
		//try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
		try(BufferedReader reader = new BufferedReader((new InputStreamReader(this.getClass().getResourceAsStream("/StringFunctionCalculation10"))))) {
			inputLine = reader.readLine().trim();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return inputLine;
	}

	private List<LexicalNode> getInitialNodes(String toIndex) {
		return IntStream.range(0, toIndex.length() - 1)
				.boxed()
				.collect(Collectors.groupingBy(toIndex::charAt)).values().stream()
				.map(LexicalNode::new)
				.collect(Collectors.toList());
	}
}
