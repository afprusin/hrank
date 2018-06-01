package com.ohumbrella.hrank.algorithms.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HappyLadybugs {

	// Complete the happyLadybugs function below.
	private boolean canLadybugsBeMadeHappy(String b) {
		Map<Character, List<Character>> squaresSorted = new HashMap<>();
		boolean hasSwapSquare = false;
		for(char currentSquare : b.toCharArray()) {
			if(currentSquare == '_') {
				hasSwapSquare = true;
			}
			else {
				squaresSorted.computeIfAbsent(currentSquare, key -> new ArrayList<>())
						.add(currentSquare);
			}
		}

		boolean soloLadybug = false;
		for(List<Character> colorCategory : squaresSorted.values()) {
			if(colorCategory.size() < 2) {
				soloLadybug = true;
				break;
			}
		}

		boolean result;
		if(hasSwapSquare  &&  ! soloLadybug) {
			result = true;
		}
		else if( ! soloLadybug  &&  areLadybugsAlreadyHappy(b)) {
			result = true;
		}
		else {
			result = false;
		}

		return result;
	}

	private boolean areLadybugsAlreadyHappy(String b) {
		boolean areHappy = true;

		boolean colorPairSeen = false;
		char lastColorSeen = b.charAt(0);
		for(int i = 1; i < b.length(); i++) {
			final char currentColor = b.charAt(i);
			if(currentColor == '_') {
				continue;
			}
			if(currentColor != lastColorSeen  &&  ! colorPairSeen) {
				areHappy = false;
				break;
			}
			if(currentColor == lastColorSeen) {
				colorPairSeen = true;
			}
			lastColorSeen = currentColor;
		}

		return areHappy;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new HappyLadybugs().performSolution();
	}

	private void performSolution() {
		int g = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int gItr = 0; gItr < g; gItr++) {
			int n = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			String b = scanner.nextLine();

			boolean result = canLadybugsBeMadeHappy(b);
			if(result) {
				System.out.println("YES");
			}
			else {
				System.out.println("NO");
			}
		}

		scanner.close();
	}
}

