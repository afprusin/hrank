package com.ohumbrella.hrank.algorithms.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LarrysArray {

	private boolean isArraySortable(List<Integer> toCheck) {
		return getInversions(toCheck) % 2 == 0;
	}

	private int getInversions(List<Integer> toCount) {
		int inversions = 0;
		for(int i = 0; i < toCount.size(); i++) {
			for(int j = i + 1; j < toCount.size(); j++) {
				if(toCount.get(j) < toCount.get(i)) {
					inversions++;
				}
			}
		}
		return inversions;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new LarrysArray().performSolution();
	}

	public void performSolution() {
		int t = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int tItr = 0; tItr < t; tItr++) {
			int n = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			List<Integer> toCheck = Arrays.stream(
					scanner.nextLine().split(" "))
					.map(Integer::parseInt)
					.collect(Collectors.toList());

			if(isArraySortable(toCheck)) {
				System.out.println("YES");
			}
			else {
				System.out.println("NO");
			}
		}
		scanner.close();
	}
}

