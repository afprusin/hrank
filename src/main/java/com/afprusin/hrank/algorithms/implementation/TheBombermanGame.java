package com.afprusin.hrank.algorithms.implementation;

import java.util.*;
import java.util.stream.Collectors;

public class TheBombermanGame {
	private class Bomb {
		private final int x;
		private final int y;
		private boolean active = true;

		Bomb(int x, int y) {
			this.x = x;
			this.y = y;
		}

		int getX() {
			return x;
		}

		int getY() {
			return y;
		}

		void deactivate() {
			active = false;
		}

		boolean isActive() {
			return active;
		}
	}

	private class BombGroup {
		Collection<Bomb> bombs;

		BombGroup(Collection<Bomb> bombs) {
			this.bombs = bombs;
		}

		Collection<Bomb> getBombs() {
			return bombs;
		}
	}

	private static final char BOMB_SYMBOL = 'O';
	private static final char EMPTY_SYMBOL = '.';
	private static final int CYCLE_START_ROUND = 5;
	private static final int ROUNDS_IN_PATTERN_CYCLE = 4;

	// Complete the bomberMan function below.
	private String getGridAfterRounds(int totalRounds, String[] grid) {
		Bomb[][] bombGrid = new Bomb[grid.length][grid[0].length()];

		Deque<BombGroup> bombs = new ArrayDeque<>();
		bombs.addLast(setConvertedBombs(grid, bombGrid));

		totalRounds = calculateEffectiveTotalComputationRounds(totalRounds);

		int currentRound = 2;
		while(currentRound <= totalRounds) {
			final boolean bombPlacementRound = currentRound % 2 == 0;

			// Place bombs
			if(bombPlacementRound) {
				bombs.addLast(getBombsPlantedThisRound(bombGrid, currentRound));
			}
			// Explode bombs placed three rounds ago
			else {
				removeBombsFromGrid(bombGrid, bombs.pop());
			}

			currentRound++;
		}

		return getBombGridTextRepresentation(bombGrid);
	}

	// Via some experimentation, it was determined that bomb placement devolves into a
	//	cycle that begins with the fifth round, and repeats every four rounds after that
	private int calculateEffectiveTotalComputationRounds(int totalRounds) {
		int effectiveRounds = totalRounds;

		if(totalRounds >= CYCLE_START_ROUND) {
			final int roundsPastFifthRound = (totalRounds - CYCLE_START_ROUND) %
					ROUNDS_IN_PATTERN_CYCLE;
			effectiveRounds = CYCLE_START_ROUND + roundsPastFifthRound;
		}
		return effectiveRounds;
	}

	private BombGroup setConvertedBombs(String[] grid, Bomb[][] toSet) {
		List<Bomb> bombs = new ArrayList<>();

		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length(); j++) {
				final Bomb additionalBomb = new Bomb(i, j);
				toSet[i][j] = additionalBomb;
				if(grid[i].charAt(j) == BOMB_SYMBOL) {
					bombs.add(additionalBomb);

				}
				else {
					additionalBomb.deactivate();
				}
			}
		}
		return new BombGroup(bombs);
	}

	private BombGroup getBombsPlantedThisRound(Bomb[][] bombGrid, int round) {
		List<Bomb> additionalBombs = new ArrayList<>();

		for(int i = 0; i < bombGrid.length; i++) {
			for(int j = 0; j < bombGrid[0].length; j++) {
				if( ! bombGrid[i][j].isActive()) {
					final Bomb additionalBomb = new Bomb(i, j);
					additionalBombs.add(additionalBomb);
					bombGrid[i][j] = additionalBomb;
				}
			}
		}

		return new BombGroup(additionalBombs);
	}

	private void removeBombsFromGrid(Bomb[][] bombGrid, BombGroup group) {
		group.getBombs().stream()
				.filter(Bomb::isActive)
				.collect(Collectors.toList())
				.forEach(bomb -> removeBombsForSingleBombEffect(bombGrid, bomb));
	}

	private void removeBombsForSingleBombEffect(Bomb[][] bombGrid, Bomb bomb) {
		final int x = bomb.getX();
		final int y = bomb.getY();
		bombGrid[x][y].deactivate();
		if(x + 1 < bombGrid.length) {
			bombGrid[x + 1][y].deactivate();
		}
		if(x - 1 >= 0) {
			bombGrid[x - 1][y].deactivate();
		}
		if(y + 1 < bombGrid[0].length) {
			bombGrid[x][y + 1].deactivate();
		}
		if(y - 1 >= 0) {
			bombGrid[x][y - 1].deactivate();
		}
	}

	private String getBombGridTextRepresentation(Bomb[][] bombGrid) {
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < bombGrid.length; i++) {
			if(output.length() > 0) {
				output.append(System.lineSeparator());
			}
			for(int j = 0; j < bombGrid[0].length; j++) {
				if(bombGrid[i][j].isActive()) {
					output.append(BOMB_SYMBOL);
				}
				else {
					output.append(EMPTY_SYMBOL);
				}
			}
		}
		return output.toString();
	}

	private static final Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		new TheBombermanGame().performSolution();
	}

	public void performSolution() {
		String[] rcn = scanner.nextLine().split(" ");

		int r = Integer.parseInt(rcn[0]);
		int c = Integer.parseInt(rcn[1]);
		int n = Integer.parseInt(rcn[2]);

		String[] grid = new String[r];

		for (int i = 0; i < r; i++) {
			String gridItem = scanner.nextLine();
			grid[i] = gridItem;
		}

		System.out.println(getGridAfterRounds(n, grid));

		scanner.close();
	}
}

