package com.afprusin.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BreakingTheRecords {

	private class ResultPair {
		private int highScoresBroken;
		private int lowScoresBroken;

		public ResultPair(int highScoresBroken, int lowScoresBroken) {
			this.highScoresBroken = highScoresBroken;
			this.lowScoresBroken = lowScoresBroken;
		}

		public int getHighScoresBroken() {
			return highScoresBroken;
		}

		public int getLowScoresBroken() {
			return lowScoresBroken;
		}
	}

	// Complete the breakingRecords function below.
	public ResultPair breakingRecords(int[] score) {
		int highScoreBreaks = 0;
		int lowScoreBreaks = 0;

		if(score.length > 0) {
			int currentHigh = score[0];
			int currentLow = score[0];

			for (int aScore : score) {
				if (aScore > currentHigh) {
					currentHigh = aScore;
					highScoreBreaks++;
				}
				if (aScore < currentLow) {
					currentLow = aScore;
					lowScoreBreaks++;
				}
			}
		}
		return new ResultPair(highScoreBreaks, lowScoreBreaks);
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new BreakingTheRecords().performSolution();
	}

	public void performSolution() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] score = new int[n];

		String[] scoreItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int scoreItem = Integer.parseInt(scoreItems[i]);
			score[i] = scoreItem;
		}

		ResultPair result = breakingRecords(score);

		bufferedWriter.write(result.highScoresBroken + " " + result.lowScoresBroken);

		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}

