package eu.veldsoft.complica4.model;

/**
 * ANN training data.
 * 
 * @author Todor Balabanov
 */
public class Example {
	/**
	 * Playing player piece.
	 */
	public int piece;

	/**
	 * Played column.
	 */
	public int colunm;

	/**
	 * Example rank. If it is high the rank is worse.
	 */
	public int rank;

	/**
	 * Board state before piece to be played.
	 */
	public int state[][] = { {} };

	/**
	 * Common constructor.
	 * 
	 * @param piece
	 *            Playing player piece.
	 * @param colunm
	 *            Column to be played.
	 * @param rank
	 *            Example rank.
	 */
	private Example(int piece, int colunm, int rank) {
		super();
		this.piece = piece;
		this.colunm = colunm;
		this.rank = rank;
	}

	/**
	 * Numeric based constructor.
	 * 
	 * @param state
	 *            Board state as numeric values.
	 * @param piece
	 *            Playing player piece.
	 * @param colunm
	 *            Column to be played.
	 * @param rank
	 *            Example rank.
	 */
	public Example(int[][] state, int piece, int colunm, int rank) {
		this(piece, colunm, rank);
		this.state = new int[state.length][];
		for (int i = 0; i < state.length; i++) {
			this.state[i] = new int[state[i].length];
			for (int j = 0; j < state[i].length; j++) {
				this.state[i][j] = state[i][j];
			}
		}
	}

	/**
	 * Object based constructor.
	 * 
	 * @param state
	 *            Board state as object values.
	 * @param piece
	 *            Playing player piece.
	 * @param colunm
	 *            Column to be played.
	 * @param rank
	 *            Example rank.
	 */
	public Example(Piece[][] state, Piece piece, int colunm, int rank) {
		this(piece.getId(), colunm, rank);
		this.state = new int[state.length][];
		for (int i = 0; i < state.length; i++) {
			this.state[i] = new int[state[i].length];
			for (int j = 0; j < state[i].length; j++) {
				this.state[i][j] = state[i][j].getId();
			}
		}
	}

	/**
	 * Playing player piece getter.
	 * 
	 * @return Piece numeric value.
	 */
	public int getPiece() {
		return piece;
	}

	/**
	 * Playing player piece setter.
	 * 
	 * @param piece
	 *            Playing player piece value.
	 */
	public void setPiece(int piece) {
		this.piece = piece;
	}

	/**
	 * Column to play getter.
	 * 
	 * @return Index of the column to be played.
	 */
	public int getColunm() {
		return colunm;
	}

	/**
	 * Column to play setter.
	 * 
	 * @param colunm
	 *            Index of the column to be played.
	 */
	public void setColunm(int colunm) {
		this.colunm = colunm;
	}

	/**
	 * Example rank getter.
	 * 
	 * @return Rank of the example.
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Example rank setter.
	 * 
	 * @param rank
	 *            Rank of the example.
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Board state getter implemented as weak copy.
	 * 
	 * @return Reference to the board state matrix.
	 */
	public int[][] getState() {
		return state;
	}

	/**
	 * Board state setter implemented as weak copy.
	 * 
	 * @param state
	 *            Reference to the board state matrix.
	 */
	public void setState(int[][] state) {
		this.state = state;
	}

}
