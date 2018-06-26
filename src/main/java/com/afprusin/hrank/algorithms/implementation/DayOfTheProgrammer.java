package com.afprusin.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class DayOfTheProgrammer {

	// TODO: I HATE DATE PROBLEMS, ESPECIALLY STRING DATE PROBLEMS

	// Complete the solve function below.
	static String solve(int year) {
		LocalDateTime time = LocalDateTime.of(year, 1, 1, 1, 1);
		time = time.plusDays(264);
		//time.atZone(ZoneId.)


		return "";
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		int year = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		String result = solve(year);

		bufferedWriter.write(result);
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
