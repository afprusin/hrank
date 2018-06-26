package com.afprusin.hrank.algorithms.implementation;

import java.util.*;

public class QueensAttackII {

	// Complete the queensAttack function below.
	private int queensAttack(int dimensions, int queensX, int queensY, int[][] obstacles) {
		Map<Integer, Set<Integer>> obstacleIndex = new HashMap<>();

		for(int[] obstacle : obstacles) {
			final int obstacleX = obstacle[0];
			final int obstacleY = obstacle[1];

			obstacleIndex.computeIfAbsent(obstacleX, key -> new HashSet<>())
					.add(obstacleY);
		}

		int attackableSquares = 0;
		for(int iterationX : Arrays.asList(-1, 0, 1)) {
			for(int iterationY : Arrays.asList(-1, 0, 1)) {
				if(iterationX == 0  &&  iterationY == 0) {
					continue;
				}
				attackableSquares +=
						getValidSquaresToBoundary(queensX, queensY, dimensions, obstacleIndex, iterationX, iterationY);
			}
		}

		return attackableSquares;
	}

	private int getValidSquaresToBoundary(
			int queensX, int queensY, int dimensions, Map<Integer, Set<Integer>> obstacleIndex,
			int iterationX, int iterationY) {

		int x = queensX + iterationX;
		int y = queensY + iterationY;
		int validSquares = 0;

		while(x > 0  &&  x <= dimensions  &&  y > 0  &&  y <= dimensions) {
			if(obstacleIndex.containsKey(x)  &&  obstacleIndex.get(x).contains(y)) {
				break;
			}
			validSquares++;
			x += iterationX;
			y += iterationY;
		}

		return validSquares;
	}

	//private static final Scanner scanner = new Scanner(System.in);
	private static final Scanner scanner = new Scanner(QueensAttackII.class.getResourceAsStream("/QueensAttackII20"));

	public static void main(String[] args) {
		new QueensAttackII().performSolution();
	}

	private void performSolution() {
		String[] nk = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nk[0]);

		int k = Integer.parseInt(nk[1]);

		String[] r_qC_q = scanner.nextLine().split(" ");

		int r_q = Integer.parseInt(r_qC_q[0]);

		int c_q = Integer.parseInt(r_qC_q[1]);

		int[][] obstacles = new int[k][2];

		for (int i = 0; i < k; i++) {
			String[] obstaclesRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			for (int j = 0; j < 2; j++) {
				int obstaclesItem = Integer.parseInt(obstaclesRowItems[j]);
				obstacles[i][j] = obstaclesItem;
			}
		}

		int result = queensAttack(n, r_q, c_q, obstacles);

		System.out.println(result);

		scanner.close();
	}
}

