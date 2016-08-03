package eu.veldsoft.complica4.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods in the Board class.
 *
 * @author Georgi Gospodinov
 * @see Board
 */
public class BoardTest {

	// TODO Fill in the comments with more depth.
	// This is done while writing the tests.

	/**
	 * Board object used in all test methods and the setUp method.
	 */
	private Board board = new Board();

	/**
	 * Fill a board with a random amount of pieces. This method generates a
	 * random number for the amount of pieces to put for each player and then
	 * calls the other fillRandomly method.
	 *
	 * @param boardToFill
	 *            The board in which random pieces are put.
	 * @see BoardTest#fillRandomly(Board, int)
	 */
	private static void fillRandomly(Board boardToFill) {
		/*
		 * A typical game ends before each player has put 30 pieces.
		 */
		int piecesPerPlayer = Util.PRNG.nextInt(30);
		fillRandomly(boardToFill, piecesPerPlayer);
	}

	/**
	 * Fill a board with a given amount of pieces. This method makes use of the
	 * fact that Board.getPieces() returns a pointer to the original array and
	 * not a deep copy. This method is often called by the other fillRandomly
	 * method.
	 *
	 * @param boardToFill
	 *            The board in which random pieces are put.
	 * @param piecesPerPlayer
	 *            The amount of pieces to put for each player.
	 * @see Board#getPieces()
	 * @see BoardTest#fillRandomly(Board)
	 */
	private static void fillRandomly(Board boardToFill, int piecesPerPlayer) {
		Piece[][] pieces = boardToFill.getPieces();
		LinkedList<Piece> players = getPlayerPieces();
		for (int i = 0; i < piecesPerPlayer; i++) {
			for (Piece player : players) {
				pieces[Util.PRNG.nextInt(Board.COLS)][Util.PRNG.nextInt(Board.ROWS)] = player;
			}
		}
	}

	/**
	 * Create a LinkedList of all the player pieces. Then shuffle it. This
	 * method is often called with get(0) because a random player is needed.
	 * Since players are shuffled, the first element in the list will always be
	 * different.
	 *
	 * @return The shuffled List of Pieces.
	 */
	private static LinkedList<Piece> getPlayerPieces() {
		LinkedList<Piece> players = new LinkedList<Piece>(Arrays.asList(Piece.values()));

		/*
		 * Empty is not a player.
		 */
		players.remove(Piece.EMPTY);
		Collections.shuffle(players);

		return players;
	}

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

		// TODO Test negative outcomes.
		/*
		 * Generate some random boards that cannot result in ending the game.
		 * Each player will have placed three pieces, so there cannot be a
		 * sequence of four.
		 */
		for (int i = 0; i < 20; i++) {
			board.reset();
			fillRandomly(board, 3);

			board.hasWinner();
			assertFalse(board.isGameOver());
		}
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

		LinkedList<Piece> players = getPlayerPieces();
		/*
		 * Add a piece to each column.
		 */
		for (int i = 0; i < Board.COLS; i++) {
			int index = Util.PRNG.nextInt(Board.NUMBER_OF_PLAYERS);
			board.addTo(i, players.get(index));
			assertEquals(players.get(index).getId(), board.getState()[i][Board.ROWS - 1]);
		}

		// TODO Test rows other than the bottom.
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
		/*
		 * Put combination for two simultaneous winners. First winner is Player
		 * 1. Second winner is player 4. The win combination appears after first
		 * four moves.
		 */
		board.addTo(0, Piece.PLAYER1);
		board.addTo(1, Piece.PLAYER2);
		board.addTo(2, Piece.PLAYER3);
		board.addTo(3, Piece.PLAYER4);

		board.addTo(0, Piece.PLAYER2);
		board.addTo(1, Piece.PLAYER1);
		board.addTo(2, Piece.PLAYER4);
		board.addTo(3, Piece.PLAYER3);

		board.addTo(0, Piece.PLAYER3);
		board.addTo(1, Piece.PLAYER4);
		board.addTo(2, Piece.PLAYER1);
		board.addTo(3, Piece.PLAYER2);

		board.addTo(0, Piece.PLAYER4);
		board.addTo(3, Piece.PLAYER1);

		board.hasWinner();
		List<Example> session = board.getWinnerSession();

		// TODO Something is wrong here.
		// See the System.out.print-s in testWinners().
		for (Example e : session) {
			System.out.println(e.getPiece());
		}

		fail("Not fully implemented!");
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
		 * player.
		 */
		int numberOfPieces = Util.PRNG.nextInt(Board.ROWS);
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

