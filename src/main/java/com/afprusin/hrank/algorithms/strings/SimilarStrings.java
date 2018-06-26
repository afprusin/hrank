package com.afprusin.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SimilarStrings {

	private class TestCases {
		private int[] toSearch;
		private List<ArrayRangePair> subString;

		TestCases(int[] toSearch, List<ArrayRangePair> subString) {
			this.toSearch = toSearch;
			this.subString = subString;
		}

		int[] getToSearch() {
			return toSearch;
		}

		List<ArrayRangePair> getSubstrings() {
			return subString;
		}
	}

	private class ArrayRangePair {
		int lowIndex;
		int highIndex;

		ArrayRangePair(int lowIndex, int highIndex) {

			this.lowIndex = lowIndex;
			this.highIndex = highIndex;
		}

		int getLowIndex() {
			return lowIndex;
		}

		int getHighIndex() {
			return highIndex;
		}
	}

	private class SubstringHash {
		private int encounters;
		private int hash;

		public SubstringHash(int encounters, int hash) {
			this.encounters = encounters;
			this.hash = hash;
		}

		public int getEncounters() {
			return encounters;
		}

		public int getHash() {
			return hash;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			SubstringHash that = (SubstringHash) o;
			return encounters == that.encounters &&
					hash == that.hash;
		}

		@Override
		public int hashCode() {
			return Objects.hash(encounters, hash);
		}
	}

	private class CharacterEquivalents {
		private static final int UNSET = -1;
		private int[] equivalents = new int[10];
		private int[] reverseEquivalents = new int[10];
		private int[] blank = new int[10];

		CharacterEquivalents() {
			Arrays.fill(blank, UNSET);
			reset();
		}

		boolean areEquivalent(int indexI, int indexJ) {
			boolean result;

			if(equivalents[indexI] == UNSET  && reverseEquivalents[indexJ] == UNSET) {
				equivalents[indexI] = indexJ;
				reverseEquivalents[indexJ] = indexI;
				result = true;
			}
			else {
				result = equivalents[indexI] == indexJ;
			}

			return result;
		}

		void reset() {
			System.arraycopy(blank, 0, equivalents, 0, 10);
			System.arraycopy(blank, 0, reverseEquivalents, 0, 10);
		}
	}

	private static final int HASHING_CHARACTER_REQUIREMENT = 4;

	public static void main(String[] args) {
		long start = System.nanoTime();
		new SimilarStrings().performSolution();
		long end = System.nanoTime();
		System.out.println((end - start) / (double)1000000000 + " seconds");
	}

	public void performSolution() {
		TestCases testCases = getInput();
		Map<Integer, List<Integer>> hashedIndexes = getIndexesGroupedByHash(testCases.toSearch);

		for(ArrayRangePair substring : testCases.getSubstrings()) {
			System.out.println(getCountOfSimilarStrings(substring, testCases.getToSearch(), hashedIndexes));
		}
	}

	private TestCases getInput() {
		String inputLine;
		List<ArrayRangePair> substrings = new ArrayList<>();
		int[] toSearch;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			final String[] metadata = reader.readLine().trim().split(" ");
			toSearch = convertInputLineToIndexArray(reader.readLine().trim());

			int expectedTestcases = Integer.valueOf(metadata[1]);
			while((inputLine = reader.readLine()) != null) {
				String[] substringIndexes = inputLine.trim().split(" ");
				final int lowIndex = Integer.valueOf(substringIndexes[0]) - 1;
				final int highIndex = Integer.valueOf(substringIndexes[1]);
				substrings.add(new ArrayRangePair(lowIndex, highIndex));
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

	private Map<Integer, List<Integer>> getIndexesGroupedByHash(int[] toIndex) {
		Map<Integer, List<Integer>> indexesByHash = new HashMap<>();

		for(int i = 0; i < toIndex.length; i++) {
			final int baseValue = toIndex[i];
			int encounters = 0;
			int j = i + 1;
			int lastIndex = i;
			int currentHash = 0;
			while(j < toIndex.length) {
				if(toIndex[j] == baseValue) {
					encounters++;
					currentHash = 31 * currentHash + (j - lastIndex);

					indexesByHash.computeIfAbsent(
							Objects.hash(encounters, currentHash),
							key -> new ArrayList<>()).add(i);

					lastIndex = j;
				}
				j++;

				if(encounters == HASHING_CHARACTER_REQUIREMENT) {
					break;
				}
			}
		}

		return indexesByHash;
	}

	private Optional<SubstringHash> getSubstringHash(int[] toHash, int startIndexInclusive, int endIndexExclusive) {
		final int baseValue = toHash[startIndexInclusive];
		Optional<SubstringHash> result = Optional.empty();

		int encounters = 0;
		int lastIndex = startIndexInclusive;
		int i = startIndexInclusive + 1;
		int currentHash = 0;
		while(i < endIndexExclusive) {
			if(toHash[i] == baseValue) {
				encounters++;
				currentHash = 31 * currentHash + (i - lastIndex);
				lastIndex = i;
			}
			i++;
			if(encounters >= HASHING_CHARACTER_REQUIREMENT) {
				break;
			}
		}

		if(encounters > 0) {
			result = Optional.of(new SubstringHash(encounters, currentHash));
		}

		return result;
	}

	private long getCountOfSimilarStrings(ArrayRangePair substring, int[] toSearch, Map<Integer, List<Integer>> indexesByHash) {
		CharacterEquivalents equivalents = new CharacterEquivalents();
		final int substringLength = substring.getHighIndex() - substring.getLowIndex();
		final Optional<SubstringHash> substringHash =
				getSubstringHash(toSearch, substring.getLowIndex(), substring.getHighIndex());

		long matchCount = 0;
		final int lastValidIndex = toSearch.length - substringLength;

		if(substringHash.isPresent()) {
			List<Integer> indexesToSearch = indexesByHash.get(substringHash.hashCode());
			if(indexesToSearch == null) {
				matchCount = 1;
			}
			else {
				for (Integer startingIndex : indexesToSearch) {
					if (startingIndex > lastValidIndex) {
						continue;
					}
					if (checkSubstringSimilarityAgainstRange(
							toSearch, substring.getLowIndex(), startingIndex, substringLength, equivalents)) {
						matchCount++;
					}
				}
			}
		}
		else {
			for (int i = 0; i <= lastValidIndex; i++) {
				if (checkSubstringSimilarityAgainstRange(
						toSearch, substring.getLowIndex(), i, substringLength, equivalents)) {
					matchCount++;
				}
			}
		}

		return matchCount;
	}

	private boolean checkSubstringSimilarityAgainstRange(
			int[] toSearch, int substringIndex, int toSearchIndex, int length, CharacterEquivalents equivalents) {
		equivalents.reset();

		for(int i = 0; i < length; i++) {
			if( ! equivalents.areEquivalent(toSearch[substringIndex], toSearch[toSearchIndex])) {
				return false;
			}
			substringIndex++;
			toSearchIndex++;
		}
		return true;
	}
}
