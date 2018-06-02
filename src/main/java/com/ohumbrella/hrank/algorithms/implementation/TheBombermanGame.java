package com.ohumbrella.hrank.algorithms.implementation;

import java.lang.reflect.Array;
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

	// Complete the bomberMan function below.
	private String getGridAfterRounds(int rounds, String[] grid) {
		Bomb[][] bombGrid = new Bomb[grid.length][grid[0].length()];
		Deque<BombGroup> bombs = new ArrayDeque<>();
		bombs.addLast(setConvertedBombs(grid, bombGrid));

		int currentRound = 1;

		while(currentRound <= rounds) {
			final boolean evenRound = currentRound % 2 == 0;

			// Even round
			if(evenRound) {
				bombs.addLast(getBombsPlantedThisRound(bombGrid, currentRound));
			}
			// Odd round
			else if(currentRound >= 3) {
				removeBombsFromGrid(bombGrid, bombs.pop());
			}
			currentRound++;
		}

		return getBombGridTextRepresentation(bombGrid);
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

	//private static final Scanner scanner = new Scanner(System.in);
	private static final Scanner scanner = new Scanner(TheBombermanGame.class.getResourceAsStream("/TheBombermanGame11"));

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

