package eu.veldsoft.complica4.model.ia;

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
	 * @param index
	 */
	private void tryMove(int[][] state, int player, int index) {
		if (state[index][0] != 0) {
			for (int j = state[index].length - 1; j > 0; j--) {
				state[index][j] = state[index][j - 1];
			}
			state[index][0] = player;
		} else {
			int j;
			for (j = 0; j < state[index].length; j++) {
				if (state[index][j] != 0) {
					break;
				}
			}
			state[index][j - 1] = player;
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

				for (k = 0; k < WIN_LINE_LENGTH
						&& (j + k) < state[i].length; k++) {
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
	 * 
	 * @param result
	 * @return
	 */
	private int addOneAndWin() {
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
		return -1;
	}

	/**
	 * 
	 * @return
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
		} else if ((result = addOneAndBlockOtherToWin()) != -1) {
		} else if ((result = addRnadom()) != -1) {
		}

		return result;
	}

}
