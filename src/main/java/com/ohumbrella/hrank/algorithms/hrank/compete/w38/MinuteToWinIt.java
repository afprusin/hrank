package com.ohumbrella.hrank.algorithms.hrank.compete.w38;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinuteToWinIt {

	private class TestCase {
		private Integer valueDifference;
		private List<Integer> values;

		TestCase(Integer valueDifference, List<Integer> values) {
			this.valueDifference = valueDifference;
			this.values = values;
		}

		Integer getValueDifference() {
			return valueDifference;
		}

		public List<Integer> getValues() {
			return values;
		}
	}

	public static void main(String[] args) {
		new MinuteToWinIt().performSolution();
	}

	public void performSolution() {
		TestCase testCase = getInput();

		System.out.println(getTotalTimeToCorrectValueSteps(
				testCase.getValueDifference(), testCase.getValues()));
	}

	private TestCase getInput() {
		TestCase testCase;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			String metadataLine;
			String valuesLine;
			metadataLine = reader.readLine();
			valuesLine = reader.readLine();

			if(metadataLine != null &&  valuesLine != null) {
				Integer valueDifference =
						Integer.valueOf(metadataLine.trim().split(" ")[1]);
				List<Integer> values = Arrays.stream(valuesLine.trim().split(" "))
						.map(Integer::valueOf)
						.collect(Collectors.toList());

				testCase = new TestCase(valueDifference, values);
			}
			else {
				throw new IllegalStateException(
						"Input did not contain the expected test case");
			}
		} catch (IOException e) {
			throw new IllegalStateException("Encountered error while reading input", e);
		}

		return testCase;
	}

	private Integer getTotalTimeToCorrectValueSteps(
			Integer valueStep, List<Integer> values) {

		Map<Integer, List<Integer>> offsetGroups = getOffsetGroups(valueStep, values);
		Integer largestOffsetGroup = offsetGroups.values().stream()
				.map(List::size)
				.max(Comparator.comparing(Function.identity())).orElseThrow(() ->
						new IllegalStateException("Unable to determine largest valid offset subsegment"));

		return values.size() - largestOffsetGroup;
	}

	private Map<Integer, List<Integer>> getOffsetGroups(Integer valueStep, List<Integer> values) {
		return IntStream.range(0, values.size())
				.boxed()
				.collect(Collectors.groupingBy(valuesIndex ->
						getOffset(valuesIndex, values.get(valuesIndex), valueStep)));
	}

	private Integer getOffset(Integer index, Integer value, Integer valueStep) {
		return value - (index * valueStep);
	}
}
