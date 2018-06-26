package com.afprusin.hrank.algorithms.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindDigits {

	private int findDigits(int toSearch) {
		List<Integer> potentialDivisors = getIndividualDigits(toSearch);

		return (int)potentialDivisors.stream()
				.filter(divisor -> divisor != 0)
				.filter(divisor -> toSearch % divisor == 0)
				.count();
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new FindDigits().performSolutions();
	}

	private List<Integer> getIndividualDigits(int value) {
		List<Integer> digits = new ArrayList<>();
		while(value > 0) {
			digits.add(value % 10);
			value = Math.floorDiv(value, 10);
		}
		return digits;
	}

	private void performSolutions() {
		int t = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int tItr = 0; tItr < t; tItr++) {
			int n = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int result = findDigits(n);

			System.out.println(result);
		}

		scanner.close();
	}
}

