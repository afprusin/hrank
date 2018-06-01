package com.ohumbrella.hrank.algorithms.implementation;

import java.io.IOException;
import java.util.Scanner;

public class ChocolateFeast {

	// Complete the chocolateFeast function below.
	private int chocolateFeast(int money, int chocolatePrice, int wrappers) {

		int barsPurchased = Math.floorDiv(money, chocolatePrice);
		int availableWrappers = barsPurchased;

		while(availableWrappers >= wrappers) {
			final int additionalBarsPurchased = Math.floorDiv(availableWrappers, wrappers);
			barsPurchased += additionalBarsPurchased;
			availableWrappers = (availableWrappers % wrappers) + additionalBarsPurchased;
		}

		return barsPurchased;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new ChocolateFeast().performSolution();
	}

	private void performSolution() {
		int t = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int tItr = 0; tItr < t; tItr++) {
			String[] ncm = scanner.nextLine().split(" ");

			int n = Integer.parseInt(ncm[0]);

			int c = Integer.parseInt(ncm[1]);

			int m = Integer.parseInt(ncm[2]);

			int result = chocolateFeast(n, c, m);

			System.out.println(result);
		}

		scanner.close();
	}
}
