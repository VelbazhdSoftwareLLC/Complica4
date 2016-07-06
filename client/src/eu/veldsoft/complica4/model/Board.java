package eu.veldsoft.complica4.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Object model of the game board.
 * 
 * @author Todor Balabanov
 */
public class Board {
	/**
	 * Number of rows constant.
	 */
	public static final int ROWS = 7;

	/**
	 * Number of columns constant.
	 */
	public static final int COLS = 5;

	/**
	 * Number of players on the board constant.
	 */
	public static final int NUMBER_OF_PLAYERS = 4;

	/**
	 * Size of the win line constant.
	 */
	static final int WIN_LINE_LENGTH = 4;

	/**
	 * Turns counter.
	 */
	private int turn = 0;

	/**
	 * Game over flag.
	 */
	private boolean gameOver = false;

	/**
	 * Pieces on the board object model.
	 */
	private Piece pieces[][] = new Piece[COLS][ROWS];

	/**
	 * Session history.
	 */
	private List<Example> session = new ArrayList<Example>();

	/**
	 * Check for horizontal win line.
	 * 
	 * @param i
	 *            Start x coordinate.
	 * @param j
	 *            Start y coordinate.
	 * 
	 * @return True if there is a win line, false otherwise.
	 */
	private boolean hasHorizontalLine(int i, int j) {
		Piece current = pieces[i][j];

		for (int k = 0; k < WIN_LINE_LENGTH; k++) {
			/*
			 * Keep array limits strict.
			 */
			if (i + k >= COLS) {
				return false;
			}

			if (pieces[i + k][j] != current) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check for vertical win line.
	 * 
	 * @param i
	 *            Start x coordinate.
	 * @param j
	 *            Start y coordinate.
	 * 
	 * @return True if there is a win line, false otherwise.
	 */
	private boolean hasVerticalLine(int i, int j) {
		Piece current = pieces[i][j];

		for (int k = 0; k < WIN_LINE_LENGTH; k++) {
			/*
			 * Keep array limits strict.
			 */
			if (j + k >= ROWS) {
				return false;
			}

			if (pieces[i][j + k] != current) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check for primary diagonal win line.
	 * 
	 * @param i
	 *            Start x coordinate.
	 * @param j
	 *            Start y coordinate.
	 * 
	 * @return True if there is a win line, false otherwise.
	 */
	private boolean hasFirstDiagonalLine(int i, int j) {
		Piece current = pieces[i][j];

		for (int k = 0; k < WIN_LINE_LENGTH; k++) {
			/*
			 * Keep array limits strict.
			 */
			if (i + k >= COLS) {
				return false;
			}
			if (j + k >= ROWS) {
				return false;
			}

			if (pieces[i + k][j + k] != current) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check for secondary diagonal win line.
	 * 
	 * @param i
	 *            Start x coordinate.
	 * @param j
	 *            Start y coordinate.
	 * 
	 * @return True if there is a win line, false otherwise.
	 */
	private boolean hasSecondDiagonalLine(int i, int j) {
		Piece current = pieces[i][j];

		for (int k = 0; k < WIN_LINE_LENGTH; k++) {
			/*
			 * Keep array limits strict.
			 */
			if (i - k < 0) {
				return false;
			}
			if (j + k >= ROWS) {
				return false;
			}

			if (pieces[i - k][j + k] != current) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Shift one of the columns one position down.
	 * 
	 * @param colunm
	 *            Index of the column to be shifted.
	 */
	private void shift(int colunm) {
		for (int j = pieces[colunm].length - 1; j > 0; j--) {
			pieces[colunm][j] = pieces[colunm][j - 1];
		}
		pieces[colunm][0] = Piece.EMPTY;
	}

	/**
	 * Add piece on the top of the column.
	 * 
	 * @param colunm
	 *            Index of the column to add to.
	 * @param piece
	 *            What kind of piece to be added.
	 */
	private void addTop(int colunm, Piece piece) {
		int j = -1;
		for (j = 0; j < pieces[colunm].length; j++) {
			if (pieces[colunm][j] != Piece.EMPTY) {
				j--;
				break;
			}
		}

		/*
		 * If the column is empty.
		 */
		// TODO Do it with exception.
		if (j == pieces[colunm].length) {
			j = pieces[colunm].length - 1;
		}

		pieces[colunm][j] = piece;
	}

	/**
	 * Current turn getter.
	 * 
	 * @return Turn number.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Is game over getter.
	 * 
	 * @return True if the game is over, false otherwise.
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Pieces on the board getter. Should be implemented as deep copy.
	 * 
	 * @return Two dimensional array with the pieces on the board.
	 */
	public Piece[][] getPieces() {
		return pieces;
	}

	/**
	 * State of the board getter.
	 * 
	 * @return Two dimensional array with the state of the board as integer
	 *         numbers.
	 */
	public int[][] getState() {
		int result[][] = new int[pieces.length][];

		for (int i = 0; i < pieces.length; i++) {
			result[i] = new int[pieces[i].length];
		}

		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				result[i][j] = pieces[i][j].getId();
			}
		}

		return result;
	}

	/**
	 * Session history getter.
	 * 
	 * @return All moves played.
	 */
	public List<Example> getSession() {
		return session;
	}

	/**
	 * Session history of the winner.
	 * 
	 * @return Moves only done by the winner.
	 */
	public List<Example> getWinnerSession() {
		List<Example> win = new ArrayList<Example>();

		/*
		 * There is no winner.
		 */
		if (gameOver == false) {
			return win;
		}

		/*
		 * Filtrate winner moves.
		 */
		Set<Integer> ids = new HashSet<Integer>();
		int[][] winners = winners();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				if (winners[i][j] == 1) {
					ids.add(pieces[i][j].getId());
				}
			}
		}

		/*
		 * Fill examples of the winners.
		 */
		for (Integer id : ids) {
			for (int s = 0; s < session.size(); s++) {
				if (s % NUMBER_OF_PLAYERS == (id - 1)) {
					Example example = session.get(s);
					example.rank = session.size();
					win.add(example);
				}
			}
		}

		return win;
	}

	public void next() {
		turn++;
	}

	public void reset() {
		turn = 0;
		gameOver = false;
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				pieces[i][j] = Piece.EMPTY;
			}
		}
		session.clear();
	}

	public void addTo(int colunm, Piece piece) throws RuntimeException {
		if (colunm < 0 || colunm >= COLS) {
			throw new RuntimeException("Invalid column: " + colunm);
		}

		if (piece == Piece.EMPTY) {
			throw new RuntimeException("Invalid move.");
		}

		if (pieces[colunm][0] != Piece.EMPTY) {
			shift(colunm);
		}

		/*
		 * Store session state. The example rank is unknown at this point in
		 * time.
		 */
		session.add(new Example(pieces, piece, colunm, 0));

		/*
		 * Do a valid move.
		 */
		addTop(colunm, piece);
	}

	public boolean hasWinner() {
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				if (pieces[i][j] == Piece.EMPTY) {
					continue;
				}

				if (hasHorizontalLine(i, j) == true) {
					gameOver = true;
					return true;
				}
				if (hasVerticalLine(i, j) == true) {
					gameOver = true;
					return true;
				}
				if (hasFirstDiagonalLine(i, j) == true) {
					gameOver = true;
					return true;
				}
				if (hasSecondDiagonalLine(i, j) == true) {
					gameOver = true;
					return true;
				}
			}
		}

		return false;
	}

	public int[][] winners() {
		int winners[][] = new int[COLS][ROWS];

		for (int i = 0; i < winners.length; i++) {
			for (int j = 0; j < winners[i].length; j++) {
				winners[i][j] = 0;
			}
		}

		for (int i = 0; i < winners.length; i++) {
			for (int j = 0; j < winners[i].length; j++) {
				if (hasHorizontalLine(i, j) == true) {
					for (int k = 0; k < WIN_LINE_LENGTH; k++) {
						winners[i + k][j] = 1;
					}
				}
				if (hasVerticalLine(i, j) == true) {
					for (int k = 0; k < WIN_LINE_LENGTH; k++) {
						winners[i][j + k] = 1;
					}
				}
				if (hasFirstDiagonalLine(i, j) == true) {
					for (int k = 0; k < WIN_LINE_LENGTH; k++) {
						winners[i + k][j + k] = 1;
					}
				}
				if (hasSecondDiagonalLine(i, j) == true) {
					for (int k = 0; k < WIN_LINE_LENGTH; k++) {
						winners[i - k][j + k] = 1;
					}
				}
			}
		}

		return winners;
	}
}
