package com.afprusin.hrank.algorithms.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ManasaAndStones {
	private List<Integer> stones(int n, int a, int b) {
		final List<Integer> stones = new ArrayList<>();

		final int steps = n - 1;
		for(int i = 0; i <= steps; i++) {
			stones.add((steps - i) * a + i * b);
		}

		return stones.stream()
				.distinct()
				.sorted()
				.collect(Collectors.toList());
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new ManasaAndStones().performSolution();
	}

	public void performSolution() {
		int T = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int TItr = 0; TItr < T; TItr++) {
			int n = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int a = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int b = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			List<Integer> result = stones(n, a, b);

			System.out.println(String.join(" ", result.stream()
					.map(String::valueOf)
					.collect(Collectors.toList())));
		}

		scanner.close();
	}
}
