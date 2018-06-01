package com.ohumbrella.hrank.algorithms.implementation;

import java.io.IOException;
import java.util.Scanner;

public class NonDivisibleSubset {

	// Complete the nonDivisibleSubset function below.
	private int nonDivisibleSubset(int notDivisibleBy, int[] values) {
		int[] remainderCountsByRemainder = new int[notDivisibleBy];

		// Count the number of each remainder value
		for(int currentValue : values) {
			remainderCountsByRemainder[currentValue % notDivisibleBy]++;
		}

		int divisibleSubsetSize = 0;

		// Only one zero-remainder element can be used, as two add to a zero remainder
		if(remainderCountsByRemainder[0] > 0) {
			divisibleSubsetSize++;
		}

		boolean isEven = (notDivisibleBy % 2 == 0);
		// Only one (not-divisible-by / 2) value may be used for the same reason
		if(isEven  &&  remainderCountsByRemainder[notDivisibleBy / 2] > 0) {
			divisibleSubsetSize++;
		}

		// Skip the compare for remainder (not-divisible-by / 2) in even numbers
		int midpoint;
		if(isEven) {
			midpoint = Math.floorDiv(notDivisibleBy, 2) - 1;
		}
		else {
			midpoint = Math.floorDiv(notDivisibleBy, 2);
		}

		// For pairs of remainders that sum to a zero-remainder, take the largest of the two
		for(int i = 1; i <= midpoint; i++) {
			final int j = notDivisibleBy - i;
			if(remainderCountsByRemainder[i] >= remainderCountsByRemainder[j]) {
				divisibleSubsetSize += remainderCountsByRemainder[i];
			}
			else {
				divisibleSubsetSize += remainderCountsByRemainder[j];
			}
		}

		return divisibleSubsetSize;
	}

	//private static final Scanner scanner = new Scanner(System.in);
	private static final Scanner scanner = new Scanner(NonDivisibleSubset.class.getResourceAsStream("/NonDivisibleSubset00"));

	public static void main(String[] args) throws IOException {
		new NonDivisibleSubset().solveProblem();
	}

	public void solveProblem() {
		String[] nk = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nk[0]);

		int k = Integer.parseInt(nk[1]);

		int[] S = new int[n];

		String[] SItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int SItem = Integer.parseInt(SItems[i]);
			S[i] = SItem;
		}

		int result = nonDivisibleSubset(k, S);

		System.out.println(result);

		scanner.close();
	}
}
