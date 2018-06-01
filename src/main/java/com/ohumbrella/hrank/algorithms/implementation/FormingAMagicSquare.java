package com.ohumbrella.hrank.algorithms.implementation;

import java.util.*;

public class FormingAMagicSquare {
	private List<int[][]> validMagicSquares = new ArrayList<>();

	public FormingAMagicSquare() {
		validMagicSquares.add(new int[][]{{8,1,6}, {3,5,7}, {4,9,2}});
		validMagicSquares.add(new int[][]{{8,3,4}, {1,5,9}, {6,7,2}});
		validMagicSquares.add(new int[][]{{6,1,8}, {7,5,3}, {2,9,4}});
		validMagicSquares.add(new int[][]{{4,3,8}, {9,5,1}, {2,7,6}});
		validMagicSquares.add(new int[][]{{4,9,2}, {3,5,7}, {8,1,6}});
		validMagicSquares.add(new int[][]{{6,7,2}, {1,5,9}, {8,3,4}});
		validMagicSquares.add(new int[][]{{2,9,4}, {7,5,3}, {6,1,8}});
		validMagicSquares.add(new int[][]{{2,7,6}, {9,5,1}, {4,3,8}});
	}

	private int getLowestCostMagicSquare(int[][] inputSquare) {
		int bestCost = Integer.MAX_VALUE;

		for(int[][] validSquare : validMagicSquares) {
			int cost = 0;
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					cost += Math.abs(inputSquare[i][j] - validSquare[i][j]);
				}
			}
			if(cost < bestCost) {
				bestCost = cost;
			}
		}

		return bestCost;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new FormingAMagicSquare().performSolution();
	}

	public void performSolution() {
		int[][] s = new int[3][3];

		for (int i = 0; i < 3; i++) {
			String[] sRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			for (int j = 0; j < 3; j++) {
				int sItem = Integer.parseInt(sRowItems[j]);
				s[i][j] = sItem;
			}
		}

		final int result = getLowestCostMagicSquare(s);

		System.out.println(result);

		scanner.close();
	}
}

