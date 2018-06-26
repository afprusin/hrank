package com.afprusin.hrank.algorithms.implementation;

import java.util.*;
import java.util.stream.Collectors;

public class AlmostSorted {
	private enum Manipulation {
		SWAP("swap"),
		REVERSE("reverse");

		private String asText;

		Manipulation(String asText) {
			this.asText = asText;
		}

		public String getAsText() {
			return asText;
		}
	}

	private class SortTechnique {
		private final Manipulation manipulation;
		private final int firstIndex;
		private final int secondIndex;

		SortTechnique(Manipulation manipulation, int firstIndex, int secondIndex) {
			this.manipulation = manipulation;
			this.firstIndex = firstIndex;
			this.secondIndex = secondIndex;
		}

		Manipulation getManipulation() {
			return manipulation;
		}

		int getFirstIndex() {
			return firstIndex;
		}

		int getSecondIndex() {
			return secondIndex;
		}
	}

	private class SortResult {
		private final boolean isSortable;
		private final Optional<SortTechnique> technique;

		SortResult(boolean isSortable, Optional<SortTechnique> technique) {
			this.isSortable = isSortable;
			this.technique = technique;
		}

		public boolean isSortable() {
			return isSortable;
		}

		Optional<SortTechnique> getTechnique() {
			return technique;
		}
	}

	private SortResult getSortOperationIfPossible(List<Integer> toSort) {
		List<Integer> sorted = new ArrayList<>(toSort);
		Collections.sort(sorted);

		List<Integer> unequalIndexes = new ArrayList<>();
		for(int i = 0; i < toSort.size(); i++) {
			if( ! toSort.get(i).equals(sorted.get(i))) {
				unequalIndexes.add(i);
			}
		}

		SortResult result;
		if(unequalIndexes.isEmpty()) {
			result = new SortResult(true, Optional.empty());
		}
		else if(unequalIndexes.size() == 2) {
			result = new SortResult(true, Optional.of(new SortTechnique(
					Manipulation.SWAP,
					unequalIndexes.get(0) + 1,
					unequalIndexes.get(1) + 1)));
		}
		else if(unequalIndexes.size() > 2  &&  isSortSingleReversal(unequalIndexes, toSort, sorted)) {
			result = new SortResult(true, Optional.of(new SortTechnique(
					Manipulation.REVERSE,
					unequalIndexes.get(0) + 1,
					unequalIndexes.get(unequalIndexes.size() - 1) + 1)));
		}
		else {
			result = new SortResult(false, Optional.empty());
		}

		return result;
	}

	private boolean isSortSingleReversal(List<Integer> indexes, List<Integer> toSort, List<Integer> sorted) {
		return areIndexesSequential(indexes)  &&  areValuesMirrored(indexes, toSort, sorted);
	}

	private boolean areValuesMirrored(List<Integer> indexes, List<Integer> toSort, List<Integer> sorted) {
		boolean result = true;

		final int halfIndexesRoundedDown = Math.floorDiv(indexes.size(), 2);
		for(int i = 0; i < halfIndexesRoundedDown; i++) {
			final int originalIndex = indexes.get(i);
			final int mirroredIndex = indexes.get(indexes.size() - (1 + i));

			if(( ! toSort.get(originalIndex).equals(sorted.get(mirroredIndex)))  ||
					( ! sorted.get(originalIndex).equals(toSort.get(mirroredIndex)))) {
				result = false;
				break;
			}
		}
		return result;
	}

	private boolean areIndexesSequential(List<Integer> indexes) {
		final int indexRange = indexes.get(indexes.size() - 1) - indexes.get(0);
		final boolean hasMidpoint = indexRange % 2 == 0;

		boolean result = true;
		for(int i = 0; i < indexes.size() - 1; i++) {
			// It should be assumed that the midpoint will always be missing for
			//	a correct reversal of an odd number of elements
			if(hasMidpoint  &&  (i == ((indexes.size() / 2) - 1))) {
				continue;
			}
			if((indexes.get(i) + 1) != indexes.get(i + 1)) {
				result = false;
				break;
			}
		}
		return result;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new AlmostSorted().performSolution();
	}

	public void performSolution() {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		List<Integer> toSort = Arrays.stream(scanner.nextLine().split(" "))
				.map(Integer::parseInt)
				.collect(Collectors.toList());

		final SortResult result = getSortOperationIfPossible(toSort);
		if(result.isSortable()) {
			System.out.println("yes");
			result.getTechnique().ifPresent(technique ->
					System.out.println(technique.getManipulation().getAsText() +
					" " + technique.getFirstIndex() +
					" " + technique.getSecondIndex()));
		}
		else {
			System.out.println("no");
		}

		scanner.close();
	}
}

