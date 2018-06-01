package com.ohumbrella.hrank.algorithms.implementation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SequenceEquation {

	// Complete the permutationEquation function below.
	private List<Integer> permutationEquation(int[] values) {

		Map<Integer, Integer> indexesByValue = IntStream.range(0, values.length)
				.boxed()
				.collect(Collectors.toMap(index -> index, index -> values[index]));

		return IntStream.range(1, values.length + 1)
				.boxed()
				.map(value -> indexesByValue.get(indexesByValue.get(value) + 1) + 1)
				.collect(Collectors.toList());
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new SequenceEquation().performSolution();
	}

	public void performSolution() {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] p = new int[n];

		String[] pItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int pItem = Integer.parseInt(pItems[i]);
			p[i] = pItem;
		}

		permutationEquation(p).forEach(System.out::println);

		scanner.close();
	}
}
