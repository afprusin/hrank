package com.ohumbrella.hrank.algorithms.implementation;

import java.io.IOException;
import java.util.Scanner;

public class HalloweenSale {

	// Complete the howManyGames function below.
	private int howManyGames(int initialPrice, int discount, int minimumPrice, int dollarsInWallet) {

		int currentPrice = initialPrice;
		int gamesPurchased = 0;

		do {
			dollarsInWallet -= currentPrice;

			if(dollarsInWallet >= 0) {
				gamesPurchased++;
			}

			if(currentPrice > minimumPrice) {
				currentPrice -= discount;
				if(currentPrice < minimumPrice) {
					currentPrice = minimumPrice;
				}
			}
		}
		while(dollarsInWallet > 0);

		return gamesPurchased;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new HalloweenSale().performSolution();
	}

	public void performSolution() {
		String[] pdms = scanner.nextLine().split(" ");

		int p = Integer.parseInt(pdms[0]);

		int d = Integer.parseInt(pdms[1]);

		int m = Integer.parseInt(pdms[2]);

		int s = Integer.parseInt(pdms[3]);

		int answer = howManyGames(p, d, m, s);

		System.out.println(answer);
		scanner.close();
	}
}
