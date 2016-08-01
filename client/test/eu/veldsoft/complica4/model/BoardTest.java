package eu.veldsoft.complica4.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods in the Board class.
 *
 * @author Georgi Gospodinov
 * @see Board
 */
public class BoardTest {

	// TODO Fill in the comments (more depth).

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
	 * Tests to see if the new turn is returned correctly.
	 */
	@Test
	public void testGetTurn() {
		/*
		 * Turn is zero before the start of the game.
		 */
		assertEquals(0, board.getTurn());

		/*
		 * The game starts with turn one.
		 */
		board.next();
		assertEquals(1, board.getTurn());

		/*
		 * The turn is incremented by exactly one.
		 */
		board.next();
		assertEquals(2, board.getTurn());

		/*
		 * Another test of the turn counter.
		 */
		board.next();
		assertEquals(3, board.getTurn());

		/*
		 * Reseting the board sets the turn counter back to zero.
		 */
		board.reset();
		assertEquals(0, board.getTurn());
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
		 * Take the state of pieces on the board.
		 */
		Piece[][] pieces = board.getPieces();

		/*
		 * Select random player. Empty is not a player.
		 */
		LinkedList<Piece> players = new LinkedList<Piece>(Arrays.asList(Piece.values()));
		players.remove(0);
		Collections.shuffle(players);

		int randomPlayer = new Random().nextInt(Board.NUMBER_OF_PLAYERS);

		/*
		 * Vertical.
		 */
		pieces[0][0] = players.get(randomPlayer);
		pieces[0][1] = players.get(randomPlayer);
		pieces[0][2] = players.get(randomPlayer);
		pieces[0][3] = players.get(randomPlayer);
		board.hasWinner();
		assertTrue(board.isGameOver());

		/*
		 * Horizontal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[0][0] = players.get(randomPlayer);
		pieces[1][0] = players.get(randomPlayer);
		pieces[2][0] = players.get(randomPlayer);
		pieces[3][0] = players.get(randomPlayer);
		board.hasWinner();
		assertTrue(board.isGameOver());

		/*
		 * Primary diagonal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[3][0] = players.get(randomPlayer);
		pieces[2][1] = players.get(randomPlayer);
		pieces[1][2] = players.get(randomPlayer);
		pieces[0][3] = players.get(randomPlayer);
		board.hasWinner();
		assertTrue(board.isGameOver());

		/*
		 * Secondary diagonal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[0][0] = players.get(randomPlayer);
		pieces[1][1] = players.get(randomPlayer);
		pieces[2][2] = players.get(randomPlayer);
		pieces[3][3] = players.get(randomPlayer);
		board.hasWinner();
		assertTrue(board.isGameOver());

		// TODO Test negative outcomes.

		// TODO Test situations with different levels of board fullness.
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
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				assertEquals(Piece.EMPTY, pieces[i][j]);
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
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state.length; j++) {
				assertEquals(0, state[i][j]);
			}
		}

		/*
		 * Select random player. Empty is not a player.
		 */
		LinkedList<Piece> players = new LinkedList<Piece>(Arrays.asList(Piece.values()));
		players.remove(0);
		Collections.shuffle(players);

		/*
		 * Add a piece to each column.
		 */
		for (int i = 0; i < Board.NUMBER_OF_PLAYERS; i++) {
			board.addTo(i, players.get(i)); // TODO Totally wrong. Counter i
											// should be something else.
			assertEquals(players.get(i).getId(), board.getState()[i][Board.ROWS - 1]);
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
		 * Put one piece for each player in a random column.
		 */
		int[] randomCols = new int[Board.NUMBER_OF_PLAYERS]; // TODO Rewrite
																// without
																// array.
		for (int i = 0; i < Board.NUMBER_OF_PLAYERS; i++) {
			randomCols[i] = new Random().nextInt(Board.NUMBER_OF_PLAYERS); // TODO
																			// Use
																			// Util.PRGN.
		}

		// TODO Longer game sessions.

		// for(Piece piece : Piece.values()) {
		// if(piece == Piece.EMPTY) {
		// continue;
		// }
		// // Do the job.
		// }

		board.addTo(randomCols[0], Piece.PLAYER1);
		board.addTo(randomCols[1], Piece.PLAYER2);
		board.addTo(randomCols[2], Piece.PLAYER3);
		board.addTo(randomCols[3], Piece.PLAYER4);

		/*
		 * There are now 4 turns in the session.
		 */
		List<Example> session = board.getSession();
		assertEquals(4, session.size());

		assertEquals(1, session.get(0).piece);
		assertEquals(2, session.get(1).piece);
		assertEquals(3, session.get(2).piece);
		assertEquals(4, session.get(3).piece);
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
		// TODO See the System.out.print-s in.
		// testWinners()
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
		int turn = board.getTurn();

		// TODO Test longer games as games with 100 moves.

		/*
		 * Next increments the turn counter by one.
		 */
		board.next();
		assertEquals(turn + 1, board.getTurn());

		board.next();
		assertEquals(turn + 2, board.getTurn());
	}

	/**
	 * Tests to see if the board is completely reset when necessary.
	 */
	@Test
	public void testReset() {
		/*
		 * Select random player. Empty is not a player.
		 */
		LinkedList<Piece> players = new LinkedList<Piece>(Arrays.asList(Piece.values()));
		players.remove(0);
		Collections.shuffle(players);

		/*
		 * Add a random number of pieces to random columns by random players.
		 */
		Random rand = new Random(); // TODO Use Util.PRNG
		int numberOfPieces = rand.nextInt(Board.COLS * Board.ROWS);
		for (int i = 0; i < numberOfPieces; i++) {
			board.addTo(rand.nextInt(Board.COLS), players.get(rand.nextInt(Board.NUMBER_OF_PLAYERS)));
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
		Random rand = new Random(); //TODO Use Util.PRNG.
		int numberOfPieces = rand.nextInt(Board.ROWS);
		int column = rand.nextInt(Board.COLS);

		/*
		 * Select random player. Empty is not a player.
		 */
		LinkedList<Piece> players = new LinkedList<Piece>(Arrays.asList(Piece.values()));
		players.remove(0);
		Collections.shuffle(players);
		Piece player = players.get(0);
		for (int i = 0; i < numberOfPieces; i++) {
			board.addTo(column, player);
		}

		Piece[][] pieces = board.getPieces();
		for (int i = 0; i < numberOfPieces; i++) {
			assertEquals(player, pieces[column][Board.ROWS - 1 - i]);
		}
		
		//TODO Test the cases when the column will be full and it will shift.
	}

	/**
	 * Tests to see if the program recognizes the winner.
	 */
	@Test
	public void testHasWinner() {
		/*
		 * Select random player. Empty is not a player.
		 */
		LinkedList<Piece> players = new LinkedList<Piece>(Arrays.asList(Piece.values()));
		players.remove(0);
		Collections.shuffle(players);

		int player = new Random().nextInt(Board.NUMBER_OF_PLAYERS); //TODO Use Util.PRNG.

		Piece[][] pieces = board.getPieces();

		//TODO Form vertical win combination in different positions.
		/*
		 * Vertical.
		 */
		pieces[0][0] = players.get(player);
		pieces[0][1] = players.get(player);
		pieces[0][2] = players.get(player);
		pieces[0][3] = players.get(player);
		assertTrue(board.hasWinner());

		//TODO
		/*
		 * Horizontal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[0][0] = players.get(player);
		pieces[1][0] = players.get(player);
		pieces[2][0] = players.get(player);
		pieces[3][0] = players.get(player);
		assertTrue(board.hasWinner());

		//TODO
		/*
		 * Primary diagonal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[3][0] = players.get(player);
		pieces[2][1] = players.get(player);
		pieces[1][2] = players.get(player);
		pieces[0][3] = players.get(player);
		assertTrue(board.hasWinner());

		//TODO
		/*
		 * Secondary diagonal.
		 */
		board.reset();
		pieces = board.getPieces();
		pieces[0][0] = players.get(player);
		pieces[1][1] = players.get(player);
		pieces[2][2] = players.get(player);
		pieces[3][3] = players.get(player);
		assertTrue(board.hasWinner());
		
		//TODO Test has winner when there is no winner.
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
		
		//TODO Fix the comments below.

		// should it be getWinners()? or defineWinners()?
		int[][] winners = board.winners();

		// PLAYER1 victory - bottom left to top right diagonal
		assertEquals(1, winners[0][Board.ROWS - 1]);
		assertEquals(1, winners[1][Board.ROWS - 2]);
		assertEquals(1, winners[2][Board.ROWS - 3]);
		assertEquals(1, winners[3][Board.ROWS - 4]);

		// PLAYER4 victory - bottom right to top left diagonal
		assertEquals(1, winners[3][Board.ROWS - 1]);
		assertEquals(1, winners[2][Board.ROWS - 2]);
		assertEquals(1, winners[1][Board.ROWS - 3]);
		assertEquals(1, winners[0][Board.ROWS - 4]);
		
//		 for ( int i = 0; i < Board.COLS; i++ ) { for ( int j = 0; j <
//		 Board.ROWS; j++ ) { System.out.print ( winners[i][j] + " " ); }
//		 System.out.println (); }

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

		// PLAYER3 victory - in a column
		assertEquals(1, winners[4][Board.ROWS - 1]);
		assertEquals(1, winners[4][Board.ROWS - 2]);
		assertEquals(1, winners[4][Board.ROWS - 3]);
		assertEquals(1, winners[4][Board.ROWS - 4]);

		// PLAYER2 victory - in a row
		assertEquals(1, winners[0][Board.ROWS - 1]);
		assertEquals(1, winners[1][Board.ROWS - 1]);
		assertEquals(1, winners[2][Board.ROWS - 1]);
		assertEquals(1, winners[3][Board.ROWS - 1]);
	}
}
