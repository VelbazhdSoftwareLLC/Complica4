package eu.veldsoft.complica4.model;

import static eu.veldsoft.complica4.Utility.getPlayerPieces;
import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import eu.veldsoft.complica4.Utility;

/**
 * Tests the methods in the Example class.
 *
 * @author Georgi Gospodinov
 * @see Example
 */
public class ExampleTest {

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
		/*
		 * Create a randomly filled board to use its state in the constructors.
		 */
		Board board = new Board();
		board.reset();
		Utility.fillRandomly(board);
		int[][] boardState = board.getState();
		Piece[][] state = new Piece[Board.COLS][Board.ROWS];
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				state[i][j] = Piece.values()[boardState[i][j]];
			}
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

	/**
	 * Tests to see if the piece is returned correctly.
	 */
	@Test
	public void testGetPiece() {
		/*
		 * The example object should be set to default values.
		 */
		assertEquals(DEFAULT_PIECE_ID, example.getPiece());

		/*
		 * Create a randomly filled board to use its state in the constructors.
		 */
		Board board = new Board();
		board.reset();
		Utility.fillRandomly(board);
		int[][] boardState = board.getState();
		Piece[][] state = new Piece[Board.COLS][Board.ROWS];
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				state[i][j] = Piece.values()[boardState[i][j]];
			}
		}

		/*
		 * Test with all different pieces with both constructors.
		 */
		LinkedList<Piece> pieces = Utility.getPlayerPieces();
		for (Piece piece : pieces) {
			example = new Example(DEFAULT_STATE, piece.getId(), DEFAULT_COLUMN, DEFAULT_RANK);
			assertEquals(piece.getId(), example.getPiece());

			example = new Example(state, piece, DEFAULT_COLUMN, DEFAULT_RANK);
			assertEquals(piece.getId(), example.getPiece());
		}
	}

	/**
	 * Tests to see if the state is returned correctly.
	 */
	@Test
	public void testGetState() {
		/*
		 * Test the getState method with both constructors.
		 */

		int[][] stateAsInts = new int[Board.COLS][Board.ROWS];

		/*
		 * Put a random amount of pieces on the board.
		 */
		int amount = Util.PRNG.nextInt(100);
		for (int i = 0; i < amount; i++) {
			stateAsInts[Util.PRNG.nextInt(Board.COLS)][Util.PRNG.nextInt(Board.ROWS)] = Util.PRNG.nextInt(Piece.maxId())
					+ 1;
		}

		example = new Example(stateAsInts, DEFAULT_PIECE_ID, DEFAULT_COLUMN, DEFAULT_RANK);

		int[][] exampleState = example.getState();
		for (int i = 0; i < exampleState.length; i++) {
			for (int j = 0; j < exampleState[i].length; j++) {
				assertEquals(stateAsInts[i][j], exampleState[i][j]);
			}
		}

		Piece[][] stateAsPieces = new Piece[Board.COLS][Board.ROWS];
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				stateAsPieces[i][j] = Piece.values()[stateAsInts[i][j]];
			}
		}

		example = new Example(stateAsPieces, DEFAULT_PIECE, DEFAULT_COLUMN, DEFAULT_RANK);

		exampleState = example.getState();
		for (int i = 0; i < exampleState.length; i++) {
			for (int j = 0; j < exampleState[i].length; j++) {
				assertEquals(stateAsInts[i][j], exampleState[i][j]);
			}
		}
	}

	/**
	 * Tests to see if the column is returned correctly.
	 */
	@Test
	public void testGetColumn() {
		/*
		 * The example object should be set to default values.
		 */
		assertEquals(DEFAULT_COLUMN, example.getColunm());

		/*
		 * Create a randomly filled board to use its state in the constructors.
		 */
		Board board = new Board();
		board.reset();
		Utility.fillRandomly(board);
		int[][] boardState = board.getState();
		Piece[][] state = new Piece[Board.COLS][Board.ROWS];
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				state[i][j] = Piece.values()[boardState[i][j]];
			}
		}

		/*
		 * Test with all different columns with both constructors.
		 */
		for (int column = 0; column < Board.COLS; column++) {
			example = new Example(DEFAULT_STATE, DEFAULT_PIECE_ID, column, DEFAULT_RANK);
			assertEquals(column, example.getColunm());

			example = new Example(state, DEFAULT_PIECE, column, DEFAULT_RANK);
			assertEquals(column, example.getColunm());
		}
	}

	/**
	 * Tests to see if the rank is returned correctly.
	 */
	@Test
	public void testGetRank() {
		/*
		 * The example object should be set to default values.
		 */
		assertEquals(DEFAULT_RANK, example.getRank());

		int rank;
		/*
		 * Run the test ten times with different values. A typical game does not
		 * last longer than 100 moves.
		 */
		for (int i = 0; i < 10; i++) {
			rank = Util.PRNG.nextInt(100);
			example.setRank(rank);
			assertEquals(rank, example.getRank());
		}
	}

	/**
	 * Test to see if the rank is assigned correctly.
	 */
	@Test
	public void testSetRank() {
		int rank;
		/*
		 * Run the test ten times with different values. A typical game does not
		 * last longer than 100 moves.
		 */
		for (int i = 0; i < 10; i++) {
			rank = Util.PRNG.nextInt(100);
			example.setRank(rank);
			assertEquals(rank, example.getRank());
		}
	}

}
