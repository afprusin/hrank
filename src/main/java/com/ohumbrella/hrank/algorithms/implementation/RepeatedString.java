package com.ohumbrella.hrank.algorithms.implementation;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class RepeatedString {

	// Complete the repeatedString function below.
	static long repeatedString(String s, long n) {
		final long partialCharacters = n % s.length();
		final long completeStrings = Math.floorDiv(n, s.length());

		long partialCharacterCount = 0;
		long totalCharacterCount = 0;

		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == 'a') {
				totalCharacterCount++;
				if(i < partialCharacters) {
					partialCharacterCount++;
				}
			}
		}

		return (totalCharacterCount * completeStrings) + partialCharacterCount;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new RepeatedString().solveProblem();
	}

	public void solveProblem() {
		String s = scanner.nextLine();

		long n = scanner.nextLong();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		long result = repeatedString(s, n);

		System.out.println(String.valueOf(result));

		scanner.close();
	}
}

