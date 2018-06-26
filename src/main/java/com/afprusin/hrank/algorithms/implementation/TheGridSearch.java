package com.afprusin.hrank.algorithms.implementation;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TheGridSearch {

	// Complete the gridSearch function below.
	private String gridSearch(String[] grid, String[] pattern) {
		Pattern firstLine = Pattern.compile(pattern[0]);
		boolean matches = false;

		for(int i = 0; i < grid.length; i++) {
			Matcher lineMatcher = firstLine.matcher(grid[i]);
			int currentCharacter = 0;
			while(lineMatcher.find(currentCharacter)) {
				currentCharacter = lineMatcher.start() + 1;
				final int j = lineMatcher.start();
				if(isPatternAtPosition(grid, pattern, i, j)) {
					matches = true;
					break;
				}
			}
		}

		return matches ? "YES" : "NO";
	}

	private boolean isPatternAtPosition(String[] grid, String[] pattern, int x, int y) {
		boolean matches = true;
		for(int i = 0; i < pattern.length  &&  i + x < grid.length; i++) {
			for(int j = 0; j < pattern[0].length()  &&  j + y < grid[0].length(); j++) {
				if(pattern[i].charAt(j) != grid[i + x].charAt(j + y)) {
					matches = false;
					break;
				}
			}
		}
		return matches;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new TheGridSearch().performSolution();
	}

	public void performSolution() {
		int t = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int tItr = 0; tItr < t; tItr++) {
			String[] RC = scanner.nextLine().split(" ");

			int R = Integer.parseInt(RC[0]);

			int C = Integer.parseInt(RC[1]);

			String[] G = new String[R];

			for (int i = 0; i < R; i++) {
				String GItem = scanner.nextLine();
				G[i] = GItem;
			}

			String[] rc = scanner.nextLine().split(" ");

			int r = Integer.parseInt(rc[0]);

			int c = Integer.parseInt(rc[1]);

			String[] P = new String[r];

			for (int i = 0; i < r; i++) {
				String PItem = scanner.nextLine();
				P[i] = PItem;
			}

			System.out.println(gridSearch(G, P));
		}

		scanner.close();
	}
}

