package eu.veldsoft.complica4.model.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.veldsoft.complica4.model.Util;

/**
 * 
 * @author Todor Balabanov
 */
public class SimpleRulesArtificialIntelligence extends
		AbstractArtificialIntelligence {
	/**
	 * 
	 */
	private int[][] state = null;

	/**
	 * 
	 */
	private int player = -1;

	/**
	 * 
	 * @param original
	 * @return
	 */
	private int[][] copy(int[][] original) {
		int[][] result = new int[original.length][];

		for (int i = 0; i < original.length; i++) {
			result[i] = new int[original[i].length];
			for (int j = 0; j < original[i].length; j++) {
				result[i][j] = original[i][j];
			}
		}

		return result;
	}

	/**
	 * 
	 * @param state
	 * @param player
	 * @param colunm
	 */
	private void tryMove(int[][] state, int player, int colunm) {
		if (state[colunm][0] != 0) {
			for (int j = state[colunm].length - 1; j > 0; j--) {
				state[colunm][j] = state[colunm][j - 1];
			}
			state[colunm][0] = player;
		} else {
			int j;
			for (j = 0; j < state[colunm].length; j++) {
				if (state[colunm][j] != 0) {
					break;
				}
			}
			state[colunm][j - 1] = player;
		}
	}

	/**
	 * 
	 * @param state
	 * @param player
	 * @return
	 */
	private boolean isWinner(int[][] state, int player) {
		for (int i = 0, k; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				for (k = 0; k < WIN_LINE_LENGTH && (i + k) < state.length; k++) {
					if (state[i + k][j] != player) {
						break;
					}
				}
				if (k == WIN_LINE_LENGTH) {
					return true;
				}

				for (k = 0; k < WIN_LINE_LENGTH && (j + k) < state[i].length; k++) {
					if (state[i][j + k] != player) {
						break;
					}
				}
				if (k == WIN_LINE_LENGTH) {
					return true;
				}

				for (k = 0; k < WIN_LINE_LENGTH && (i + k) < state.length
						&& (j + k) < state[i].length; k++) {
					if (state[i + k][j + k] != player) {
						break;
					}
				}
				if (k == WIN_LINE_LENGTH) {
					return true;
				}

				for (k = 0; k < WIN_LINE_LENGTH && (i - k) >= 0
						&& (j + k) < state[i].length; k++) {
					if (state[i - k][j + k] != player) {
						break;
					}
				}
				if (k == WIN_LINE_LENGTH) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Check for combination of three pieces in a row.
	 * 
	 * @param nextState
	 *            Next state of the board.
	 * @param player
	 *            Index of the player.
	 * 
	 * @return True if there is three pieces in a row, false otherwise.
	 */
	private boolean hasThreeInRow(int[][] nextState, int player) {
		int subLineLength = WIN_LINE_LENGTH - 1;

		for (int i = 0, k; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				for (k = 0; k < subLineLength && (i + k) < state.length; k++) {
					if (state[i + k][j] != player) {
						break;
					}
				}
				if (k == subLineLength) {
					return true;
				}

				for (k = 0; k < subLineLength && (j + k) < state[i].length; k++) {
					if (state[i][j + k] != player) {
						break;
					}
				}
				if (k == subLineLength) {
					return true;
				}

				for (k = 0; k < subLineLength && (i + k) < state.length
						&& (j + k) < state[i].length; k++) {
					if (state[i + k][j + k] != player) {
						break;
					}
				}
				if (k == subLineLength) {
					return true;
				}

				for (k = 0; k < subLineLength && (i - k) >= 0
						&& (j + k) < state[i].length; k++) {
					if (state[i - k][j + k] != player) {
						break;
					}
				}
				if (k == subLineLength) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Check for combination of three pieces in a row.
	 * 
	 * @param nextState
	 *            Next state of the board.
	 * @param player
	 *            Index of the player.
	 * 
	 * @return True if there is three pieces in a row, false otherwise.
	 */
	private boolean hasTwoInRow(int[][] nextState, int player) {
		int subLineLength = WIN_LINE_LENGTH - 2;

		for (int i = 0, k; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				for (k = 0; k < subLineLength && (i + k) < state.length; k++) {
					if (state[i + k][j] != player) {
						break;
					}
				}
				if (k == subLineLength) {
					return true;
				}

				for (k = 0; k < subLineLength && (j + k) < state[i].length; k++) {
					if (state[i][j + k] != player) {
						break;
					}
				}
				if (k == subLineLength) {
					return true;
				}

				for (k = 0; k < subLineLength && (i + k) < state.length
						&& (j + k) < state[i].length; k++) {
					if (state[i + k][j + k] != player) {
						break;
					}
				}
				if (k == subLineLength) {
					return true;
				}

				for (k = 0; k < subLineLength && (i - k) >= 0
						&& (j + k) < state[i].length; k++) {
					if (state[i - k][j + k] != player) {
						break;
					}
				}
				if (k == subLineLength) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 
	 * @param result
	 * @return
	 */
	private int addOneAndWin() {
		/*
		 * Try to form four in a row.
		 */
		for (int i = 0; i < state.length; i++) {
			int[][] nextState = copy(state);
			tryMove(nextState, player, i);
			if (isWinner(nextState, player) == true) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 
	 * @return
	 */
	private int addOneAndBlockOtherToWin() {
		/*
		 * Put all players in a collection in order to shuffle it.
		 */
		List<Integer> ids = new ArrayList<Integer>();
		for (int p = 1; p <= NUMBER_OF_PLAYERS; p++) {
			if (p == player) {
				continue;
			}
			ids.add(p);
		}

		/*
		 * By shuffling it will not prefer the left most opponent to block.
		 */
		Collections.shuffle(ids);

		/*
		 * Try each opponent for direct win move.
		 */
		for (Integer p : ids) {
			for (int i = 0; i < state.length; i++) {
				int[][] nextState = copy(state);
				tryMove(nextState, p, i);
				if (isWinner(nextState, p) == true) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * Add one piece in order to to form three pieces in a row.
	 * 
	 * @return Index of a column to play or -1 if rule is not applicable.
	 */
	private int addOneForThree() {
		/*
		 * Try to for three in a row.
		 */
		List<Integer> solutions = new ArrayList<Integer>();
		for (int i = 0; i < state.length; i++) {
			int[][] nextState = copy(state);
			tryMove(nextState, player, i);
			if (hasThreeInRow(nextState, player) == true) {
				solutions.add(i);
			}
		}

		// TODO It is better to form three in a row where it will be possible to
		// form four in a row.

		if (solutions.size() == 0) {
			return -1;
		} else {
			Collections.shuffle(solutions);
			return solutions.get(0);
		}
	}

	/**
	 * Add one piece in order to block opponents to form three pieces in a row.
	 * 
	 * @return Index of a column to play or -1 if rule is not applicable.
	 */
	private int addOneForBlockOtherToFormThree() {
		/*
		 * Put all players in a collection in order to shuffle it.
		 */
		List<Integer> ids = new ArrayList<Integer>();
		for (int p = 1; p <= NUMBER_OF_PLAYERS; p++) {
			if (p == player) {
				continue;
			}
			ids.add(p);
		}

		/*
		 * By shuffling it will not prefer the left most opponent to block.
		 */
		Collections.shuffle(ids);

		/*
		 * Try each opponent for sub-direct win move.
		 */
		List<Integer> solutions = new ArrayList<Integer>();
		for (Integer p : ids) {
			for (int i = 0; i < state.length; i++) {
				int[][] nextState = copy(state);
				tryMove(nextState, p, i);
				if (hasThreeInRow(nextState, p) == true) {
					solutions.add(i);
				}
			}
		}

		// TODO It is better to block three in a row where it will be possible
		// to form four in a row.

		if (solutions.size() == 0) {
			return -1;
		} else {
			Collections.shuffle(solutions);
			return solutions.get(0);
		}
	}

	/**
	 * Add one piece in order to to form two pieces in a row.
	 * 
	 * @return Index of a column to play or -1 if rule is not applicable.
	 */
	private int addOneForTwo() {
		/*
		 * Try to for three in a row.
		 */
		List<Integer> solutions = new ArrayList<Integer>();
		for (int i = 0; i < state.length; i++) {
			int[][] nextState = copy(state);
			tryMove(nextState, player, i);
			if (hasTwoInRow(nextState, player) == true) {
				solutions.add(i);
			}
		}

		// TODO It is better to form three in a row where it will be possible to
		// form four in a row.

		if (solutions.size() == 0) {
			return -1;
		} else {
			Collections.shuffle(solutions);
			return solutions.get(0);
		}
	}

	/**
	 * Add one piece in order to block opponents to form two pieces in a row.
	 * 
	 * @return Index of a column to play or -1 if rule is not applicable.
	 */
	private int addOneForBlockOtherToFormTwo() {
		/*
		 * Put all players in a collection in order to shuffle it.
		 */
		List<Integer> ids = new ArrayList<Integer>();
		for (int p = 1; p <= NUMBER_OF_PLAYERS; p++) {
			if (p == player) {
				continue;
			}
			ids.add(p);
		}

		/*
		 * By shuffling it will not prefer the left most opponent to block.
		 */
		Collections.shuffle(ids);

		/*
		 * Try each opponent for sub-direct win move.
		 */
		List<Integer> solutions = new ArrayList<Integer>();
		for (Integer p : ids) {
			for (int i = 0; i < state.length; i++) {
				int[][] nextState = copy(state);
				tryMove(nextState, p, i);
				if (hasTwoInRow(nextState, p) == true) {
					solutions.add(i);
				}
			}
		}

		// TODO It is better to block three in a row where it will be possible
		// to form four in a row.

		if (solutions.size() == 0) {
			return -1;
		} else {
			Collections.shuffle(solutions);
			return solutions.get(0);
		}
	}

	/**
	 * Calculate pick and select around the pick.
	 * 
	 * @return Index of the column to play or -1 if there is no proper index.
	 */
	private int escapeGrouping() {
		/*
		 * Give chance to other rules to work.
		 */
		if (Util.PRNG.nextDouble() < 0.1) {
			return -1;
		}

		/*
		 * Zero counters.
		 */
		double total = 0;
		double cumulative[] = new double[state.length];
		for (int i = 0; i < cumulative.length; i++) {
			cumulative[i] = 0;
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j] != player) {
					cumulative[i]++;
					total++;
				}
			}
		}

		/*
		 * If there is no our own stones give up in this rule.
		 */
		if (total < 1.0D) {
			return -1;
		}

		/*
		 * Calculate percentage by columns.
		 */
		for (int i = 0; i < cumulative.length; i++) {
			cumulative[i] /= total;
		}

		/*
		 * Calculate cumulative function.
		 */
		for (int i = 1; i < cumulative.length; i++) {
			cumulative[i] += cumulative[i - 1];
		}

		/*
		 * Select index with particular probability.
		 */
		double number = Util.PRNG.nextDouble();
		for (int i = cumulative.length - 1; i >= 0; i--) {
			if (number > cumulative[i]) {
				return i + 1;
			}
		}

		return 0;
	}

	/**
	 * Select random column. It does not matter which one.
	 * 
	 * @return Index of a column to play.
	 */
	private int addRnadom() {
		return Util.PRNG.nextInt(state.length);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int move(int[][] state, int player) throws NoValidMoveException {
		super.move(state, player);

		/*
		 * Keep local object copies.
		 */
		this.state = state;
		this.player = player;

		/*
		 * Apply rule.
		 */
		int result = -1;
		if ((result = addOneAndWin()) != -1) {
			Util.log("Rule 1.");
		} else if ((result = addOneAndBlockOtherToWin()) != -1) {
			Util.log("Rule 2.");
		} else if ((result = addOneForThree()) != -1) {
			Util.log("Rule 3.");
		} else if ((result = addOneForBlockOtherToFormThree()) != -1) {
			Util.log("Rule 4.");
		} else if ((result = addOneForTwo()) != -1) {
			Util.log("Rule 5.");
		} else if ((result = addOneForBlockOtherToFormTwo()) != -1) {
			Util.log("Rule 6.");
		} else if ((result = escapeGrouping()) != -1) {
			Util.log("Rule 7.");
		} else if ((result = addRnadom()) != -1) {
			Util.log("Rule 8.");
		}

		return result;
	}
}
