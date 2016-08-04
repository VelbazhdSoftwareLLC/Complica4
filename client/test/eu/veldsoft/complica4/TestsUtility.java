package eu.veldsoft.complica4;

import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Piece;
import eu.veldsoft.complica4.model.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * This class contains some methods that are used in the test classes to help
 * generate random test cases.
 *
 * @author Georgi Gospodinov
 */
public class TestsUtility {

	/**
	 * Fill a board with a random amount of pieces. This method generates a
	 * random number for the amount of pieces to put for each player and then
	 * calls the other fillRandomly method.
	 *
	 * @param boardToFill
	 *            The board in which random pieces are put.
	 * @see TestsUtility#fillRandomly(Board, int)
	 */
	public static void fillRandomly(Board boardToFill) {
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
	 * @see TestsUtility#fillRandomly(Board)
	 */
	public static void fillRandomly(Board boardToFill, int piecesPerPlayer) {
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
