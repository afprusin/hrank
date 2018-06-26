package com.afprusin.hrank.algorithms.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SockMerchant {

	public static void main(String args[]) {
		new SockMerchant().performSolution();
	}

	private void performSolution() {
		System.out.println(getCountOfSockPairs(getInput()));
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

		return Arrays.stream(inputLines.get(1).split(" "))
				.map(Integer::parseInt)
				.collect(Collectors.toList());
	}

	private int getCountOfSockPairs(List<Integer> socks) {
		return socks.stream()
				.collect(Collectors.groupingBy(color -> color)).values().stream()
				.mapToInt(List::size)
				.map(numberOfEachColor -> Math.floorDiv(numberOfEachColor, 2))
				.sum();
	}
}
