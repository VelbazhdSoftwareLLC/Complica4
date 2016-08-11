package eu.veldsoft.complica4.model.ia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import eu.veldsoft.complica4.model.Util;

/**
 * Tests the methods in the AbstractArtificialIntelligence class.
 * 
 * @author Georgi Gospodinov
 * 
 * @see AbstractArtificialIntelligence
 */
public class AbstractArtificialIntelligenceTest {

	/**
	 * Test to see that the move method returns the correct value and that it
	 * throws the custom exception when appropriate.
	 */
	@Test
	public void testMove() {
		int[][] state = null;
		int player = 0;
		AbstractArtificialIntelligence aai = new AbstractArtificialIntelligence() {
		};

		// TODO Show how I test the throw clause. Check how its done in
		// stackoverflow and github.
		/*
		 * The throw clauses are tested by calling the move method with
		 * incorrect values and if no exception is thrown, the fail method is
		 * called.
		 */
		try {
			aai.move(state, player);
			fail("Move completed successfully when state parameter was null!");
		} catch (NoValidMoveException e) {
		}

		state = new int[Util.PRNG.nextInt() + ArtificialIntelligence.STATE_COLS][];
		try {
			aai.move(state, player);
			fail("Move completed successfully when state column length was not correct!");
		} catch (NoValidMoveException e) {
		}

		state = new int[ArtificialIntelligence.STATE_COLS][Util.PRNG.nextInt() + ArtificialIntelligence.STATE_ROWS];
		try {
			aai.move(state, player);
			fail("Move completed successfully when state row length was not correct!");
		} catch (NoValidMoveException e) {
		}

		state = new int[ArtificialIntelligence.STATE_COLS][ArtificialIntelligence.STATE_ROWS];
		player = Util.PRNG.nextInt() + ArtificialIntelligence.NUMBER_OF_PLAYERS;
		try {
			aai.move(state, player);
			fail("Move completed successfully when player id was not correct!");
		} catch (NoValidMoveException e) {
		}

		/*
		 * When the input is correct, the method should return -1.
		 */
		player = Util.PRNG.nextInt(ArtificialIntelligence.NUMBER_OF_PLAYERS + 1);
		assertEquals(-1, aai.move(state, player));
	}

}
