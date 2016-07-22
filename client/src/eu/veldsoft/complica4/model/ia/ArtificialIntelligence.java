package eu.veldsoft.complica4.model.ia;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Game artificial intelligence common interface.
 * 
 * @author Todor Balabanov
 */
public interface ArtificialIntelligence {
	/**
	 * Number of rows in the board.
	 */
	static final int STATE_ROWS = 7;

	/**
	 * Number of columns in the board.
	 */
	static final int STATE_COLS = 5;

	/**
	 * Total number of players on the board.
	 */
	static final int NUMBER_OF_PLAYERS = 4;

	/**
	 * All possible integer values on the board.
	 */
	static final Set<Integer> STATE_VALUES = new HashSet<Integer>(
			Arrays.asList(new Integer[] { 0, 1, 2, 3, 4 }));

	/**
	 * Size of the win line.
	 */
	static final int WIN_LINE_LENGTH = 4;

	/**
	 * Move suggestion by the A.I. module.
	 * 
	 * @param state
	 *            Board state as integer numbers matrix.
	 * @param player
	 *            Player identifier.
	 * 
	 * @return Column to be played or -1 if there is no acceptable solution.
	 * 
	 * @throws NoValidMoveException
	 *             If there is no valid move conditions of the board.
	 */
	int move(int[][] state, int player) throws NoValidMoveException;
}
