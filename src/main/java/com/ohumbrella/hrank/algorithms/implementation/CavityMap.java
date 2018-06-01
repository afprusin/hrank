package com.ohumbrella.hrank.algorithms.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CavityMap {

	private String cavityMap(String[] grid) {

		List<List<Integer>> depths = Arrays.stream(grid)
				.map(this::convertStringToIntegerList)
				.collect(Collectors.toList());

		StringBuilder result = new StringBuilder();
		for(int i = 0; i < depths.size(); i++) {
			for(int j = 0; j < depths.get(0).size(); j++) {
				if(i > 0  &&  i < depths.size() - 1  &&
						j > 0  &&  j < depths.get(0).size() - 1  &&
						isCoordinateCavity(depths, i, j)) {
					result.append("X");
				}
				else {
					result.append(depths.get(i).get(j));
				}
			}
			result.append("\n");
		}

		return result.toString();
	}

	private List<Integer> convertStringToIntegerList(String toConvert) {
		return toConvert.chars()
				.map(charVal -> charVal - 48)
				.boxed()
				.collect(Collectors.toList());
	}

	private boolean isCoordinateCavity(List<List<Integer>> depths, int x, int y) {
		final int coordinateHeight = depths.get(x).get(y);
		return (depths.get(x - 1).get(y) < coordinateHeight  &&
				depths.get(x + 1).get(y) < coordinateHeight  &&
				depths.get(x).get(y - 1) < coordinateHeight  &&
				depths.get(x).get(y + 1) < coordinateHeight);
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new CavityMap().performSolution();
	}

	public void performSolution() {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		String[] grid = new String[n];

		for (int i = 0; i < n; i++) {
			String gridItem = scanner.nextLine();
			grid[i] = gridItem;
		}

		System.out.println(cavityMap(grid));

		scanner.close();
	}
}

