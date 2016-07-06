package eu.veldsoft.complica4.model.ia;

/**
 * 
 * @author Todor Balabanov
 */
abstract class AbstractArtificialIntelligence implements ArtificialIntelligence {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int move(int[][] state, int player) throws NoValidMoveException {
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
