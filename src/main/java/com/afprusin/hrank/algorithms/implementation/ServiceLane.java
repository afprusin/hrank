package com.afprusin.hrank.algorithms.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceLane {

	// Complete the serviceLane function below.
	private List<Integer> serviceLane(int[] widths, int[][] cases) {
		List<Integer> results = new ArrayList<>();

		for (int[] currentCase : cases) {
			final int entryPoint = currentCase[0];
			final int exitPoint = currentCase[1];

			int minimumWidth = Integer.MAX_VALUE;
			for (int laneSegment = entryPoint; laneSegment <= exitPoint; laneSegment++) {
				if (widths[laneSegment] < minimumWidth) {
					minimumWidth = widths[laneSegment];
				}
			}
			results.add(minimumWidth);
		}

		return results;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new ServiceLane().performSolution();
	}

	public void performSolution() {
		String[] nt = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nt[0]);

		int t = Integer.parseInt(nt[1]);

		int[] width = new int[n];

		String[] widthItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int widthItem = Integer.parseInt(widthItems[i]);
			width[i] = widthItem;
		}

		int[][] cases = new int[t][2];

		for (int i = 0; i < t; i++) {
			String[] casesRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			for (int j = 0; j < 2; j++) {
				int casesItem = Integer.parseInt(casesRowItems[j]);
				cases[i][j] = casesItem;
			}
		}

		List<Integer> results = serviceLane(width, cases);

		results.forEach(System.out::println);

		scanner.close();
	}
}
