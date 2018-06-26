package com.afprusin.hrank.algorithms.implementation;

import java.io.IOException;
import java.util.Scanner;

public class LisasWorkbook {

	// Complete the workbook function below.
	private int workbook(int chapters, int problemsPerPage, int[] problemsPerChapter) {

		int currentPage = 1;
		int qualifyingProblems = 0;

		for(int problemCount : problemsPerChapter) {
			for(int problemChunk = 0; problemChunk < problemCount; problemChunk += problemsPerPage) {
				final int lastProblemInChunk = problemChunk + problemsPerPage;
				if(problemChunk < currentPage  &&  currentPage <= lastProblemInChunk  &&
						currentPage <= problemCount) {
					qualifyingProblems++;
				}
				currentPage++;
			}
		}

		return qualifyingProblems;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new LisasWorkbook().performSolution();
	}

	private void performSolution() {
		String[] nk = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nk[0]);

		int k = Integer.parseInt(nk[1]);

		int[] arr = new int[n];

		String[] arrItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int arrItem = Integer.parseInt(arrItems[i]);
			arr[i] = arrItem;
		}

		int result = workbook(n, k, arr);

		System.out.println(result);

		scanner.close();
	}
}

