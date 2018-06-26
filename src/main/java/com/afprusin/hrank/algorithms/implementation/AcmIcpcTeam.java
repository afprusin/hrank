package com.afprusin.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.Scanner;

public class AcmIcpcTeam {

	// Complete the acmTeam function below.
	private static int[] acmTeam(BitSet[] membersKnownTopics) {

		int currentMaxTopics = 0;
		int maxTopicsRepeats = 1;

		BitSet topicsKnown = new BitSet(membersKnownTopics[0].size());

		for(int i = 0; i < membersKnownTopics.length - 1; i++) {
			for(int j = i + 1; j < membersKnownTopics.length; j++) {
				topicsKnown.clear();
				topicsKnown.or(membersKnownTopics[i]);
				topicsKnown.or(membersKnownTopics[j]);

				final int combinedTopics = topicsKnown.cardinality();
				if(combinedTopics == currentMaxTopics) {
					maxTopicsRepeats++;
				}
				else if(combinedTopics > currentMaxTopics) {
					currentMaxTopics = combinedTopics;
					maxTopicsRepeats = 1;
				}
			}
		}
		return new int[]{currentMaxTopics, maxTopicsRepeats};
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		String[] nm = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nm[0]);

		//int m = Integer.parseInt(nm[1]);

		BitSet[] topic = new BitSet[n];

		for (int i = 0; i < n; i++) {
			String topicText = scanner.nextLine().trim();
			BitSet currentTopicSet = new BitSet(topicText.length());
			for(int j = 0; j < topicText.length(); j++) {
				if(topicText.charAt(j) == '1') {
					currentTopicSet.set(j);
				}
			}
			topic[i] = currentTopicSet;
		}

		int[] result = acmTeam(topic);

		for (int i = 0; i < result.length; i++) {
			bufferedWriter.write(String.valueOf(result[i]));

			if (i != result.length - 1) {
				bufferedWriter.write("\n");
			}
		}

		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}

