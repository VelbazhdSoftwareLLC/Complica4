package eu.veldsoft.complica4.model;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static eu.veldsoft.complica4.TestsUtility.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the methods in the Example class.
 *
 * @author Georgi Gospodinov
 * @see Example
 */
public class ExampleTest {

	// TODO There are three methods in Example that are not used: setPiece(),
	// setColumn(), setState().

	private static final Piece DEFAULT_PIECE = Piece.PLAYER1;
	private static final int DEFAULT_PIECE_ID = DEFAULT_PIECE.getId();
	private static final int DEFAULT_COLUMN = 1;
	private static final int DEFAULT_RANK = 30;
	private static final int[][] DEFAULT_STATE = { { 0 } };

	private Example example = new Example(DEFAULT_STATE, DEFAULT_PIECE_ID, DEFAULT_COLUMN, DEFAULT_RANK);

	/**
	 * Reconstructs the example field with default values before each test is
	 * run.
	 */
	@Before
	public void setUp() {
		example = new Example(DEFAULT_STATE, DEFAULT_PIECE_ID, DEFAULT_COLUMN, DEFAULT_RANK);
	}

	/**
	 * Tests to see if the common constructor assigns the correct values to the
	 * fields.
	 */
	@Test
	public void testExampleIntIntInt() {

		/*
		 * Generate some random values for the constructor. Games rarely last
		 * longer than 100 turns.
		 */
		int column = Util.PRNG.nextInt(Board.COLS);
		int rank = Util.PRNG.nextInt(100);
		Piece piece = getPlayerPieces().get(0);

		/*
		 * The constructor that needs to be tested has private access, so
		 * instead the two constructors that use it are called here. The
		 * differences they have can be neglected because their states are
		 * empty.
		 */
		Example example1 = new Example(new int[][] { {} }, piece.getId(), column, rank);
		Example example2 = new Example(new Piece[][] { {} }, piece, column, rank);

		assertEquals(piece.getId(), example1.getPiece());
		assertEquals(column, example1.getColunm());
		assertEquals(rank, example1.getRank());

		assertEquals(piece.getId(), example2.getPiece());
		assertEquals(column, example2.getColunm());
		assertEquals(rank, example2.getRank());

	}

	/**
	 * Tests to see if the constructor with the state-as-integers assigns the
	 * correct values to the fields.
	 */
	@Test
	public void testExampleIntArrayArrayIntIntInt() {

		int[][] state = new int[Board.COLS][Board.ROWS];

		/*
		 * Put a random amount of pieces on the board.
		 */
		int amount = Util.PRNG.nextInt(100);
		for (int i = 0; i < amount; i++) {
			state[Util.PRNG.nextInt(Board.COLS)][Util.PRNG.nextInt(Board.ROWS)] = Util.PRNG.nextInt(Piece.maxId()) + 1;
		}

		/*
		 * Generate some random values for the constructor. Games rarely last
		 * longer than 100 turns.
		 */
		int column = Util.PRNG.nextInt(Board.COLS);
		int rank = Util.PRNG.nextInt(100);
		int piece = getPlayerPieces().get(0).getId();

		example = new Example(state, piece, column, rank);

		assertEquals(piece, example.getPiece());
		assertEquals(column, example.getColunm());
		assertEquals(rank, example.getRank());

		int[][] exampleState = example.getState();

		for (int i = 0; i < exampleState.length; i++) {
			for (int j = 0; j < exampleState[i].length; j++) {
				assertEquals(state[i][j], exampleState[i][j]);
			}
		}

	}

	/**
	 * Tests to see if the constructor with the state-as-pieces assigns the
	 * correct values to the fields.
	 */
	@Test
	public void testExamplePieceArrayArrayPieceIntInt() {
		Piece[][] state = new Piece[Board.COLS][Board.ROWS];

		LinkedList<Piece> players = getPlayerPieces();
		/*
		 * Put a random amount of pieces on the board.
		 */
		int amount = Util.PRNG.nextInt(100);
		for (int i = 0; i < amount; i++) {
			state[Util.PRNG.nextInt(Board.COLS)][Util.PRNG.nextInt(Board.ROWS)] = players
					.get(Util.PRNG.nextInt(Board.NUMBER_OF_PLAYERS));
		}

		/*
		 * Generate some random values for the constructor. Games rarely last
		 * longer than 100 turns.
		 */
		int column = Util.PRNG.nextInt(Board.COLS);
		int rank = Util.PRNG.nextInt(100);
		Piece piece = getPlayerPieces().get(0);

		example = new Example(state, piece, column, rank);

		assertEquals(piece.getId(), example.getPiece());
		assertEquals(column, example.getColunm());
		assertEquals(rank, example.getRank());

		int[][] exampleState = example.getState();

		for (int i = 0; i < exampleState.length; i++) {
			for (int j = 0; j < exampleState[i].length; j++) {
				assertEquals(state[i][j].getId(), exampleState[i][j]);
			}
		}
	}

	@Test
	public void testGetPiece() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPiece() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetState() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetState() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetColumn() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetColumn() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRank() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRank() {
		fail("Not yet implemented");
	}

}
