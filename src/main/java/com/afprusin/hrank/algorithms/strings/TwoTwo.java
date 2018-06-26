package com.afprusin.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TwoTwo {

	private class NumberNode {
		// 0-9 indexes representing digits 0-9; required to meet runtime needs
		private NumberNode[] childDigits = new NumberNode[10];
		private boolean isTerminal = false;

		public NumberNode get(int digit) {
			NumberNode resolved = childDigits[digit];
			if(resolved == null) {
				throw new IllegalArgumentException(
						"Digit " + digit + " not present in the pattern tree at this node");
			}
			return resolved;
		}

		NumberNode getAndCreateIfAbsent(int number) {
			NumberNode resolved = childDigits[number];
			if(resolved == null) {
				resolved = new NumberNode();
				childDigits[number] = resolved;
			}
			return resolved;
		}

		void setTerminal() {
			isTerminal = true;
		}

		boolean isTerminal() {
			return isTerminal;
		}

		boolean isPresent(int nextDigit) {
			return childDigits[nextDigit] != null;
		}
	}

	public static void main(String[] args) {
		new TwoTwo().performSolution();
	}

	public void performSolution() {
		List<int[]> testCases = getInput();
		NumberNode rootPattern = getPowersOfTwoPattern();

		for(int[] testCase : testCases) {
			System.out.println(getCountOfInstancesInText(rootPattern, testCase));
		}
	}

	private List<int[]> getInput() {
		String inputLine;
		List<int[]> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			int expectedTestcases = Integer.valueOf(reader.readLine().trim());
			while((inputLine = reader.readLine()) != null) {
				lines.add(convertStringToNumericArray(inputLine.trim()));
			}

			if(lines.size() != expectedTestcases) {
				throw new IllegalStateException(
						"Input did not contain the expected number of test cases (" +
								expectedTestcases + ")");
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return lines;
	}

	private NumberNode getPowersOfTwoPattern() {
		NumberNode root = new NumberNode();

		addToPattern(root, BigInteger.ONE);

		BigInteger currentPowerOfTwo = BigInteger.valueOf(2);
		for(int i = 1; i <= 800; i++) {
			addToPattern(root, currentPowerOfTwo);
			currentPowerOfTwo = currentPowerOfTwo.multiply(BigInteger.valueOf(2));
		}

		return root;
	}

	private int[] convertStringToNumericArray(String toConvert) {
		return toConvert.chars()
				.map(this::getNumericCharacterAsInteger)
				.toArray();
	}


	private int getNumericCharacterAsInteger(int toConvert) {
		final int zeroCharacterOffset = 48;

		return toConvert - zeroCharacterOffset;
	}

	private void addToPattern(NumberNode root, BigInteger toAdd) {
		NumberNode currentNode = root;
		for(char currentCharacter : toAdd.toString().toCharArray()) {
			final int nextDigit = getNumericCharacterAsInteger(currentCharacter);
			currentNode = currentNode.getAndCreateIfAbsent(nextDigit);
		}
		currentNode.setTerminal();
	}

	private long getCountOfInstancesInText(NumberNode patternRoot, int[] toSearch) {
		long count = 0;
		for(int i = 0; i < toSearch.length; i++) {
			NumberNode currentNode = patternRoot;
			for(int j = i; j < toSearch.length; j++) {
				final int digit = toSearch[j];

				if(currentNode.isPresent(digit)) {
					currentNode = currentNode.get(digit);
					if(currentNode.isTerminal()) {
						count++;
					}
				}
				else {
					break;
				}
			}
		}
		return count;
	}
}
