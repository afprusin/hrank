package com.afprusin.hrank.algorithms.implementation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PickingNumbers {

	// Complete the pickingNumbers function below.
	static int pickingNumbers(List<Integer> items) {
		Collections.sort(items);

		int largestSquence = 0;

		for(int i = 0; i < items.size(); i++) {
			int currentSequence = 1;
			for(int j = i + 1; j < items.size(); j++) {
				if(Math.abs(items.get(i) - items.get(j)) > 1) {
					break;
				}
				currentSequence++;
			}
			if(currentSequence > largestSquence) {
				largestSquence = currentSequence;
			}
		}

		return largestSquence;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		List<Integer> items = Arrays.stream(scanner.nextLine().split(" "))
				.map(item -> Integer.valueOf(item))
				.collect(Collectors.toList());
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int result = pickingNumbers(items);

		System.out.println(result);

		scanner.close();
	}
}