		// TODO Test the cases when the column will be full and it will shift.
	}

	/**
	 * Tests to see if the program recognizes the winner.
	 */
	@Test
	public void testHasWinner() {
		/*
		 * Select random player.
		 */
		Piece player = getPlayerPieces().get(0);

		Piece[][] pieces = board.getPieces();

		// TODO Form vertical win combination in different positions.
		/*
		 * Vertical.
		 */
		pieces[0][0] = player;
		pieces[0][1] = player;
		pieces[0][2] = player;
		pieces[0][3] = player;
		assertTrue(board.hasWinner());

		// TODO
		/*
		 * Horizontal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[0][0] = player;
		pieces[1][0] = player;
		pieces[2][0] = player;
		pieces[3][0] = player;
		assertTrue(board.hasWinner());

		// TODO
		/*
		 * Primary diagonal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[3][0] = player;
		pieces[2][1] = player;
		pieces[1][2] = player;
		pieces[0][3] = player;
		assertTrue(board.hasWinner());

		// TODO
		/*
		 * Secondary diagonal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[0][0] = player;
		pieces[1][1] = player;
		pieces[2][2] = player;
		pieces[3][3] = player;
		assertTrue(board.hasWinner());

		// TODO Test has winner when there is no winner.
	}

	/**
	 * Tests to see if all winning pieces are recognized.
	 */
	@Test
	public void testWinners() {
		// TODO take a deep look at this and Board.winners()

		board.addTo(0, Piece.PLAYER1);
		board.addTo(1, Piece.PLAYER2);
		board.addTo(2, Piece.PLAYER3);
		board.addTo(3, Piece.PLAYER4);

		board.addTo(0, Piece.PLAYER2);
		board.addTo(1, Piece.PLAYER1);
		board.addTo(2, Piece.PLAYER4);
		board.addTo(3, Piece.PLAYER3);

		board.addTo(0, Piece.PLAYER3);
		board.addTo(1, Piece.PLAYER4);
		board.addTo(2, Piece.PLAYER1);
		board.addTo(3, Piece.PLAYER2);

		board.addTo(0, Piece.PLAYER4);
		board.addTo(3, Piece.PLAYER1);

		int[][] winners = board.winners();

		/*
		 * Player one wins with a diagonal from bottom left to top right.
		 */
		assertEquals(1, winners[0][Board.ROWS - 1]);
		assertEquals(1, winners[1][Board.ROWS - 2]);
		assertEquals(1, winners[2][Board.ROWS - 3]);
		assertEquals(1, winners[3][Board.ROWS - 4]);

		/*
		 * Player four wins with a diagonal from bottom right to top left.
		 */
		assertEquals(1, winners[3][Board.ROWS - 1]);
		assertEquals(1, winners[2][Board.ROWS - 2]);
		assertEquals(1, winners[1][Board.ROWS - 3]);
		assertEquals(1, winners[0][Board.ROWS - 4]);

		// for (int i = 0; i < Board.COLS; i++) {
		// for (int j = 0; j < Board.ROWS; j++) {
		// System.out.print(winners[i][j] + " ");
		// }
		// System.out.println();
		// }

		board.reset();

		board.addTo(0, Piece.PLAYER2);
		board.addTo(1, Piece.PLAYER2);
		board.addTo(2, Piece.PLAYER2);
		board.addTo(3, Piece.PLAYER2);

		board.addTo(4, Piece.PLAYER3);
		board.addTo(4, Piece.PLAYER3);
		board.addTo(4, Piece.PLAYER3);
		board.addTo(4, Piece.PLAYER3);

		winners = board.winners();

		/*
		 * Player three wins with a column.
		 */
		assertEquals(1, winners[4][Board.ROWS - 1]);
		assertEquals(1, winners[4][Board.ROWS - 2]);
		assertEquals(1, winners[4][Board.ROWS - 3]);
		assertEquals(1, winners[4][Board.ROWS - 4]);

		/*
		 * Player two wins with a row.
		 */
		assertEquals(1, winners[0][Board.ROWS - 1]);
		assertEquals(1, winners[1][Board.ROWS - 1]);
		assertEquals(1, winners[2][Board.ROWS - 1]);
		assertEquals(1, winners[3][Board.ROWS - 1]);

		// for ( int i = 0; i < Board.COLS; i++ ) {
		// for ( int j = 0; j <
		// Board.ROWS; j++ ) {
		// System.out.print ( winners[i][j] + " " );
		// }
		// System.out.println ();
		// }
	}
}
