package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Encryption {

	// Complete the encryption function below.
	static String encryption(String s) {
		final long dimensions =
				Math.round(Math.floor(Math.sqrt((double)s.length())));
		final long columns =
				(dimensions * dimensions) >= s.length() ? dimensions : dimensions + 1;

		StringBuilder encodedString = new StringBuilder();
		for(int i = 0; i < columns; i++) {
			if(encodedString.length() > 0) {
				encodedString.append(" ");
			}
			int index = i;
			while(index < s.length()) {
				encodedString.append(s.charAt(index));
				index += columns;
			}
		}
		return encodedString.toString();
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		String s = scanner.nextLine();

		String result = encryption(s);

		bufferedWriter.write(result);
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}

