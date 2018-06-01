package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JumpingOnTheClouds {

	// Complete the jumpingOnClouds function below.
	static int jumpingOnClouds(int[] c) {

		int jumps = 0;
		int currentIndex = 0;
		while(currentIndex < (c.length - 1)) {
			if(c[currentIndex] == 1) {
				currentIndex++;
			}
			else {
				currentIndex += 2;
			}
			jumps++;
		}

		return jumps;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new JumpingOnTheClouds().solveProblem();
	}

	public void solveProblem() {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] c = new int[n];

		String[] cItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int cItem = Integer.parseInt(cItems[i]);
			c[i] = cItem;
		}

		int result = jumpingOnClouds(c);

		System.out.println(result);

		scanner.close();
	}
}

