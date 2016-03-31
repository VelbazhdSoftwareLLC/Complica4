package eu.veldsoft.complica4.model.ia;

import eu.veldsoft.complica4.model.Util;

/**
 * 
 * @author Todor Balabanov
 */
public class RandomArtificialIntelligence implements ArtificialIntelligence {
	/**
	 * 
	 */
	@Override
	public int move(int[][] state, int player) {
		int result = -1;

		if (state == null) {
			throw new NoValidMoveException();
		}

		result = Util.PRNG.nextInt(state.length);

		return result;
	}
}
