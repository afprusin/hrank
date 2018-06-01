package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClimbingTheLeaderBoard {

	// Complete the climbingLeaderboard function below.
	private List<Integer> climbingLeaderboard(int[] scores, int[] aliceScores) {
		List<Integer> sortedCondensedScores = Arrays.stream(scores)
				.boxed()
				.distinct()
				.sorted()
				.collect(Collectors.toList());

		List<Integer> ranks = new ArrayList<>();

		Iterator<Integer> boardScores = sortedCondensedScores.iterator();
		int currentBoardScore = boardScores.next();
		int currentPosition = sortedCondensedScores.size();

		for (final int currentAliceScore : aliceScores) {
			while (boardScores.hasNext() && currentAliceScore > currentBoardScore) {
				currentBoardScore = boardScores.next();
				currentPosition--;
			}

			if (currentAliceScore < currentBoardScore) {
				ranks.add(currentPosition + 1);
			}
			else {
				ranks.add(currentPosition);
			}
		}
		return ranks;
	}

	//private static final Scanner scanner = new Scanner(System.in);
	private static final Scanner scanner = new Scanner(ClimbingTheLeaderBoard.class.getResourceAsStream("/ClimbingTheLeaderboard00"));

	public static void main(String[] args) throws IOException {
		new ClimbingTheLeaderBoard().solveProblem();
	}

	public void solveProblem() throws IOException {
		int scoresCount = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] scores = new int[scoresCount];

		String[] scoresItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < scoresCount; i++) {
			int scoresItem = Integer.parseInt(scoresItems[i]);
			scores[i] = scoresItem;
		}

		int aliceCount = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] alice = new int[aliceCount];

		String[] aliceItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < aliceCount; i++) {
			int aliceItem = Integer.parseInt(aliceItems[i]);
			alice[i] = aliceItem;
		}

		List<Integer> results = climbingLeaderboard(scores, alice);

		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i));
		}

		scanner.close();
	}
}
