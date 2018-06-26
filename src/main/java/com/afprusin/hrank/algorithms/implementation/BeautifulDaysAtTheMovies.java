package com.afprusin.hrank.algorithms.implementation;

import java.util.Scanner;
import java.util.stream.IntStream;

public class BeautifulDaysAtTheMovies {

	// Complete the beautifulDays function below.
	private int beautifulDays(int firstDay, int lastDay, int divisibleBy) {
		return (int)IntStream.range(firstDay, lastDay + 1)
				.filter(i -> isBeautifulDay(i, divisibleBy))
				.count();
	}

	private boolean isBeautifulDay(int day, int divisibleBy) {
		final int reversedDay = reverseValue(day);

		return (Math.abs(reversedDay - day) % divisibleBy) == 0;
	}

	private int reverseValue(int toReverse) {
		int reversed = 0;
		while(toReverse > 0) {
			reversed = reversed * 10 + (toReverse % 10);
			toReverse /= 10;
		}

		return reversed;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new BeautifulDaysAtTheMovies().performSolution();
	}

	public void performSolution() {
		String[] ijk = scanner.nextLine().split(" ");

		int i = Integer.parseInt(ijk[0]);
		int j = Integer.parseInt(ijk[1]);
		int k = Integer.parseInt(ijk[2]);

		int result = beautifulDays(i, j, k);

		System.out.println(result);

		scanner.close();
	}
}
