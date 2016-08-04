package eu.veldsoft.complica4.model.ia;

import eu.veldsoft.complica4.model.Util;

/**
 * Random search meta heuristic is used as A.I.
 * 
 * @author Todor Balabanov
 */
public class RandomArtificialIntelligence extends AbstractArtificialIntelligence {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int move(int[][] state, int player) {
		super.move(state, player);

		return Util.PRNG.nextInt(state.length);
	}
}
