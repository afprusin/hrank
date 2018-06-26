package com.afprusin.hrank.algorithms.implementation;

import java.util.Scanner;

public class BeautifulTriplets {

	// Complete the beautifulTriplets function below.
	private int beautifulTriplets(int spacing, int[] values) {
		int beautifulTriplets = 0;

		int firstIndex = 0;
		int secondIndex = 1;

		for(int thirdIndex = 2; thirdIndex < values.length; thirdIndex++) {
			while(secondIndex < thirdIndex  &&
					values[thirdIndex] - values[secondIndex] > spacing) {
				secondIndex++;
			}
			while(firstIndex < secondIndex  &&
					values[secondIndex] - values[firstIndex] > spacing) {
				firstIndex++;
			}


			if (spacing == values[thirdIndex] - values[secondIndex]  &&
					spacing == values[secondIndex] - values[firstIndex]) {
				beautifulTriplets++;
			}
		}
		return beautifulTriplets;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new BeautifulTriplets().performSolution();
	}

	private void performSolution() {
		String[] nd = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nd[0]);

		int d = Integer.parseInt(nd[1]);

		int[] arr = new int[n];

		String[] arrItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int arrItem = Integer.parseInt(arrItems[i]);
			arr[i] = arrItem;
		}

		int result = beautifulTriplets(d, arr);

		System.out.println(result);

		scanner.close();
	}
}

