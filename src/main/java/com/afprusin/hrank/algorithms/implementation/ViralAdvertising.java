package com.afprusin.hrank.algorithms.implementation;

import java.util.Scanner;

public class ViralAdvertising {

	private int viralAdvertising(int advertisingDays) {
		final int initialRecipients = 5;
		final int recipientsPerLike = 2;
		final int recipientsPerShare = 3;

		int currentRecipients = initialRecipients;
		int totalLikes = 0;

		for(int i = 0; i < advertisingDays; i++) {
			final int todaysLikes = Math.floorDiv(currentRecipients, recipientsPerLike);
			totalLikes += todaysLikes;
			currentRecipients = todaysLikes * recipientsPerShare;
		}

		return totalLikes;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new ViralAdvertising().performSolution();
	}

	public void performSolution() {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int result = viralAdvertising(n);

		System.out.println(result);

		scanner.close();
	}
}
