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
		private String toSearch;
		private List<String> subString;

		TestCases(String toSearch, List<String> subString) {
			this.toSearch = toSearch;
			this.subString = subString;
		}

		String getToSearch() {
			return toSearch;
		}

		List<String> getSubstrings() {
			return subString;
		}
	}

	private class CharacterEquivalents {
		private static final char UNSET_NULL = '\u0000';
		private char[] equivalents = new char[10];
		private char[] reverseEquivalents = new char[10];

		boolean compareOrEstablishEquivalent(char i, char j) {
			final int indexI = getCharactersIndex(i);
			final int indexJ = getCharactersIndex(j);
			boolean result;

			if(equivalents[indexI] == UNSET_NULL  && reverseEquivalents[indexJ] == UNSET_NULL) {
				equivalents[indexI] = j;
				reverseEquivalents[indexJ] = i;
				result = true;
				//System.out.println("Set:" + i + "," + j);
			}
			else {
				result = equivalents[indexI] == j;
				//System.out.println(i + ":" + j + "?" + result);
			}

			return result;
		}

		void reset() {
			Arrays.fill(equivalents, UNSET_NULL);
			Arrays.fill(reverseEquivalents, UNSET_NULL);
		}

		private int getCharactersIndex(char toIndex) {
			return toIndex - 97;
		}
	}

	public static void main(String[] args) {
		new SimilarStrings().performSolution();
	}

	public void performSolution() {
		TestCases testCases = getInput();

		for(String substring : testCases.getSubstrings()) {
			System.out.println(getCountOfSimilarStrings(substring, testCases.getToSearch()));
		}
//		System.out.println(getCountOfSimilarStrings(testCases.getSubstrings().get(2), testCases.getToSearch()));
	}

	private TestCases getInput() {
		String inputLine;
		List<String> substrings = new ArrayList<>();
		String toSearch;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/SimilarStrings11")))) {
			//try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			final String[] metadata = reader.readLine().trim().split(" ");
			toSearch = reader.readLine().trim();

			int expectedTestcases = Integer.valueOf(metadata[1]);
			while((inputLine = reader.readLine()) != null) {
				String[] substringIndexes = inputLine.trim().split(" ");
				final int lowIndex = Integer.valueOf(substringIndexes[0]) - 1;
				final int highIndex = Integer.valueOf(substringIndexes[1]);
				substrings.add(toSearch.substring(lowIndex, highIndex));
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

	private long getCountOfSimilarStrings(String substring, String toSearch) {
		CharacterEquivalents equivalents = new CharacterEquivalents();

		long matchCount = 0;

		for(int i = 0; i <= toSearch.length() - substring.length(); i++) {
			if(checkSubstringSimilarityAgainstRange(substring, toSearch, i, equivalents)) {
				matchCount++;
			}
		}

		return matchCount;
	}

	private boolean checkSubstringSimilarityAgainstRange(
			String subString, String toSearch, int baseSearchIndex, CharacterEquivalents equivalents) {

		equivalents.reset();
		boolean result = true;

		for(int i = 0; i < subString.length(); i++) {
			if( ! equivalents.compareOrEstablishEquivalent(
					subString.charAt(i), toSearch.charAt(i + baseSearchIndex))) {
				result = false;
				break;
			}
		}

		//System.out.println(subString + " : " + toSearch.substring(baseSearchIndex, subString.length() + baseSearchIndex) + " : " + result);

		return result;
	}


}
