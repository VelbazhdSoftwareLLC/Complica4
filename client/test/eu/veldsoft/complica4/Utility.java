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
	 * @return The board itself.
	 * @see Utility#fillRandomly(Board, int)
	 */
	public static Board fillRandomly(Board boardToFill) {
		/*
		 * A typical game ends before each player has put 30 pieces.
		 */
		int piecesPerPlayer = Util.PRNG.nextInt(30);
		fillRandomly(boardToFill, piecesPerPlayer);

		return boardToFill;
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
	 * @return The board itself.
	 * @see Board#getPieces()
	 * @see Utility#fillRandomly(Board)
	 */
	public static Board fillRandomly(Board boardToFill, int piecesPerPlayer) {
		Piece[][] pieces = boardToFill.getPieces();
		LinkedList<Piece> players = getPlayerPieces();
		for (int i = 0; i < piecesPerPlayer; i++) {
			for (Piece player : players) {
				pieces[Util.PRNG.nextInt(Board.COLS)][Util.PRNG.nextInt(Board.ROWS)] = player;
			}
		}

		return boardToFill;
	}

	// TODO Fill board in 1/3.
	// additionsNumber = PRNG.nextInt(5)-2 + ROWS*COS/3;
	// addTo()

	// TODO Fill board in 2/3.
	// additionsNumber = PRNG.nextInt(5)-2 + 2*ROWS*COS/3;
	// addTo()

	// TODO Fill board totally.
	// additionsNumber = ROWS*COLS
	// addTo()

	// TODO Remove winners.
	// while(board.hasWinners() == true) {
	// do{
	// randX = random;
	// randY = random;
	// }while(piece[randX][radnY] == EMPTY);
	//
	// pieces[randX][randY] = randPiece();
	// }

	// TODO Generate at least one winner.
	// while(board.hasWinners() == false) {
	// do{
	// randX = random;
	// randY = random;
	// }while(piece[randX][radnY] == EMPTY);
	//
	// pieces[randX][randY] = randPiece();
	// }

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
