package eu.veldsoft.complica4.model.ia;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import eu.veldsoft.complica4.model.Util;

/**
 * Tests the methods in the AbstractArtificialIntelligence class.
 * 
 * @author Georgi Gospodinov
 * @see AbstractArtificialIntelligence
 */
public class AbstractArtificialIntelligenceTest {
	/**
	 * This object is used to test that the correct exception is thrown.
	 */
	@Rule
	public ExpectedException exception = ExpectedException.none();

	/**
	 * Test to see that the move method returns the correct value when the input
	 * is valid.
	 */
	@Test
	public void testMove() {
		int[][] state = new int[ArtificialIntelligence.STATE_COLS][ArtificialIntelligence.STATE_ROWS];

		int player = Util.PRNG.nextInt(ArtificialIntelligence.NUMBER_OF_PLAYERS + 1);
		ArtificialIntelligence ai = new AbstractArtificialIntelligence() {
		};

		/*
		 * The move method should return -1 when all inputs are valid.
		 */
		// TODO assertEquals(-1, ai.move(state, player));
	}

	/**
	 * Tests to see that an exception is thrown by the move method when the
	 * input state is null.
	 */
	@Test
	public void testMoveNullState() {
		int[][] state = null;
		int player = 0;
		ArtificialIntelligence ai = new AbstractArtificialIntelligence() {
		};
		exception.expect(NoValidMoveException.class);
		// TODO ai.move(state, player);
	}

	/**
	 * Tests to see that an exception is thrown by the move method when the
	 * columns dimension of the input state is more than number of columns on
	 * the board.
	 */
	@Test
	public void testMoveInvalidColumn() {
		int[][] state = new int[1 + Util.PRNG.nextInt(ArtificialIntelligence.STATE_COLS)
				+ ArtificialIntelligence.STATE_COLS][];
		int player = 0;
		ArtificialIntelligence ai = new AbstractArtificialIntelligence() {
		};
		exception.expect(NoValidMoveException.class);
		// TODO ai.move(state, player);
	}

	/**
	 * Tests to see that an exception is thrown by the move method when the rows
	 * dimension of the input state is more than the number of rows on the
	 * board.
	 */
	@Test
	public void testMoveInvalidRow() {
		int[][] state = new int[ArtificialIntelligence.STATE_COLS][1
				+ Util.PRNG.nextInt(ArtificialIntelligence.STATE_ROWS) + ArtificialIntelligence.STATE_ROWS];
		int player = 0;
		ArtificialIntelligence ai = new AbstractArtificialIntelligence() {
		};
		exception.expect(NoValidMoveException.class);
		// TODO ai.move(state, player);
	}

	/**
	 * Tests to see that an exception is thrown by the move method when the
	 * player input is more than the number of players in the game.
	 */
	@Test
	public void testMoveManyPlayers() {
		int[][] state = new int[ArtificialIntelligence.STATE_COLS][ArtificialIntelligence.STATE_ROWS];
		int player = 1 + Util.PRNG.nextInt() + ArtificialIntelligence.NUMBER_OF_PLAYERS;
		ArtificialIntelligence ai = new AbstractArtificialIntelligence() {
		};
		exception.expect(NoValidMoveException.class);
		// TODO ai.move(state, player);
	}

	/**
	 * Tests to see that an exception is thrown by the move method when the
	 * player input is a negative number.
	 */
	@Test
	public void testMoveNegativePlayer() {
		int[][] state = new int[ArtificialIntelligence.STATE_COLS][ArtificialIntelligence.STATE_ROWS];
		int player = 0 - Util.PRNG.nextInt();
		ArtificialIntelligence ai = new AbstractArtificialIntelligence() {
		};
		exception.expect(NoValidMoveException.class);
		// TODO ai.move(state, player);
	}

}
