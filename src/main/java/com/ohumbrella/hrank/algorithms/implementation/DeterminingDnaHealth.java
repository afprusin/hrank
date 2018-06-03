package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class DeterminingDnaHealth {

	private static int getCharAsBaseZeroIndex(char toGet) {
		return ((int)toGet) - 97;
	}

	private class DictionaryNode {
		private DictionaryNode[] children = new DictionaryNode[26];
		private List<HealthValue> healthValues = new ArrayList<>();

		DictionaryNode[] getChildren() {
			return children;
		}

		boolean isTerminal() {
			return ( ! healthValues.isEmpty());
		}

		void addHealthValue(long health, int index) {
			healthValues.add(new HealthValue(health, index));
		}

		long getTotalValue(int lowIndex, int highIndex) {
			long total = 0;
			for(HealthValue currentValue : healthValues) {
				if(currentValue.getIndex() >= lowIndex  &&
						currentValue.getIndex() <= highIndex) {
					total += currentValue.getHealth();
				}
			}
			return total;
		}
	}

	private class HealthValue {
		private long health;
		private int index;

		HealthValue(long health, int index) {
			this.health = health;
			this.index = index;
		}

		long getHealth() {
			return health;
		}

		int getIndex() {
			return index;
		}
	}

	private DictionaryNode initializeDictionary(List<String> words, List<Long> values) {
		DictionaryNode root = new DictionaryNode();

		for(int i = 0; i < words.size(); i++) {
			addWordToDictionary(words.get(i), values.get(i), i, root, 0);
		}

		return root;
	}

	private void addWordToDictionary(
			String word, long healthValue, int healthIndex,
			DictionaryNode currentNode, int currentIndex) {
		final boolean isLastCharacter = currentIndex == (word.length() - 1);
		final char currentCharacter = word.charAt(currentIndex);
		final int characterIndex = getCharAsBaseZeroIndex(currentCharacter);

		if(currentNode.getChildren()[characterIndex] == null) {
			currentNode.getChildren()[characterIndex] = new DictionaryNode();
		}
		DictionaryNode childNode = currentNode.getChildren()[getCharAsBaseZeroIndex(currentCharacter)];

		if(isLastCharacter) {
			childNode.addHealthValue(healthValue, healthIndex);
		}
		else {
			addWordToDictionary(word, healthValue, healthIndex, childNode, currentIndex + 1);
		}
	}

	private static long getGeneticHealth(
			String genetics, DictionaryNode root, int lowIndex, int highIndex) {

		int baseIndex = 0;
		int traversalIndex = 0;
		long totalHealth = 0;

		while(baseIndex < genetics.length()) {
			DictionaryNode currentNode = root;
			traversalIndex = baseIndex;

			while(traversalIndex < genetics.length()) {
				final char currentCharacter = genetics.charAt(traversalIndex);
				final int characterIndex = getCharAsBaseZeroIndex(currentCharacter);
				currentNode = currentNode.getChildren()[characterIndex];

				if (currentNode == null) {
					break;
				}
				totalHealth += currentNode.getTotalValue(lowIndex, highIndex);
				traversalIndex++;
			}
			baseIndex++;
		}
		return totalHealth;
	}

	public static void main(String[] args) {
		for(int i = 0; i < 100; i++)
		new DeterminingDnaHealth().runSolution();
	}

	private void runSolution() {
		long startTime = System.nanoTime();
//		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(
				this.getClass().getResourceAsStream("DnaHealth13")))) {
			int geneticSequences = Integer.parseInt(reader.readLine());

			List<String> genes = Arrays.asList(reader.readLine().split(" "));

			List<Long> geneHealths = Arrays.stream(reader.readLine().split(" "))
					.map(Long::valueOf)
					.collect(Collectors.toList());

			int testCases;
			testCases = Integer.parseInt(reader.readLine());

			long inputParsingFinish = System.nanoTime();

			DictionaryNode dictionaryRoot = initializeDictionary(genes, geneHealths);

			long initializeDictionary = System.nanoTime();

			long lowestHealth = Long.MAX_VALUE;
			long greatestHealth = Long.MIN_VALUE;
			for (int i = 0; i < testCases; i++) {
				final String[] testCaseTokens = reader.readLine().split(" ");
				final int firstValidHealth = Integer.parseInt(testCaseTokens[0]);
				final int lastValidHealth = Integer.parseInt(testCaseTokens[1]);
				final String genetics = testCaseTokens[2];

				final long currentHealth = getGeneticHealth(
						genetics, dictionaryRoot, firstValidHealth, lastValidHealth);

				if(lowestHealth > currentHealth) {
					lowestHealth = currentHealth;
				}
				if(greatestHealth < currentHealth) {
					greatestHealth = currentHealth;
				}
			}

			long search = System.nanoTime();

			System.out.println(inputParsingFinish - startTime);
			System.out.println(initializeDictionary - inputParsingFinish);
			System.out.println(search - initializeDictionary);

			// Print results
			System.out.println(lowestHealth + " " + greatestHealth);
		}
		catch (IOException e) {
			throw new IllegalStateException(
					"Encountered error while reading test case from standard-in", e);
		}
	}
}