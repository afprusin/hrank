package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ElectronicsShop {

	private class TestCase {
		private final int budget;
		private final List<Integer> keyboardPrices;
		private final List<Integer> usbPrices;

		public TestCase(int budget, List<Integer> keyboardPrices, List<Integer> usbPrices) {
			this.budget = budget;
			this.keyboardPrices = keyboardPrices;
			this.usbPrices = usbPrices;
		}

		public int getBudget() {
			return budget;
		}

		public List<Integer> getKeyboardPrices() {
			return keyboardPrices;
		}

		public List<Integer> getUsbPrices() {
			return usbPrices;
		}
	}

	public static void main(String args[]) {
		new ElectronicsShop().performSolution();
	}

	private void performSolution() {
		final TestCase testCase = getInput();
		System.out.println(getMaximumAmountSpendable(
				testCase.getBudget(), testCase.getKeyboardPrices(), testCase.getUsbPrices()));
	}

	private TestCase getInput() {
		final int expectedInputLines = 3;

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

		final int budget = Integer.parseInt(inputLines.get(0).trim().split(" ")[0]);
		List<Integer> keyboardPrices = Arrays.stream(getTokenizedLine(inputLines.get(1)))
				.map(Integer::parseInt)
				.collect(Collectors.toList());

		List<Integer> usbPrices = Arrays.stream(getTokenizedLine(inputLines.get(2)))
				.map(Integer::parseInt)
				.collect(Collectors.toList());

		return new TestCase(budget, keyboardPrices, usbPrices);
	}

	private String[] getTokenizedLine(String line) {
		return line.trim().split(" ");
	}

	private int getMaximumAmountSpendable(int budget, List<Integer> keyboardPrices, List<Integer> usbPrices) {
		Collections.sort(keyboardPrices);
		Collections.sort(usbPrices);

		int maximumSpendable = -1;

		for(Integer keyboardPrice : keyboardPrices) {
			for(Integer usbPrice : usbPrices) {
				final int combinedPrice = keyboardPrice + usbPrice;
				if(combinedPrice > budget) {
					break;
				}
				else if(combinedPrice > maximumSpendable) {
					maximumSpendable = combinedPrice;
				}
			}
		}
		return maximumSpendable;
	}
}
