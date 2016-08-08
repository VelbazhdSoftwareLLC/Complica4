package eu.veldsoft.complica4.model;

import static eu.veldsoft.complica4.Utility.fillRandomly;
import static eu.veldsoft.complica4.Utility.getPlayerPieces;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import eu.veldsoft.complica4.Utility;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods in the Board class.
 *
 * @author Georgi Gospodinov
 * @see Board
 */
public class BoardTest {

	/**
	 * Board object used in all test methods and the setUp method.
	 */
	private Board board = new Board();

	/**
	 * A preparation before each test.
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		board.reset();
	}

	/**
	 * Tests to see if the turn is returned correctly.
	 */
	@Test
	public void testGetTurn() {
		/*
		 * The turn is tracked correctly for many moves. A typical game rarely
		 * lasts more than 100 moves.
		 */
		int moves = 100;
		for (int i = 0; i < moves; i++) {
			assertEquals(i, board.getTurn());
			board.next();
		}
		assertEquals(moves, board.getTurn());
	}

	/**
	 * Test to see if the program recognizes the end of the game.
	 */
	@Test
	public void testIsGameOver() {
		/*
		 * Game is not over at start.
		 */
		assertFalse(board.isGameOver());

		/*
		 * Select random player.
		 */
		Piece player = getPlayerPieces().get(0);
		Piece[][] pieces;

		/*
		 * Vertical wins. Game over should be recognized for all possible
		 * combinations of four in every column.
		 */
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS - 3; j++) {
				board.reset();
				fillRandomly(board);
				pieces = board.getPieces();

				pieces[i][j] = player;
				pieces[i][j + 1] = player;
				pieces[i][j + 2] = player;
				pieces[i][j + 3] = player;

				board.hasWinner();
				assertTrue(board.isGameOver());
			}
		}

		/*
		 * Horizontal wins. Game over should be recognized for all possible
		 * combinations of four in every row.
		 */
		for (int i = 0; i < Board.ROWS; i++) {

			for (int j = 0; j < Board.COLS - 3; j++) {
				board.reset();
				fillRandomly(board);
				pieces = board.getPieces();

				pieces[j][i] = player;
				pieces[j + 1][i] = player;
				pieces[j + 2][i] = player;
				pieces[j + 3][i] = player;

				board.hasWinner();
				assertTrue(board.isGameOver());
			}
		}

		/*
		 * Primary diagonal wins. Game over should be recognized for all
		 * possible combinations of four in every bottom left to top right
		 * diagonal.
		 */
		for (int i = 0; i < Board.COLS - 3; i++) {
			for (int j = 0; j < Board.ROWS - 3; j++) {
				board.reset();
				fillRandomly(board);
				pieces = board.getPieces();

				pieces[i + 3][j] = player;
				pieces[i + 2][j + 1] = player;
				pieces[i + 1][j + 2] = player;
				pieces[i][j + 3] = player;

				board.hasWinner();
				assertTrue(board.isGameOver());
			}
		}

		/*
		 * Secondary diagonal wins. Game over should be recognized for all
		 * possible combinations of four in every top left to bottom right
		 * diagonal.
		 */
		for (int i = 0; i < Board.COLS - 3; i++) {
			for (int j = 0; j < Board.ROWS - 3; j++) {
				board.reset();
				fillRandomly(board);
				pieces = board.getPieces();

				pieces[i][j] = player;
				pieces[i + 1][j + 1] = player;
				pieces[i + 2][j + 2] = player;
				pieces[i + 3][j + 3] = player;

				board.hasWinner();
				assertTrue(board.isGameOver());
			}
		}

		/*
		 * Game should not be over when there are no winners.
		 */
		// TODO The gameOver variable in the Board class is set to true in the
		// hasWinner() method.
		// That method is used when removing winners in Utility, and the
		// variable is never set to false again.
		// Maybe have that method set gameOver to false if it doesn't find
		// anything?
		board.reset();
		assertFalse(Utility.removeWinners(Utility.fillOneThird(board)).isGameOver());
		board.reset();
		assertFalse(Utility.removeWinners(Utility.fillTwoThirds(board)).isGameOver());
		board.reset();
		assertFalse(Utility.removeWinners(Utility.fillCompletely(board)).isGameOver());
	}

	/**
	 * Tests to see if the pieces in the board are saved and returned correctly.
	 */
	@Test
	public void testGetPieces() {
		Piece[][] pieces = board.getPieces();

		/*
		 * No pieces at start.
		 */
		for (Piece[] columnPieces : pieces) {
			for (Piece piece : columnPieces) {
				assertEquals(Piece.EMPTY, piece);
			}
		}

		/*
		 * Generate random positions to test with. Coordinates are distributed
		 * in two-dimensional array. X is in index 0, Y is in index 1.
		 */
		int[][] coordinates = new int[Board.NUMBER_OF_PLAYERS][2];
		for (int i = 0; i < Board.NUMBER_OF_PLAYERS; i++) {
			coordinates[i][0] = Util.PRNG.nextInt(Board.COLS);
			coordinates[i][1] = Util.PRNG.nextInt(Board.ROWS);
		}

		/*
		 * Check one position for each player.
		 */
		pieces[coordinates[0][0]][coordinates[0][1]] = Piece.PLAYER1;
		pieces[coordinates[1][0]][coordinates[1][1]] = Piece.PLAYER2;
		pieces[coordinates[2][0]][coordinates[2][1]] = Piece.PLAYER3;
		pieces[coordinates[3][0]][coordinates[3][1]] = Piece.PLAYER4;
		assertEquals(Piece.PLAYER1, board.getPieces()[coordinates[0][0]][coordinates[0][1]]);
		assertEquals(Piece.PLAYER2, board.getPieces()[coordinates[1][0]][coordinates[1][1]]);
		assertEquals(Piece.PLAYER3, board.getPieces()[coordinates[2][0]][coordinates[2][1]]);
		assertEquals(Piece.PLAYER4, board.getPieces()[coordinates[3][0]][coordinates[3][1]]);
	}

	/**
	 * Tests to see if the state of the game is saved and returned correctly.
	 */
	@Test
	public void testGetState() {
		int[][] state = board.getState();

		/*
		 * State is empty at start.
		 */
		for (int[] columnState : state) {
			for (int j = 0; j < state.length; j++) {
				assertEquals(0, columnState[j]);
			}
		}

		fillRandomly(board);
		state = board.getState();
		Piece[][] pieces = board.getPieces();

		/*
		 * The state array should match the ID-s of the pieces.
		 */
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				assertEquals(pieces[i][j].getId(), state[i][j]);
			}
		}
	}

	/**
	 * Tests to see if the move history (session) is saved and returned
	 * correctly.
	 */
	@Test
	public void testGetSession() {
		/*
		 * At start, session is empty.
		 */
		assertTrue(board.getSession().isEmpty());

		/*
		 * Put a random amount of pieces for each player in a random column.
		 * Game rarely lasts more than 100 turns. Divide those by the number of
		 * players.
		 */
		int amount = Util.PRNG.nextInt(100 / Board.NUMBER_OF_PLAYERS);
		for (int i = 0; i < amount; i++) {
			for (Piece piece : Piece.values()) {
				if (piece == Piece.EMPTY) {
					continue;
				}
				board.addTo(Util.PRNG.nextInt(Board.COLS), piece);
			}
		}

		/*
		 * The number of turns in the session is now 4 times the value of
		 * amount.
		 */
		List<Example> session = board.getSession();
		assertEquals(4 * amount, session.size());

		/*
		 * Check that the pieces in the session belong to the respective
		 * players. For each iteration of i, it is checked whether each player
		 * has put a piece.
		 */
		for (int i = 0; i < amount; i++) {
			for (int j = 0; j < Board.NUMBER_OF_PLAYERS; j++) {
				assertEquals(j + 1, session.get(i * 4 + j).getPiece());
			}
		}

	}

	/**
	 * Tests to see if the move history (session) of the winner is correctly
	 * separated from the rest of the moves.
	 */
	@Test
	public void testGetWinnerSession() {
		Utility.generateAtLeastOneWinner(Utility.fillOneThird(board)).hasWinner();
		List<Example> gameSession = board.getSession();
		List<Example> winnerSession = board.getWinnerSession();
		// TODO Account for multiple winners.
		int numberOfWinners = 1;
		assertEquals(numberOfWinners * gameSession.size() / 4, winnerSession.size());

		board.reset();
		Utility.generateAtLeastOneWinner(Utility.fillTwoThirds(board)).hasWinner();
		gameSession = board.getSession();
		winnerSession = board.getWinnerSession();
		// TODO Account for multiple winners.
		numberOfWinners = 1;
		assertEquals(numberOfWinners * gameSession.size() / 4, winnerSession.size());

		board.reset();
		Utility.generateAtLeastOneWinner(Utility.fillCompletely(board)).hasWinner();
		gameSession = board.getSession();
		winnerSession = board.getWinnerSession();
		// TODO Account for multiple winners.
		numberOfWinners = 1;
		assertEquals(numberOfWinners * gameSession.size() / 4, winnerSession.size());
	}

	/**
	 * Tests to see if the turn is progressing correctly.
	 */
	@Test
	public void testNext() {
		/*
		 * Next increments the turn counter by one. Test that for many moves. A
		 * typical game rarely lasts more than 100 moves.
		 */
		int moves = 100;
		for (int i = 0; i < moves; i++) {
			assertEquals(i, board.getTurn());
			board.next();
		}
		assertEquals(moves, board.getTurn());
	}

	/**
	 * Tests to see if the board is completely reset when necessary.
	 */
	@Test
	public void testReset() {
		LinkedList<Piece> players = getPlayerPieces();

		/*
		 * Add a random number of pieces to random columns by random players.
		 */
		int numberOfPieces = Util.PRNG.nextInt(Board.COLS * Board.ROWS);
		for (int i = 0; i < numberOfPieces; i++) {
			board.addTo(Util.PRNG.nextInt(Board.COLS), players.get(Util.PRNG.nextInt(Board.NUMBER_OF_PLAYERS)));
		}

		/*
		 * Length of the session should be the number of moves done by all
		 * players.
		 */
		assertEquals(numberOfPieces, board.getSession().size());

		/*
		 * Board is completely empty after reset.
		 */
		board.reset();
		Piece[][] pieces = board.getPieces();
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				assertEquals(Piece.EMPTY, pieces[i][j]);
			}
		}

		/*
		 * There must be no winner and the game must no be over.
		 */
		assertFalse(board.hasWinner());
		assertFalse(board.isGameOver());
	}

	/**
	 * Tests to see if new pieces are added correctly to the board.
	 */
	@Test
	public void testAddTo() {
		/*
		 * Add a random number of pieces to one random column by one random
		 * player. Games rarely last more than 100 turns.
		 */
		int numberOfPieces = Util.PRNG.nextInt(100 / Board.NUMBER_OF_PLAYERS);
		int column = Util.PRNG.nextInt(Board.COLS);

		/*
		 * Select a random player.
		 */
		Piece player = getPlayerPieces().get(0);
		for (int i = 0; i < numberOfPieces; i++) {
			board.addTo(column, player);
		}

		Piece[][] pieces = board.getPieces();
		for (int i = 0; i < numberOfPieces; i++) {
			assertEquals(player, pieces[column][Board.ROWS - 1 - i]);
		}
	}

	/**
	 * Tests to see if the program recognizes the winner.
	 */
	@Test
	public void testHasWinner() {
		/*
		 * No winner at start.
		 */
		assertFalse(board.hasWinner());

		/*
		 * Select random player.
		 */
		Piece player = getPlayerPieces().get(0);
		Piece[][] pieces;

		/*
		 * Vertical wins. Winner should be recognized for all possible
		 * combinations of four in every column.
		 */
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS - 3; j++) {
				board.reset();
				fillRandomly(board);
				pieces = board.getPieces();

				pieces[i][j] = player;
				pieces[i][j + 1] = player;
				pieces[i][j + 2] = player;
				pieces[i][j + 3] = player;

				assertTrue(board.hasWinner());
			}
		}

		/*
		 * Horizontal wins. Winner should be recognized for all possible
		 * combinations of four in every row.
		 */
		for (int i = 0; i < Board.ROWS; i++) {

			for (int j = 0; j < Board.COLS - 3; j++) {
				board.reset();
				fillRandomly(board);
				pieces = board.getPieces();

				pieces[j][i] = player;
				pieces[j + 1][i] = player;
				pieces[j + 2][i] = player;
				pieces[j + 3][i] = player;

				assertTrue(board.hasWinner());
			}
		}

		/*
		 * Primary diagonal wins. Winner should be recognized for all possible
		 * combinations of four in every bottom left to top right diagonal.
		 */
		for (int i = 0; i < Board.COLS - 3; i++) {
			for (int j = 0; j < Board.ROWS - 3; j++) {
				board.reset();
				fillRandomly(board);
				pieces = board.getPieces();

				pieces[i + 3][j] = player;
				pieces[i + 2][j + 1] = player;
				pieces[i + 1][j + 2] = player;
				pieces[i][j + 3] = player;

				assertTrue(board.hasWinner());
			}
		}

		/*
		 * Secondary diagonal wins. Winner should be recognized for all possible
		 * combinations of four in every top left to bottom right diagonal.
		 */
		for (int i = 0; i < Board.COLS - 3; i++) {
			for (int j = 0; j < Board.ROWS - 3; j++) {
				board.reset();
				fillRandomly(board);
				pieces = board.getPieces();

				pieces[i][j] = player;
				pieces[i + 1][j + 1] = player;
				pieces[i + 2][j + 2] = player;
				pieces[i + 3][j + 3] = player;

				assertTrue(board.hasWinner());
			}
		}

		/*
		 * There should be no winners after removal.
		 */
		board.reset();
		assertFalse(Utility.removeWinners(Utility.fillOneThird(board)).hasWinner());
		board.reset();
		assertFalse(Utility.removeWinners(Utility.fillTwoThirds(board)).hasWinner());
		board.reset();
		assertFalse(Utility.removeWinners(Utility.fillCompletely(board)).hasWinner());
	}

	/**
	 * Tests to see if all winning pieces are recognized.
	 */
	@Test
	public void testWinners() {
		int[][] winners = Utility.generateAtLeastOneWinner(Utility.fillRandomly(board)).winners();
		int minWinningPieces = Board.WIN_LINE_LENGTH;
		int maxWinningPieces = Board.COLS * Board.NUMBER_OF_PLAYERS;

		/*
		 * Count the amount of 1s in the 2-D array.
		 */
		int winningCount = 0;
		for (int[] row : winners) {
			for (int piece : row) {
				if (piece == 1) {
					winningCount++;
				}
			}
		}

		/*
		 * The count should be between the minimum and the maximum possible
		 * amounts of winning pieces.
		 */
		assertTrue(minWinningPieces <= winningCount);
		assertTrue(winningCount <= maxWinningPieces);
	}
}
