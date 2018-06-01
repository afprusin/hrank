package com.ohumbrella.hrank.algorithms.implementation;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FairRations {

	private Optional<Integer> getFairRationsIfPossible(int[] loavesPerPerson) {
		Optional<Integer> minimumLoavesToDistribute = Optional.empty();

		List<Integer> oddlyLoavedCitizenIndexes = IntStream
				.range(0, loavesPerPerson.length)
				.filter(index -> loavesPerPerson[index] % 2 == 1)
				.boxed()
				.sorted()
				.collect(Collectors.toList());

		if(oddlyLoavedCitizenIndexes.size() % 2 == 0) {
			int minimumLoaves = 0;
			// Determine loaves to distribute via pair-distances
			for(int i = 0; i < oddlyLoavedCitizenIndexes.size() - 1; i += 2) {
				minimumLoaves += getLoavesNeededForRange(
						oddlyLoavedCitizenIndexes.get(i), oddlyLoavedCitizenIndexes.get(i + 1));
			}

			minimumLoavesToDistribute = Optional.of(minimumLoaves);
		}

		return minimumLoavesToDistribute;
	}

	private int getLoavesNeededForRange(int low, int high) {
		return (high - low) * 2;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new FairRations().performSolution();
	}

	private void performSolution() {
		int N = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] B = new int[N];

		String[] BItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < N; i++) {
			int BItem = Integer.parseInt(BItems[i]);
			B[i] = BItem;
		}

		Optional<Integer> result = getFairRationsIfPossible(B);

		if(result.isPresent()) {
			System.out.println(result.get());
		}
		else {
			System.out.println("NO");
		}

		scanner.close();
	}
}

