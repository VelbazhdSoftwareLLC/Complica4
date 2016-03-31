package eu.veldsoft.complica4.model.ia;


/**
 * 
 * @author Todor Balabanov
 */
public interface ArtificialIntelligence  {
	/**
	 * 
	 * @param state
	 * @param player
	 * @return
	 * @throws NoValidMoveException
	 */
	int move(int[][] state, int player) throws NoValidMoveException;
}
