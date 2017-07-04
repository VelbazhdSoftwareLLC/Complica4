package eu.veldsoft.complica4.model.ia;

import eu.veldsoft.complica4.model.Board;

/**
 * Alpha-beta pruning base A.I.
 * 
 * @author Todor Balabanov
 */
public class AlphaBetaArtificialIntelligence extends AbstractArtificialIntelligence {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int move(Board board, int player) throws NoValidMoveException {
		super.move(board, player);

		return 0;
	}
}
