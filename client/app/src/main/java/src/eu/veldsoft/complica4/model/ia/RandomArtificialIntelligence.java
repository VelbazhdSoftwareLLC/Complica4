package eu.veldsoft.complica4.model.ia;

import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Util;

/**
 * Random search meta heuristic is used as A.I.
 * 
 * https://en.wikipedia.org/wiki/Random_search
 * 
 * @author Todor Balabanov
 */
public class RandomArtificialIntelligence extends AbstractArtificialIntelligence {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int move(Board board, int player) {
		super.move(board, player);

		return Util.PRNG.nextInt(Board.COLS);
	}
}
