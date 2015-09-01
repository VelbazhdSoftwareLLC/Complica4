package eu.veldsoft.complica4;

class Board {
	static final int ROWS = 7;
	static final int COLS = 5;

	private int turn = 0;

	private Piece pieces[][] = new Piece[COLS][ROWS];

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
	
	public Piece[][] getPieces() {
		return pieces;
	}

	public void next() {
		turn++;
	}
	
	public void reset() {
		turn = 0;
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
}
