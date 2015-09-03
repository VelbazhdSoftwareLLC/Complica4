package eu.veldsoft.complica4;

class Board {
	static final int ROWS = 7;
	static final int COLS = 5;
	static final int WIN_LINE_LENGTH = 4;

	private int turn = 0;

	private boolean gameOver = false;

	private Piece pieces[][] = new Piece[COLS][ROWS];

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

	private void shift(int index) {
		for (int j = pieces[index].length - 1; j > 0; j--) {
			pieces[index][j] = pieces[index][j - 1];
		}
		pieces[index][0] = Piece.EMPTY;
	}

	private void addTop(int index, Piece piece) {
		int j = -1;
		for (j = 0; j < pieces[index].length; j++) {
			if (pieces[index][j] != Piece.EMPTY) {
				j--;
				break;
			}
		}

		/*
		 * If the column is empty.
		 */
		// TODO Do it with exception.
		if (j == pieces[index].length) {
			j = pieces[index].length - 1;
		}

		pieces[index][j] = piece;
	}

	public int getTurn() {
		return turn;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public Piece[][] getPieces() {
		return pieces;
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
	}

	public void addTo(int index, Piece piece) throws RuntimeException {
		if (index < 0 || index >= COLS) {
			throw new RuntimeException("Invalid column: " + index);
		}

		if (piece == Piece.EMPTY) {
			throw new RuntimeException("Invalid move.");
		}

		if (pieces[index][0] != Piece.EMPTY) {
			shift(index);
		}

		addTop(index, piece);
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
