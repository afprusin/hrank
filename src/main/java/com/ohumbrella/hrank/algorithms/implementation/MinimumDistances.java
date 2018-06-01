package com.ohumbrella.hrank.algorithms.implementation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinimumDistances {
	private int minimumDistances(int[] a) {
		return IntStream.range(0, a.length)
				.boxed()
				.collect(Collectors.groupingBy(index -> a[index]))
				.values().stream()
				.map(this::convertIndexesToDistances)
				.flatMap(Collection::stream)
				.min(Integer::compareTo)
				// -1 is specified as a the 'no solution' return value
				.orElse(-1);
	}

	private List<Integer> convertIndexesToDistances(List<Integer> toConvert) {
		Collections.sort(toConvert);

		List<Integer> indexDistances = new ArrayList<>();
		for(int i = 0; i < toConvert.size() - 1; i++) {
			indexDistances.add(Math.abs(toConvert.get(i + 1) - toConvert.get(i)));
		}

		return indexDistances;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new MinimumDistances().performSolution();
	}

	public void performSolution() {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] a = new int[n];

		String[] aItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int aItem = Integer.parseInt(aItems[i]);
			a[i] = aItem;
		}

		int result = minimumDistances(a);

		System.out.println(result);
		scanner.close();
	}
}
