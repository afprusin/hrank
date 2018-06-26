package com.afprusin.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BirthdayChocolate {

	// Complete the solve function below.
	private int solve(int[] chocolateSquares, int desiredSumOfSquares, int desiredSquaresCount) {
		int validCombinations = 0;

		if(chocolateSquares.length >= desiredSquaresCount) {
			int upperIterationBounds = chocolateSquares.length - (desiredSquaresCount - 1);
			for(int i = 0; i < upperIterationBounds; i++) {
				int currentSumOfSquares = 0;
				for(int j = i; j < i + desiredSquaresCount; j++) {
					currentSumOfSquares += chocolateSquares[j];
				}
				if(currentSumOfSquares == desiredSumOfSquares) {
					validCombinations++;
				}
			}
		}
		return validCombinations;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new BirthdayChocolate().solveProblem();
	}

	public void solveProblem() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] s = new int[n];

		String[] sItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int sItem = Integer.parseInt(sItems[i]);
			s[i] = sItem;
		}

		String[] dm = scanner.nextLine().split(" ");

		int d = Integer.parseInt(dm[0]);

		int m = Integer.parseInt(dm[1]);

		int result = solve(s, d, m);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
