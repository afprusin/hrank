package com.afprusin.hrank.algorithms.implementation;

import java.math.BigInteger;
import java.util.Scanner;

public class ExtraLongFactorials {

	// Complete the extraLongFactorials function below.
	private BigInteger getFactorial(int toGet) {
		BigInteger result = BigInteger.ONE;

		for(int i = 2; i <= toGet; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}

		return result;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new ExtraLongFactorials().performSolution();
	}

	private void performSolution() {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		System.out.println(getFactorial(n).toString());

		scanner.close();
	}
}
