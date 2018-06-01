package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MigratoryBirds {

	// Complete the migratoryBirds function below.
	private int migratoryBirds(int[] birdSightingsById) {
		final int mostPopularId = Arrays.stream(birdSightingsById)
				.boxed()
				.collect(Collectors.groupingBy(Function.identity()))
				.entrySet().stream().min((o1, o2) -> {
					int compared = Integer.compare(o2.getValue().size(), o1.getValue().size());
					if (compared == 0) {
						compared = Integer.compare(o1.getKey(), o2.getKey());
					}
					return compared;
				}).orElseThrow(() -> new IllegalStateException(
						"Input data did not yield a definite 'most popular' bird"
				))
				.getKey();

		return mostPopularId;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		new MigratoryBirds().solveProblem();
	}

	public void solveProblem() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		int arCount = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] ar = new int[arCount];

		String[] arItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < arCount; i++) {
			int arItem = Integer.parseInt(arItems[i]);
			ar[i] = arItem;
		}

		int result = migratoryBirds(ar);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}

