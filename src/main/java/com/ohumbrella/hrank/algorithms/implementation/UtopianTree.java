package com.ohumbrella.hrank.algorithms.implementation;

import java.util.Scanner;

public class UtopianTree {

	// Complete the utopianTree function below.
	private int utopianTree(int n) {
		boolean isSpring = true;
		int currentHeight = 1;

		for(int i = 0; i < n; i++) {
			if(isSpring) {
				currentHeight *= 2;
			}
			else {
				currentHeight++;
			}
			isSpring = !isSpring;
		}

		return currentHeight;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new UtopianTree().solveProblem();
	}

	private void solveProblem() {
		int t = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int tItr = 0; tItr < t; tItr++) {
			int n = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int result = utopianTree(n);

			System.out.println(result);
		}

		scanner.close();
	}
}
