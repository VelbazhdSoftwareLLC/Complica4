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
	 * 
	 */
	static final int STATE_ROWS = 7;

	/**
	 * 
	 */
	static final int STATE_COLS = 5;

	/**
	 * 
	 */
	static final Set<Integer> STATE_VALUES = new HashSet<Integer>(
			Arrays.asList(new Integer[] { 0, 1, 2, 3, 4 }));

	/**
	 * 
	 */
	static final int WIN_LINE_LENGTH = 4;

	/**
	 * 
	 * @param state
	 * @param player
	 * @return
	 * @throws NoValidMoveException
	 */
	int move(int[][] state, int player) throws NoValidMoveException;
}
