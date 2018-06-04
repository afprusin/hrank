package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BonAppetit {
	private class TestCase {
		private final int indexOfAnnasItem;
		private final List<Integer> itemPrices;
		private final int briansCharge;

		TestCase(int indexOfAnnasItem, List<Integer> itemPrices, int briansCharge) {
			this.indexOfAnnasItem = indexOfAnnasItem;
			this.itemPrices = itemPrices;
			this.briansCharge = briansCharge;
		}

		int getIndexOfAnnasItem() {
			return indexOfAnnasItem;
		}

		List<Integer> getItemPrices() {
			return itemPrices;
		}

		int getBriansCharge() {
			return briansCharge;
		}
	}

	public static void main(String args[]) {
		new BonAppetit().performSolution();
	}

	private void performSolution() {
		final int chargeDiscrepency = getChargeDiscrepency(getInput());

		if(chargeDiscrepency > 0) {
			System.out.println(chargeDiscrepency);
		}
		else {
			System.out.println("Bon Appetit");
		}
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

		List<Integer> parameterValues = Arrays.stream(inputLines.get(0).split(" "))
				.map(Integer::parseInt)
				.collect(Collectors.toList());

		int indexOfAnnasItem = parameterValues.get(1);

		List<Integer> itemPrices = Arrays.stream(inputLines.get(1).split(" "))
				.map(Integer::parseInt)
				.collect(Collectors.toList());

		int briansCharge = Integer.parseInt(inputLines.get(2).trim());

		return new TestCase(indexOfAnnasItem, itemPrices, briansCharge);
	}

	private int getChargeDiscrepency(TestCase testCase) {
		List<Integer> pricesExcludingAnnasItem = testCase.getItemPrices();
		pricesExcludingAnnasItem.remove(testCase.getIndexOfAnnasItem());

		final int expectedPayment = pricesExcludingAnnasItem.stream()
				.mapToInt(i -> i)
				.sum() / 2;

		return Math.abs(testCase.getBriansCharge() - expectedPayment);
	}
}
