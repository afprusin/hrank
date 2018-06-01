package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class DivisibleSumPairs {

	// Complete the divisibleSumPairs function below.
	private int divisibleSumPairs(int divisibleBy, int[] toCheck) {
		int validPairs = 0;
		if(toCheck.length > 1) {
			for (int i = 1; i < toCheck.length; i++) {
				for (int j = 0; j < i; j++) {
					if ((toCheck[i] + toCheck[j]) % divisibleBy == 0) {
						validPairs++;
					}
				}
			}
		}
		return validPairs;
	}

	private static final Scanner scanner = new Scanner(DivisibleSumPairs.class.getResourceAsStream("/DivisibleSumPairs16"));
	//private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new DivisibleSumPairs().solveProblem();
	}

	public void solveProblem() throws IOException {
		String[] nk = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nk[0]);

		int k = Integer.parseInt(nk[1]);

		int[] ar = new int[n];

		String[] arItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int arItem = Integer.parseInt(arItems[i]);
			ar[i] = arItem;
		}

		int result = divisibleSumPairs(k, ar);

		System.out.println(String.valueOf(result));

		scanner.close();
	}
}

