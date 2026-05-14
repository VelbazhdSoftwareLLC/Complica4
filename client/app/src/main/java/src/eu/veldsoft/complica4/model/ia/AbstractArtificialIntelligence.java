package eu.veldsoft.complica4.model.ia;

import eu.veldsoft.complica4.model.Board;

/**
 * Abstract A.I. class. It is used as parent of the real A.I. classes.
 * 
 * @author Todor Balabanov
 */
abstract class AbstractArtificialIntelligence implements ArtificialIntelligence {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int move(Board board, int player) throws NoValidMoveException {
		int[][] state = board.getState();

		if (state == null) {
			throw new NoValidMoveException();
		}
		if (state.length != STATE_COLS) {
			throw new NoValidMoveException();
		}
		for (int i = 0; i < state.length; i++) {
			if (state[i].length != STATE_ROWS) {
				throw new NoValidMoveException();
			}
		}

		if (STATE_VALUES.contains(player) == false) {
			throw new NoValidMoveException();
		}

		return -1;
	}

}
