package eu.veldsoft.complica4.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the methods in the Board class.
 * 
 * @author Georgi Gospodinov
 * @see Board
 */
public class BoardTest {
	
	//TODO fill in the comments (more depth)

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
		 * 
		 */
		assertEquals(0, board.getTurn());

		/*
		 * 
		 */
		board.next();
		assertEquals(1, board.getTurn());

		/*
		 * 
		 */
		board.next();
		assertEquals(2, board.getTurn());

		/*
		 * 
		 */
		board.next();
		assertEquals(3, board.getTurn());

		/*
		 * 
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
		 * ...
		 */
		assertFalse(board.isGameOver());
		
		/*
		 * ...
		 */
		Piece[][] pieces = board.getPieces();

		/* 
		 * Select random player. Empty is not a player.
		 */
		List <Piece> players = Arrays.asList(Piece.values());
		players.remove(Piece.EMPTY);
		Collections.shuffle(players);
		
		//TODO Random selection of wining line coordinate.
		
		pieces[0][0] = players.get(0);
		pieces[0][1] = Piece.PLAYER1;
		pieces[0][2] = Piece.PLAYER1;
		pieces[0][3] = Piece.PLAYER1;
		board.hasWinner();
		assertTrue(board.isGameOver());

		//TODO Test situations with different level of board fullness.
		
		/*
		 * Horisontal.
		 */
		
		/*
		 * Primary diagonal.
		 */
		
		/*
		 * Secondary diagonal.
		 */

		//TODO Test negative outcomes.
	}

	/**
	 * Tests to see if the pieces in the board are saved and returned correctly.
	 */
	@Test
	public void testGetPieces() {
		Piece[][] pieces = board.getPieces();

		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				assertEquals(Piece.EMPTY, pieces[i][j]);
			}
		}

		//TODO random positions
		pieces[0][1] = Piece.PLAYER1;
		pieces[1][2] = Piece.PLAYER2;
		pieces[2][3] = Piece.PLAYER3;
		pieces[3][4] = Piece.PLAYER4;
		assertEquals(Piece.PLAYER1, board.getPieces()[0][1]);
		assertEquals(Piece.PLAYER2, board.getPieces()[1][2]);
		assertEquals(Piece.PLAYER3, board.getPieces()[2][3]);
		assertEquals(Piece.PLAYER4, board.getPieces()[3][4]);
	}

	/**
	 * Tests to see if the state of the game is saved and returned correctly.
	 */
	@Test
	public void testGetState() {

		int[][] state = board.getState();

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state.length; j++) {
				assertEquals(0, state[i][j]);
			}
		}

		board.addTo(1, Piece.PLAYER1);
		board.addTo(2, Piece.PLAYER2);

		assertEquals(1, board.getState()[1][Board.ROWS - 1]);
		assertEquals(2, board.getState()[2][Board.ROWS - 1]);

	}

	/**
	 * Tests to see if the move history (session) is saved and returned
	 * correctly.
	 */
	@Test
	public void testGetSession() {
		// at start, session should be empty
		assertTrue(board.getSession().isEmpty());

		board.addTo(0, Piece.PLAYER1);
		board.addTo(0, Piece.PLAYER2);
		board.addTo(0, Piece.PLAYER3);

		List<Example> session = board.getSession();
		assertEquals(3, session.size());

		assertEquals(1, session.get(0).piece);
		assertEquals(2, session.get(1).piece);
		assertEquals(3, session.get(2).piece);

	}

	/**
	 * Tests to see if the move history (session) of the winner is correctly
	 * separated from the rest of the moves.
	 */
	@Test
	public void testGetWinnerSession() {

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
		List<Example> winnerSession = board.getWinnerSession();
		// two winners - Player 1 and Player 4, each won with four moves

		// something is wrong here....
		// see the System.out.print-s in testWinners()
		for (Example e : winnerSession)
			System.out.println(e.piece);

		fail("not fully implemented");

	}

	/**
	 * Tests to see if the turn is progressing correctly.
	 */
	@Test
	public void testNext() {
		int currentTurn = board.getTurn();
		board.next();
		assertEquals(currentTurn + 1, board.getTurn());
		board.next();
		assertEquals(currentTurn + 2, board.getTurn());
	}

	/**
	 * Tests to see if the board is completely reset when necessary.
	 */
	@Test
	public void testReset() {
		board.addTo(0, Piece.PLAYER1);
		board.addTo(1, Piece.PLAYER2);
		board.addTo(2, Piece.PLAYER3);
		board.addTo(3, Piece.PLAYER4);
		board.addTo(0, Piece.PLAYER1);
		board.addTo(0, Piece.PLAYER1);
		board.addTo(0, Piece.PLAYER1);

		assertTrue(board.hasWinner());
		assertTrue(board.isGameOver());

		board.reset();

		Piece[][] pieces = board.getPieces();
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				assertEquals(Piece.EMPTY, pieces[i][j]);
			}
		}
		assertFalse(board.hasWinner());
		assertFalse(board.isGameOver());
	}

	/**
	 * Tests to see if new pieces are added correctly to the board.
	 */
	@Test
	public void testAddTo() {
		board.addTo(0, Piece.PLAYER1);
		board.addTo(1, Piece.PLAYER2);
		board.addTo(2, Piece.PLAYER3);
		board.addTo(3, Piece.PLAYER4);
		board.addTo(0, Piece.PLAYER2);
		assertEquals(Piece.PLAYER1, board.getPieces()[0][Board.ROWS - 1]);
		assertEquals(Piece.PLAYER2, board.getPieces()[1][Board.ROWS - 1]);
		assertEquals(Piece.PLAYER3, board.getPieces()[2][Board.ROWS - 1]);
		assertEquals(Piece.PLAYER4, board.getPieces()[3][Board.ROWS - 1]);
		assertEquals(Piece.PLAYER2, board.getPieces()[0][Board.ROWS - 2]);
	}

	/**
	 * Tests to see if the program recognizes the winner.
	 */
	@Test
	public void testHasWinner() {

		board.addTo(0, Piece.PLAYER1);
		board.addTo(1, Piece.PLAYER2);
		board.addTo(2, Piece.PLAYER3);
		board.addTo(3, Piece.PLAYER4);

		board.addTo(1, Piece.PLAYER1);
		board.addTo(2, Piece.PLAYER2);
		board.addTo(3, Piece.PLAYER3);

		board.addTo(2, Piece.PLAYER1);
		board.addTo(3, Piece.PLAYER2);

		board.addTo(3, Piece.PLAYER1);

		assertTrue(board.hasWinner());
	}

	/**
	 * Tests to see if all winning pieces are recognized.
	 */
	@Test
	public void testWinners() {

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

		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				System.out.print(winners[i][j] + " ");
			}
			System.out.println();
		}

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
