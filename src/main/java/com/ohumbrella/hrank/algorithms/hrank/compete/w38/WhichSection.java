package com.ohumbrella.hrank.algorithms.hrank.compete.w38;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WhichSection {

	private class TestCase {
		private Integer myNumber;
		private List<Integer> sectionSizes;

		TestCase(Integer myNumber, List<Integer> sectionSizes) {
			this.myNumber = myNumber;
			this.sectionSizes = sectionSizes;
		}

		Integer getMyNumber() {
			return myNumber;
		}

		List<Integer> getSectionSizes() {
			return sectionSizes;
		}
	}

	public static void main(String[] args) {
		new WhichSection().performSolution();
	}

	public void performSolution() {
		List<TestCase> testCases = getInput();

		for(TestCase testCase : testCases) {
			System.out.println(getExpectedSectionAssignment(
					testCase.getMyNumber(), testCase.getSectionSizes()));
		}
	}

	private List<TestCase> getInput() {

		List<TestCase> testCases = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			int expectedTestcases = Integer.valueOf(reader.readLine().trim());
			String metadataLine;
			while((metadataLine = reader.readLine()) != null) {
				String sectionDataLine = reader.readLine();
				if(sectionDataLine != null) {
					Integer myStudentNumber = Integer.valueOf(
							metadataLine.trim().split(" ")[1]);
					List<Integer> sectionSizes =
							Arrays.stream(sectionDataLine.trim().split(" "))
									.map(Integer::valueOf)
									.collect(Collectors.toList());

					testCases.add(new TestCase(myStudentNumber, sectionSizes));
				}
			}

			if(testCases.size() != expectedTestcases) {
				throw new IllegalStateException(
						"Input did not contain the expected number of test cases (" +
								expectedTestcases + ")");
			}
		} catch (IOException e) {
			throw new IllegalStateException("Encountered error while reading input", e);
		}

		return testCases;
	}

	private Integer getExpectedSectionAssignment(
			Integer studentNumber, List<Integer> sectionSizes) {
		Integer expectedSection = -1;
		Integer studentsAssigned = 0;

		for(int sectionIndex = 0; sectionIndex < sectionSizes.size(); sectionIndex++) {
			studentsAssigned += sectionSizes.get(sectionIndex);
			if(studentsAssigned >= studentNumber) {
				expectedSection = sectionIndex + 1;
				break;
			}
		}

		if(expectedSection == -1) {
			throw new IllegalStateException(
					"Sections were not large enough to assign student number" + studentNumber);
		}

		return expectedSection;
	}
}
