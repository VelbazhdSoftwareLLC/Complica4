package eu.veldsoft.complica4;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Piece;
import eu.veldsoft.complica4.model.Util;

/**
 * This class contains some methods that are used in the test classes to help
 * generate random test cases.
 *
 * @author Georgi Gospodinov
 */
final public class Utility {
	/**
	 * The class is used only with its static functions.
	 */
	private Utility() {
	}

	/**
	 * Fill a board with a random amount of pieces. This method generates a
	 * random number for the amount of pieces to put for each player and then
	 * calls the other fillRandomly method.
	 *
	 * @param boardToFill
	 *            The board in which random pieces are put.
	 * @return The board itself, as returned by fillRandomly().
	 * @see Utility#fillRandomly(Board, int)
	 */
	public static Board fillRandomly(Board boardToFill) {
		/*
		 * A typical game ends before each player has put 30 pieces.
		 */
		int piecesPerPlayer = Util.PRNG.nextInt(30);
		return fillRandomly(boardToFill, piecesPerPlayer);
	}

	/**
	 * Fill a board with a given amount of pieces. This method is often called
	 * by the other fillRandomly method.
	 *
	 * @param boardToFill
	 *            The board in which random pieces are put.
	 * @param piecesPerPlayer
	 *            The amount of pieces to put for each player.
	 * @return The board itself.
	 * @see Board#getPieces()
	 * @see Utility#fillRandomly(Board)
	 */
	public static Board fillRandomly(Board boardToFill, int piecesPerPlayer) {
		LinkedList<Piece> players = getPlayerPieces();
		for (int i = 0; i < piecesPerPlayer; i++) {
			for (Piece player : players) {
				boardToFill.addTo(Util.PRNG.nextInt(Board.COLS), player);
			}
		}

		return boardToFill;
	}

	/**
	 * Put random pieces in about one third of the board. This method generates
	 * a random number around one third of the size of the board and calls the
	 * fillRandomly() method with it.
	 *
	 * @param boardToFill
	 *            The board in which random pieces are put.
	 * @return The board itself, as returned by fillRandomly().
	 * @see Utility#fillRandomly(Board, int)
	 */
	public static Board fillOneThird(Board boardToFill) {
		int piecesPerPlayer = Util.PRNG.nextInt(5) - 2 + Board.ROWS * Board.COLS / 3;
		return fillRandomly(boardToFill, piecesPerPlayer);
	}

	/**
	 * Put random pieces in about two thirds of the board. This method generates
	 * a random number around two thirds of the size of the board and calls the
	 * fillRandomly() method with it.
	 *
	 * @param boardToFill
	 *            The board in which random pieces are put.
	 * @return The board itself, as returned by fillRandomly().
	 * @see Utility#fillRandomly(Board, int)
	 */
	public static Board fillTwoThirds(Board boardToFill) {
		int piecesPerPlayer = Util.PRNG.nextInt(5) - 2 + 2 * Board.ROWS * Board.COLS / 3;
		return fillRandomly(boardToFill, piecesPerPlayer);
	}

	/**
	 * Put random pieces in the whole board. This method calls the
	 * fillRandomly() method with the full size of the board.
	 *
	 * @param boardToFill
	 *            The board in which random pieces are put.
	 * @return The board itself, as returned by fillRandomly().
	 * @see Utility#fillRandomly(Board, int)
	 */
	public static Board fillCompletely(Board boardToFill) {
		int piecesPerPlayer = Board.ROWS * Board.COLS;
		return fillRandomly(boardToFill, piecesPerPlayer);
	}

	/**
	 * Replaces random pieces on the board while there is a winner.
	 *
	 * @param board
	 *            The board to remove winners from.
	 * @return The board itself.
	 */
	public static Board removeWinners(Board board) {

		Piece[][] pieces = board.getPieces();
		int randX, randY;
		while (board.hasWinner() == true) {
			/*
			 * Find a non-empty piece.
			 */
			do {
				randX = Util.PRNG.nextInt(Board.COLS);
				randY = Util.PRNG.nextInt(Board.ROWS);
			} while (pieces[randX][randY] == Piece.EMPTY);

			/*
			 * Replace it with a random player's piece.
			 */
			pieces[randX][randY] = Piece.values()[Util.PRNG.nextInt(Board.NUMBER_OF_PLAYERS) + 1];
		}

		return board;
	}

	/**
	 * Replaces random pieces on the board while there is no winner.
	 *
	 * @param board
	 *            The board to remove winners from.
	 * @return The board itself.
	 */
	public static Board generateAtLeastOneWinner(Board board) {

		Piece[][] pieces = board.getPieces();
		int randX, randY;
		while (board.hasWinner() == false) {
			/*
			 * Find a non-empty piece.
			 */
			do {
				randX = Util.PRNG.nextInt(Board.COLS);
				randY = Util.PRNG.nextInt(Board.ROWS);
			} while (pieces[randX][randY] == Piece.EMPTY);

			/*
			 * Replace it with a random player's piece.
			 */
			pieces[randX][randY] = Piece.values()[Util.PRNG.nextInt(Board.NUMBER_OF_PLAYERS) + 1];
		}

		return board;
	}

	/**
	 * Create a LinkedList of all the player pieces. Then shuffle it. This
	 * method is often called with get(0) because a random player is needed.
	 * Since players are shuffled, the first element in the list will always be
	 * different.
	 *
	 * @return The shuffled List of Pieces.
	 */
	public static LinkedList<Piece> getPlayerPieces() {
		LinkedList<Piece> players = new LinkedList<Piece>(Arrays.asList(Piece.values()));

		/*
		 * Empty is not a player.
		 */
		players.remove(Piece.EMPTY);
		Collections.shuffle(players);

		return players;
	}

}
