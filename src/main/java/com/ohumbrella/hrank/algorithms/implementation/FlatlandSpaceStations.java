package com.ohumbrella.hrank.algorithms.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FlatlandSpaceStations {

	// Complete the flatlandSpaceStations function below.
	private int flatlandSpaceStations(int cities, int[] stationCities) {
		return toMidwayDistances(stationCities, cities).stream()
				.max(Integer::compare)
				.orElseThrow(() -> new IllegalArgumentException(
						"Unable to determine a maximum distance"));
	}

	private List<Integer> toMidwayDistances(int[] stationCities, int cities) {
		Arrays.sort(stationCities);
		List<Integer> midwayDistances = new ArrayList<>();

		// Account for cities at the beginning of the chain
		if(stationCities[0] != 0) {
			midwayDistances.add(stationCities[0]);
		}
		// Account for cities at the end of the chain
		if(stationCities[stationCities.length - 1] != cities - 1) {
			midwayDistances.add((cities - 1) - stationCities[stationCities.length - 1]);
		}

		for(int i = 0; i < stationCities.length - 1; i++) {
			final int midwayDistance = Math.floorDiv(
					stationCities[i + 1] - stationCities[i], 2);
			midwayDistances.add(midwayDistance);
		}

		// For edgecases where there is only one city (and one station city)
		if(midwayDistances.size() == 0) {
			midwayDistances.add(0);
		}

		return midwayDistances;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new FlatlandSpaceStations().performSolution();
	}

	private void performSolution() {
		String[] nm = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nm[0]);

		int m = Integer.parseInt(nm[1]);

		int[] c = new int[m];

		String[] cItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < m; i++) {
			int cItem = Integer.parseInt(cItems[i]);
			c[i] = cItem;
		}

		int result = flatlandSpaceStations(n, c);

		System.out.println(result);

		scanner.close();
	}
}
