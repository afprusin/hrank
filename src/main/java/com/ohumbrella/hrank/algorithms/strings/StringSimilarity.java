package com.ohumbrella.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StringSimilarity {

	public static void main(String[] args) {
		new StringSimilarity().performSolution();
	}

	public void performSolution() {
		List<String> testCases = getInput();

		for(String testCase : testCases) {
			System.out.println(getStringSimilarityScore(testCase));
		}
	}

	private List<String> getInput() {
		String inputLine;
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			int expectedTestcases = Integer.valueOf(reader.readLine().trim());
			while((inputLine = reader.readLine()) != null) {
				lines.add(inputLine.trim());
			}

			if(lines.size() != expectedTestcases) {
				throw new IllegalStateException(
						"Input did not contain the expected number of test cases (" +
						expectedTestcases + ")");
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return lines;
	}

	private long getStringSimilarityScore(String toScore) {
		// Converted to array to meet runtime requirements
		char[] textAsArray = toScore.toCharArray();
		long totalScore = 0;

		final int textLength = textAsArray.length;
		final int nonhomogeneousIndex = getFirstNonhomogeneousIndex(textAsArray);
		final long assumedScore = getAssumedScore(nonhomogeneousIndex);
		totalScore+= assumedScore;

		for(int index = 0; index < textLength; index++) {
			// Skip character matches already accounted for with the homogeneous prefix scan
			final int startingOffset =
					index < nonhomogeneousIndex ? (nonhomogeneousIndex - index) : 0;
			for(int offset = startingOffset; offset + index < textLength; offset++) {
				if(textAsArray[offset] == textAsArray[index + offset]) {
					totalScore++;
				}
				else {
					break;
				}
			}
		}

		return totalScore;
	}

	private long getAssumedScore(int index) {
		// The total score from an N-long homogeneous prefix matches the triangular number sequence
		return (((long)index) * ((long)index + (long)1)) / (long)2;
	}

	private int getFirstNonhomogeneousIndex(char[] toScore) {
		char firstCharacter = toScore[0];
		final int lengthToScan = toScore.length - 1;
		int index = 0;

		while(index < lengthToScan  &&  toScore[index] == firstCharacter) {
			index++;
		}

		return index;
	}
}
