package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BetweenTwoSets {

	int getTotalX(List<Integer> aItems, List<Integer> bItems) {
		Collections.sort(aItems);
		Collections.sort(bItems);

		final Integer highestA = aItems.get(aItems.size() - 1);
		final Integer lowestB = bItems.get(0);

		int totalFactors = 0;

		for(int i = highestA; i <= lowestB; i+=highestA) {
			if(areAllFactorsOf(aItems, i)  &&  isItemFactorForAll(bItems, i)) {
				totalFactors++;
			}
		}

		return totalFactors;
	}

	private boolean areAllFactorsOf(List<Integer> potentialFactors, Integer toCheck) {
		for(Integer currentItem : potentialFactors) {
			if(toCheck % currentItem != 0) {
				return false;
			}
		}
		return true;
	}

	private boolean isItemFactorForAll(List<Integer> toCheck, Integer potentialFactor) {
		for(Integer currentItem : toCheck) {
			if(currentItem % potentialFactor != 0) {
				return false;
			}
		}
		return true;
	}

	private static final Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new BetweenTwoSets().solveProblem();
	}

	public void solveProblem() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		String[] nm = scan.nextLine().split(" ");
		//int n = Integer.parseInt(nm[0].trim());
		//int m = Integer.parseInt(nm[1].trim());

		List<Integer> aItems = Arrays.stream(scan.nextLine().split(" "))
				.map((Integer::parseInt))
				.collect(Collectors.toList());
		List<Integer> bItems = Arrays.stream(scan.nextLine().split(" "))
				.map((Integer::parseInt))
				.collect(Collectors.toList());

		int total = getTotalX(aItems, bItems);

		bw.write(String.valueOf(total));
		bw.newLine();

		bw.close();
	}
}
