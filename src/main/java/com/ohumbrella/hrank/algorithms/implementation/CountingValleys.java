package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountingValleys {

	private static final char UP_SYMBOL = 'U';
	private static final char DOWN_SYMBOL = 'D';

	public static void main(String args[]) {
		new CountingValleys().performSolution();
	}

	private void performSolution() {
		System.out.println(getValleysTraversed(getInput()));
	}

	private List<Integer> getInput() {
		final int expectedInputLines = 2;

		List<String> inputLines = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			String line;
			while((line = reader.readLine()) != null  &&  inputLines.size() < expectedInputLines) {
				inputLines.add(line);
			}
		}
		catch (IOException e) {
			throw new IllegalStateException("Encountered error while reading testcase", e);
		}

		List<Integer> elevationChanges = new ArrayList<>();
		for(char inputChar : inputLines.get(1).toCharArray()) {
			elevationChanges.add(getElevationValueOfInputCharacter(inputChar));
		}

		return elevationChanges;
	}

	private int getElevationValueOfInputCharacter(char inputChar) {
		if(inputChar == UP_SYMBOL) {
			return 1;
		}
		else if(inputChar == DOWN_SYMBOL) {
			return -1;
		}
		else {
			throw new IllegalArgumentException("Encountered unexpected symbol " + inputChar + " while reading input");
		}
	}

	private int getValleysTraversed(List<Integer> elevationChanges) {
		int currentElevation = 0;
		int valleysTraversed = 0;
		boolean inValley = false;

		for(Integer elevationChange : elevationChanges) {
			currentElevation += elevationChange;
			if(inValley  &&  currentElevation >= 0) {
				valleysTraversed++;
				inValley = false;
			}
			else if(currentElevation < 0) {
				inValley = true;
			}
		}
		return valleysTraversed;
	}
}
