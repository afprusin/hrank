package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TaumAndBday {

	// Complete the taumBday function below.
	static long taumBday(int b, int w, int bc, int wc, int z) {

		final long optimumBlackCost = bc <= (wc + z) ? bc : (wc + z);
		final long optimumWhiteCost = wc <= (bc + z) ? wc : (bc + z);

		return (b * optimumBlackCost + w * optimumWhiteCost);
	}

	//private static final Scanner scanner = new Scanner(System.in);
	private static final Scanner scanner = new Scanner(TaumAndBday.class.getResourceAsStream("/TaumAndBday10"));

	public static void main(String[] args) {
		int t = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int tItr = 0; tItr < t; tItr++) {
			String[] bw = scanner.nextLine().split(" ");

			int b = Integer.parseInt(bw[0]);

			int w = Integer.parseInt(bw[1]);

			String[] bcWcz = scanner.nextLine().split(" ");

			int bc = Integer.parseInt(bcWcz[0]);

			int wc = Integer.parseInt(bcWcz[1]);

			int z = Integer.parseInt(bcWcz[2]);

			long result = taumBday(b, w, bc, wc, z);

			System.out.println(result);
		}

		scanner.close();
	}
}

