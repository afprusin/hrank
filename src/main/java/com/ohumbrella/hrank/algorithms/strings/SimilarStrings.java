package com.ohumbrella.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimilarStrings {

	private class TestCases {
		private int[] toSearch;
		private List<int[]> subString;

		TestCases(int[] toSearch, List<int[]> subString) {
			this.toSearch = toSearch;
			this.subString = subString;
		}

		int[] getToSearch() {
			return toSearch;
		}

		List<int[]> getSubstrings() {
			return subString;
		}
	}

	private class CharacterEquivalents {
		private static final char UNSET_NULL = '\u0000';
		private int[] equivalents = new int[10];
		private int[] reverseEquivalents = new int[10];

		boolean compareOrEstablishEquivalent(int indexI, int indexJ) {
			boolean result;

			if(equivalents[indexI] == UNSET_NULL  && reverseEquivalents[indexJ] == UNSET_NULL) {
				equivalents[indexI] = indexJ;
				reverseEquivalents[indexJ] = indexI;
				result = true;
				//System.out.println("Set:" + i + "," + j);
			}
			else {
				result = equivalents[indexI] == indexJ;
				//System.out.println(i + ":" + j + "?" + result);
			}

			return result;
		}

		void reset() {
			Arrays.fill(equivalents, UNSET_NULL);
			Arrays.fill(reverseEquivalents, UNSET_NULL);
		}
	}

	public static void main(String[] args) {
		new SimilarStrings().performSolution();
	}

	public void performSolution() {
		TestCases testCases = getInput();

		for(int[] substring : testCases.getSubstrings()) {
			System.out.println(getCountOfSimilarStrings(substring, testCases.getToSearch()));
		}
//		System.out.println(getCountOfSimilarStrings(testCases.getSubstrings().get(2), testCases.getToSearch()));
	}

	private TestCases getInput() {
		String inputLine;
		List<int[]> substrings = new ArrayList<>();
		int[] toSearch;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/SimilarStrings11")))) {
			//try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			final String[] metadata = reader.readLine().trim().split(" ");
			toSearch = convertInputLineToIndexArray(reader.readLine().trim());

			int expectedTestcases = Integer.valueOf(metadata[1]);
			while((inputLine = reader.readLine()) != null) {
				String[] substringIndexes = inputLine.trim().split(" ");
				final int lowIndex = Integer.valueOf(substringIndexes[0]) - 1;
				final int highIndex = Integer.valueOf(substringIndexes[1]);
				substrings.add(Arrays.copyOfRange(toSearch, lowIndex, highIndex));
			}

			if(substrings.size() != expectedTestcases) {
				throw new IllegalStateException(
						"Input did not contain the expected number of test cases (" +
								expectedTestcases + ")");
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return new TestCases(toSearch, substrings);
	}

	private int[] convertInputLineToIndexArray(String toConvert) {
		return toConvert.chars()
				.map(this::getBaseZeroShiftedCharacterIndexValue)
				.toArray();
	}

	private int getBaseZeroShiftedCharacterIndexValue(int toShift) {
		return toShift - 97;
	}

	private long getCountOfSimilarStrings(int[] substring, int[] toSearch) {
		CharacterEquivalents equivalents = new CharacterEquivalents();

		long matchCount = 0;

		for(int i = 0; i <= toSearch.length - substring.length; i++) {
			if(checkSubstringSimilarityAgainstRange(substring, toSearch, i, equivalents)) {
				matchCount++;
			}
		}

		return matchCount;
	}

	private boolean checkSubstringSimilarityAgainstRange(
			int[] subString, int[] toSearch, int baseSearchIndex, CharacterEquivalents equivalents) {

		equivalents.reset();
		boolean result = true;

		for(int i = 0; i < subString.length; i++) {
			if( ! equivalents.compareOrEstablishEquivalent(
					subString[i], toSearch[i + baseSearchIndex])) {
				result = false;
				break;
			}
		}

		//System.out.println(subString + " : " + toSearch.substring(baseSearchIndex, subString.length() + baseSearchIndex) + " : " + result);

		return result;
	}


}
