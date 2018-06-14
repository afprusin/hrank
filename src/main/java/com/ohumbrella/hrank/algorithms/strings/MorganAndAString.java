package com.ohumbrella.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MorganAndAString {

	private class CharacterQueue {
		private String characters;
		private int currentIndex = 0;

		CharacterQueue(String characters) {
			this.characters = characters;
		}

		boolean hasNext() {
			return currentIndex < characters.length();
		}

		boolean hasNext(int advancePlaces) {
			return characters.length() > currentIndex + advancePlaces;
		}

		char getNext() {
			if(currentIndex >= characters.length()) {
				throw new IndexOutOfBoundsException("No characters remaining");
			}
			return characters.charAt(currentIndex++);
		}

		char peekNext() {
			return peekNext(0);
		}

		char peekNext(int advancePlaces) {
			if(characters.length() <= currentIndex + advancePlaces) {
				throw new IndexOutOfBoundsException("No advanced characters remaining");
			}
			return characters.charAt(currentIndex + advancePlaces);
		}

		String getRemaining() {
			if(currentIndex >= characters.length()) {
				throw new IndexOutOfBoundsException("No characters remaining");
			}
			String result = characters.substring(currentIndex, characters.length());
			currentIndex = characters.length();

			return result;
		}
	}

	private class TestCase {
		private CharacterQueue firstWord;
		private CharacterQueue secondWord;

		TestCase(String firstWord, String secondWord) {
			this.firstWord = new CharacterQueue(firstWord);
			this.secondWord = new CharacterQueue(secondWord);
		}

		CharacterQueue getFirstSequence() {
			return firstWord;
		}

		CharacterQueue getSecondSequence() {
			return secondWord;
		}
	}

	public static void main(String args[]) {
		new MorganAndAString().performSolution();
	}

	private void performSolution() {
		List<TestCase> testCases = getInput();
		testCases.forEach(testCase -> System.out.println(getLexicographicallyMinimalQueueOrder(testCase)));
	}

	private List<TestCase> getInput() {
		List<TestCase> testCases = new ArrayList<>();
		int expectedTestCases;
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			expectedTestCases = Integer.valueOf(reader.readLine().trim());

			String firstSequence;
			String secondSequence;
			while ((firstSequence = reader.readLine()) != null &&
					(secondSequence = reader.readLine()) != null) {
				testCases.add(new TestCase(firstSequence.trim(), secondSequence.trim()));
			}

			if(expectedTestCases != testCases.size()) {
				throw new IllegalStateException("Specified number of testcases (" + expectedTestCases +
						" ) did not match the number provided (" + testCases.size() + ")");
			}
		}
		catch (IOException e) {
			throw new IllegalStateException("Encountered error while reading testcase", e);
		}

		return testCases;
	}

	private String getLexicographicallyMinimalQueueOrder(TestCase testCase) {
		StringBuilder result = new StringBuilder();

		CharacterQueue first = testCase.getFirstSequence();
		CharacterQueue second = testCase.getSecondSequence();

		while(first.hasNext()  ||  second.hasNext()) {
			if(first.hasNext()  &&  second.hasNext()) {
				appendLowestValue(first, second, result);
			}
			else if(first.hasNext()) {
				appendRemaining(first, result);
			}
			else if(second.hasNext()) {
				appendRemaining(second, result);
			}
		}

		return result.toString();
	}

	private void appendLowestValue(CharacterQueue first, CharacterQueue second, StringBuilder appendTo) {
		if(first.peekNext() == second.peekNext()) {
			consumeFromTiebreakerQueue(first, second, appendTo);
		}
		else if(first.peekNext() < second.peekNext()) {
			appendTo.append(first.getNext());
		}
		else {
			appendTo.append(second.getNext());
		}
	}

	private void consumeFromTiebreakerQueue(CharacterQueue first, CharacterQueue second, StringBuilder appendTo) {
		int positionsAdvanced = 0;
		CharacterQueue tiebreaker = first;
		CharacterQueue other = second;

		while(first.hasNext(positionsAdvanced)  ||  second.hasNext(positionsAdvanced)) {
			if( ! first.hasNext(positionsAdvanced)) {
				tiebreaker = second;
				other = first;
				break;
			}
			if( ! second.hasNext(positionsAdvanced)) {
				break;
			}
			if(first.peekNext(positionsAdvanced) < second.peekNext(positionsAdvanced)) {
				break;
			}
			if(second.peekNext(positionsAdvanced) < first.peekNext(positionsAdvanced)) {
				tiebreaker = second;
				other = first;
				break;
			}
			positionsAdvanced++;
		}

		while(tiebreaker.hasNext()  &&  tiebreaker.peekNext() == other.peekNext()) {
			appendTo.append(tiebreaker.getNext());
		}
	}

	private void appendRemaining(CharacterQueue from, StringBuilder appendTo) {
		appendTo.append(from.getRemaining());
	}
}
