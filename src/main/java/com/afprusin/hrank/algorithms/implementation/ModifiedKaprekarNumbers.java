package com.afprusin.hrank.algorithms.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModifiedKaprekarNumbers {

	// Complete the kaprekarNumbers function below.
	private List<Integer> kaprekarNumbers(int fromInclusive, int toInclusive) {
		List<Integer> numbersInRange = new ArrayList<>();

		// Specific case for the only kaprekar number with one digit when squared
		if(fromInclusive == 1) {
			numbersInRange.add(1);
		}
		// Skip numbers below the next known kaprekar number to simplify the splitting logic
		if(fromInclusive < 9) {
			fromInclusive = 9;
		}
		if(toInclusive >= 9) {
			for(long currentNumber = fromInclusive; currentNumber <= toInclusive; currentNumber++) {
				final long currentSquared = currentNumber * currentNumber;
				if(hasEqualSumSubstrings(currentSquared, currentNumber)) {
					numbersInRange.add((int)currentNumber);
				}
			}
		}

		return numbersInRange;
	}

	private boolean hasEqualSumSubstrings(long toSubstring, long expectedValue) {
		String numberAsText = String.valueOf(toSubstring);

		final int leftHalfLength = numberAsText.length() - String.valueOf(expectedValue).length();

		final String leftHalf = numberAsText.substring(0, leftHalfLength);
		final String rightHalf = numberAsText.substring(leftHalfLength, numberAsText.length());

		return Integer.valueOf(leftHalf) + Integer.valueOf(rightHalf) == expectedValue;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new ModifiedKaprekarNumbers().performSolution();
	}

	private void performSolution() {
		int p = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int q = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		List<Integer> result = kaprekarNumbers(p, q);

		if(result.isEmpty()) {
			System.out.println("INVALID RANGE");
		}
		else {
			for (int i = 0; i < result.size(); i++) {
				if (i > 0) {
					System.out.print(" ");
				}
				System.out.print(result.get(i));
			}
		}

		scanner.close();
	}
}

