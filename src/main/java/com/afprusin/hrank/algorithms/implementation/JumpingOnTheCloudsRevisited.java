package com.afprusin.hrank.algorithms.implementation;

import java.util.Scanner;

public class JumpingOnTheCloudsRevisited {

	// Complete the jumpingOnClouds function below.
	static int jumpingOnClouds(int[] c, int k) {

		int currentPosition = 0;
		int currentEnergy = 100;

		do {
			currentPosition = (currentPosition + k) % c.length;

			if(c[currentPosition] == 1) {
				currentEnergy -= 3;
			}
			else {
				currentEnergy--;
			}
		}
		while(currentPosition != 0  &&  currentEnergy > 0);

		if(currentEnergy < 0) {
			currentEnergy = 0;
		}

		return currentEnergy;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		String[] nk = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nk[0]);

		int k = Integer.parseInt(nk[1]);

		int[] c = new int[n];

		String[] cItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int cItem = Integer.parseInt(cItems[i]);
			c[i] = cItem;
		}

		System.out.println(jumpingOnClouds(c, k));

		scanner.close();
	}
}

