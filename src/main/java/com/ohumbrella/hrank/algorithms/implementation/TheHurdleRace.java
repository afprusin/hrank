package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Scanner;

public class TheHurdleRace {

	// Complete the hurdleRace function below.
	private int hurdleRace(int baseJumpCapability, int[] hurdleHeights) {
		OptionalInt maximumHeight = Arrays.stream(hurdleHeights)
				.max();
		int potionsNeeded = 0;
		if(maximumHeight.isPresent()) {
			potionsNeeded = maximumHeight.getAsInt() - baseJumpCapability;
			if(potionsNeeded < 0) {
				potionsNeeded = 0;
			}
		}

		return potionsNeeded;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new TheHurdleRace().solveProblem();
	}

	public void solveProblem() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		String[] nk = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nk[0]);

		int k = Integer.parseInt(nk[1]);

		int[] height = new int[n];

		String[] heightItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int heightItem = Integer.parseInt(heightItems[i]);
			height[i] = heightItem;
		}

		int result = hurdleRace(k, height);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}

