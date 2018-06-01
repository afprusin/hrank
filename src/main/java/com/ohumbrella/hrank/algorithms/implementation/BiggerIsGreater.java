package com.ohumbrella.hrank.algorithms.implementation;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class BiggerIsGreater {

	private String biggerIsGreater(String w) {

		char[] text = w.toCharArray();

		boolean solutionWasFound = false;

		// Starting with the second-to-last character, work backwards, looking for a valid
		//	character to increase
		int largestPreviousValue = text[text.length];
		for(int i = text.length - 2; i >= 0; i--) {
			if(text[i] < largestPreviousValue) {
				Optional<Integer> potentialSwap = getBestSwapIndexIfExists(i, text);
				if(potentialSwap.isPresent()) {
					final char temp = text[i];
					text[i] = text[potentialSwap.get()];
					text[potentialSwap.get()] = temp;
					Arrays.sort(text, i + 1, text.length);

					solutionWasFound = true;
					break;
				}
			}
			if(text[i] > largestPreviousValue) {
				largestPreviousValue = text[i];
			}

		}

		if(solutionWasFound) {
			return new String(text);
		}
		else {
			return "no answer";
		}
	}

	private Optional<Integer> getBestSwapIndexIfExists(int index, char[] text) {
		Optional<Integer> bestIndex = Optional.empty();

		for(int i = index + 1; i < text.length; i++) {
			// If a larger value (than at index) is found, and is smaller than the current-best
			if(text[i] > text[index]  &&
					( ! bestIndex.isPresent()  ||  text[bestIndex.get()] > text[i])) {
				bestIndex = Optional.of(i);
			}
		}

		return bestIndex;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new BiggerIsGreater().performSolution();
	}

	public void performSolution() {
		int T = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int TItr = 0; TItr < T; TItr++) {
			String w = scanner.nextLine();

			String result = biggerIsGreater(w);

			System.out.println(result);
		}

		scanner.close();
	}
}

