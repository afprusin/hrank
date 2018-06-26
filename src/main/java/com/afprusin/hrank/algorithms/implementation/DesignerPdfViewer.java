package com.afprusin.hrank.algorithms.implementation;

import java.util.Scanner;

public class DesignerPdfViewer {

	// Complete the designerPdfViewer function below.
	private int designerPdfViewer(int[] heightsByCharIndex, String word) {
		return word.length() * getMaxHeight(heightsByCharIndex, word);
	}

	private int getMaxHeight(int[] heightByCharIndex, String word) {
		return word.chars()
				.map(charVal -> charVal - 97)
				.map(index -> heightByCharIndex[index])
				.max()
				.orElse(0);
	}

	//private static final Scanner scanner = new Scanner(System.in);
	private static final Scanner scanner = new Scanner(DesignerPdfViewer.class.getResourceAsStream("/DesignerPdfViewer00"));


	public static void main(String[] args) {
		new DesignerPdfViewer().solveProblem();
	}

	public void solveProblem() {
		int[] h = new int[26];

		String[] hItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < 26; i++) {
			int hItem = Integer.parseInt(hItems[i]);
			h[i] = hItem;
		}

		String word = scanner.nextLine();

		int result = designerPdfViewer(h, word);

		System.out.println(String.valueOf(result));

		scanner.close();
	}
}
