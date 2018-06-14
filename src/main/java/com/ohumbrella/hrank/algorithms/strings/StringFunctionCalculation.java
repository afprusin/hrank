package com.ohumbrella.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StringFunctionCalculation {

	private class LexicalNode {
		Map<Character, LexicalNode> childNodes = new HashMap<>();
		int timesUsed = 0;

		LexicalNode() {
		}

		int getTimesUsed() {
			return timesUsed;
		}

		LexicalNode getAndIncrementChild(char childValue) {
			LexicalNode child = childNodes.computeIfAbsent(childValue, key -> new LexicalNode());
			child.incrementUses();

			return child;
		}

		Collection<LexicalNode> getAllChildren() {
			return childNodes.values();
		}

		private void incrementUses() {
			timesUsed++;
		}
	}

	public static void main(String[] args) {
		new StringFunctionCalculation().performSolution();
	}

	public void performSolution() {
		String input = getInput();
		LexicalNode dictionary = buildIndex(input);
		long score = getScore(dictionary);

		System.out.println(score);
	}

	private String getInput() {
		String inputLine;
		//try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
		try(BufferedReader reader = new BufferedReader((new InputStreamReader(this.getClass().getResourceAsStream("/StringFunctionCalculation01"))))) {
			inputLine = reader.readLine().trim();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return inputLine;
	}

	private LexicalNode buildIndex(String buildFrom) {
		LexicalNode root = new LexicalNode();

		for(int i = 0; i < buildFrom.length(); i++) {
			LexicalNode currentNode = root;
			for(int j = i; j < buildFrom.length(); j++) {
				currentNode = currentNode.getAndIncrementChild(buildFrom.charAt(j));
			}
		}

		return root;
	}

	private long getScore(LexicalNode toScore) {
		return toScore.getAllChildren().stream()
				.mapToLong(child -> getScore(child, 1))
				.max().orElse(0);
	}

	private long getScore(LexicalNode currentNode, int depth) {
		long bestChildScore = currentNode.getAllChildren().stream()
				.mapToLong(child -> getScore(child, depth + 1))
				.max().orElse(0);
		long nodeScore = (currentNode.getTimesUsed() * depth);

		return bestChildScore > nodeScore ? bestChildScore : nodeScore;
	}
}
