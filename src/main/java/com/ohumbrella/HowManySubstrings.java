package com.ohumbrella;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class HowManySubstrings {

	private class TestCase {
		private int lowerBounds;
		private int upperBounds;

		public TestCase(int lowerBounds, int upperBounds) {
			this.lowerBounds = lowerBounds;
			this.upperBounds = upperBounds;
		}
	}

	private class Index {
		private int index;

		public Index(int index) {
			this.index = index;
		}
	}

	private class TraversalGroup {
		// Current index offset of this group
		int indexOffset;
		// Value-as-index sorter.  Used only to meet time requirements
		TraversalGroup[] subgroups = new TraversalGroup[26];
		ArrayList<Index> members = new ArrayList<>();
		boolean isClean = true;
		int preserveValue = -1;

		TraversalGroup(int indexOffset) {
			this.indexOffset = indexOffset;
		}
	}

	public static void main(String[] args) throws IOException {
		new HowManySubstrings().performSolution();
	}

	public void performSolution() throws IOException {
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader reader = new BufferedReader(new InputStreamReader(HowManySubstrings.class.getResourceAsStream("/HowManySubstrings10")));
		String[] nq = reader.readLine().split(" ");

		int stringLength = Integer.parseInt(nq[0].trim());

		int substringQueries = Integer.parseInt(nq[1].trim());

		String toQuery = reader.readLine().trim();

		int[] toQueryAsIndexes = getStringConvertedToIndexes(toQuery);

		List<TestCase> testCases = new ArrayList<>();
		for (int i = 0; i < substringQueries; i++) {
			String[] testCaseTokens = reader.readLine().split(" ");
			int queryLowerBounds = Integer.valueOf(testCaseTokens[0]);
			int queryUpperBounds = Integer.valueOf(testCaseTokens[1]);

			testCases.add(new TestCase(queryLowerBounds, queryUpperBounds));
		}

		for(TestCase currentCase : testCases) {
			//long startTime = System.nanoTime();

			int[] subQuery = Arrays.copyOfRange(
					toQueryAsIndexes, currentCase.lowerBounds, currentCase.upperBounds + 1);

			System.out.println(
					getCountOfUniqueSubstrings(subQuery));

			//long endTime = System.nanoTime();

			//System.out.println("In " + (endTime - startTime) / 1000000000.0 + " s");
		}
		reader.close();
	}

	/**
	 * Counts the number of unique substrings in a given substring range
	 */
	private long getCountOfUniqueSubstrings(int[] toQuery) {
		Deque<TraversalGroup> groups = new ArrayDeque<>();
		groups.push(getInitialGroup(toQuery));

		long uniqueSubstrings = 0;

		while( ! groups.isEmpty()) {
			TraversalGroup currentGroup = groups.pop();
			int groupSize = currentGroup.members.size();

			while (groupSize > 1) {
				for(int indexIterator = 0; indexIterator < groupSize; indexIterator++) {
					final Index currentIndex = currentGroup.members.get(indexIterator);
					final int combinedIndex = currentIndex.index + currentGroup.indexOffset;

					// This traversal point has reached the end of the substring
					if(combinedIndex >= toQuery.length) {
						currentGroup.members.remove(indexIterator);
						indexIterator--;
						groupSize--;
						continue;
					}

					final int currentValue = toQuery[combinedIndex];

					// Convert this group to the first substring character encountered.
					//	Similar-value traversals will remain in the group
					if (currentGroup.isClean) {
						uniqueSubstrings++;
						currentGroup.preserveValue = currentValue;
						currentGroup.isClean = false;
					}
					// Traversals with a different value are placed in new groups
					else if (currentValue != currentGroup.preserveValue) {
						currentGroup.members.remove(indexIterator);
						indexIterator--;
						groupSize--;
						if (currentGroup.subgroups[currentValue] == null) {
							uniqueSubstrings++;
							currentGroup.subgroups[currentValue] =
									new TraversalGroup(currentGroup.indexOffset + 1);
						}
						currentGroup.subgroups[currentValue].members.add(currentIndex);
					}
				}

				// Add new groups to the processing queue
				for(int i = 0; i < currentGroup.subgroups.length; i++) {
					TraversalGroup additionalGroup = currentGroup.subgroups[i];
					if(additionalGroup != null) {
						groups.push(additionalGroup);
						currentGroup.subgroups[i] = null;
					}
				}

				currentGroup.isClean = true;
				currentGroup.indexOffset++;
			}

			// The group was stopped early; add the remaining steps for the final member to the unique substrings tally
			if(groupSize == 1) {
				uniqueSubstrings +=
						(toQuery.length - (currentGroup.members.get(0).index + currentGroup.indexOffset));
			}
		}

		return uniqueSubstrings;
	}

	private TraversalGroup getInitialGroup(int[] subString) {
		TraversalGroup root = new TraversalGroup(0);

		for(int i = 0; i < subString.length; i++) {
			root.members.add(new Index(i));
		}

		return root;
	}

	private int[] getStringConvertedToIndexes(String toConvert) {
		int[] converted = new int[toConvert.length()];

		for(int i = 0; i < toConvert.length(); i++) {
			converted[i] = ((int)toConvert.charAt(i)) - 97;
		}

		return converted;
	}
}
