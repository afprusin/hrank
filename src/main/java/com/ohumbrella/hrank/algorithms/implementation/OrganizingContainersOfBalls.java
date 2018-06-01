package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class OrganizingContainersOfBalls {

	// Complete the organizingContainers function below.
	static String organizingContainers(int[][] container) {

		List<Integer> countsOfBalls = new ArrayList<>();
		List<Integer> ballsInContainers = new ArrayList<>();

		for(int i = 0; i < container[0].length; i++) {
			int ballsOfType = 0;
			for (int[] aContainer : container) {
				ballsOfType += aContainer[i];
			}
			countsOfBalls.add(ballsOfType);
		}

		for (int[] aContainer : container) {
			int ballsInContainer = 0;
			for (int j = 0; j < aContainer.length; j++) {
				ballsInContainer += aContainer[j];
			}
			ballsInContainers.add(ballsInContainer);
		}

		boolean isPossible = true;

		for(Integer currentCount : countsOfBalls) {
			Iterator<Integer> containerIterator = ballsInContainers.iterator();
			boolean validContainerFound = false;
			while(containerIterator.hasNext()) {
				Integer currentContainer = containerIterator.next();
				if(currentContainer.equals(currentCount)) {
					validContainerFound = true;
					containerIterator.remove();
					break;
				}
			}
			if( ! validContainerFound) {
				isPossible = false;
				break;
			}
		}

		if(isPossible) {
			return "Possible";
		}
		else {
			return "Impossible";
		}
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		int q = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int qItr = 0; qItr < q; qItr++) {
			int n = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int[][] container = new int[n][n];

			for (int i = 0; i < n; i++) {
				String[] containerRowItems = scanner.nextLine().split(" ");
				scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

				for (int j = 0; j < n; j++) {
					int containerItem = Integer.parseInt(containerRowItems[j]);
					container[i][j] = containerItem;
				}
			}

			String result = organizingContainers(container);

			bufferedWriter.write(result);
			bufferedWriter.newLine();
		}

		bufferedWriter.close();

		scanner.close();
	}
}

