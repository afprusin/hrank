package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CatsAndAMouse {

	private class TestCase {
		private final int catAPosition;
		private final int catBPosition;
		private final int mousePosition;

		public TestCase(int catAPosition, int catBPosition, int mousePosition) {
			this.catAPosition = catAPosition;
			this.catBPosition = catBPosition;
			this.mousePosition = mousePosition;
		}

		public int getCatAPosition() {
			return catAPosition;
		}

		public int getCatBPosition() {
			return catBPosition;
		}

		public int getMousePosition() {
			return mousePosition;
		}
	}

	private enum Outcome {
		CAT_A("Cat A"),
		CAT_B("Cat B"),
		MOUSE("Mouse C");

		private String asText;

		Outcome(String asText) {
			this.asText = asText;
		}

		public String getAsText() {
			return asText;
		}
	}

	public static void main(String args[]) {
		new CatsAndAMouse().performSolution();
	}

	private void performSolution() {
		final List<TestCase> testCases = getInput();
		for(TestCase testCase : testCases) {
			System.out.println(getCatAndMouseOutcome(testCase).getAsText());
		}
	}

	private List<TestCase> getInput() {
		List<String> inputLines = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			String line;
			while((line = reader.readLine()) != null) {
				inputLines.add(line);
			}
		}
		catch (IOException e) {
			throw new IllegalStateException("Encountered error while reading testcase", e);
		}

		return inputLines.subList(1, inputLines.size()).stream()
				.map(this::convertInputLineToTestCase)
				.collect(Collectors.toList());
	}

	private TestCase convertInputLineToTestCase(String inputLine) {
		final String[] tokens = inputLine.trim().split(" ");
		return new TestCase(
				Integer.parseInt(tokens[0]),
				Integer.parseInt(tokens[1]),
				Integer.parseInt(tokens[2]));
	}

	private Outcome getCatAndMouseOutcome(TestCase testCase) {
		Outcome outcome;
		int catADistance = Math.abs(testCase.getCatAPosition() - testCase.getMousePosition());
		int catBDistance = Math.abs(testCase.getCatBPosition() - testCase.getMousePosition());

		if(catADistance < catBDistance) {
			outcome = Outcome.CAT_A;
		}
		else if(catBDistance < catADistance) {
			outcome = Outcome.CAT_B;
		}
		else {
			outcome = Outcome.MOUSE;
		}
		return outcome;
	}
}
