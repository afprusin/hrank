package com.ohumbrella.hrank.algorithms.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CircularArrayRotation {

	List<Integer> circularArrayRotation(int rotation, int[] values, int[] queryIndexes) {
		rotation = rotation % values.length;
		rotation = rotation * -1;
		while(rotation < 0) {
			rotation += values.length;
		}

		final int indexOffset = rotation;

		return Arrays.stream(queryIndexes)
				.map(index -> (index + indexOffset) % values.length)
				.map(index -> values[index])
				.boxed()
				.collect(Collectors.toList());
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new CircularArrayRotation().performSolution();
	}

	public void performSolution() {
		String[] nkq = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nkq[0]);
		int k = Integer.parseInt(nkq[1]);
		int q = Integer.parseInt(nkq[2]);

		int[] a = new int[n];

		String[] aItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int aItem = Integer.parseInt(aItems[i]);
			a[i] = aItem;
		}

		int[] m = new int[q];

		for (int i = 0; i < q; i++) {
			int mItem = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
			m[i] = mItem;
		}

		circularArrayRotation(k, a, m).forEach(System.out::println);

		scanner.close();
	}
}
