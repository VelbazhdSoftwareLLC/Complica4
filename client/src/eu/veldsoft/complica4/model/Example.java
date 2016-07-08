package eu.veldsoft.complica4.model;

/**
 * ANN training data.
 * 
 * @author Todor Balabanov
 */
public class Example {
	public int piece;
	public int colunm;
	public int rank;
	public int state[][] = { {} };

	public Example(int[][] state, int piece, int colunm, int rank) {
		super();
		this.piece = piece;
		this.colunm = colunm;
		this.rank = rank;
		this.state = new int[state.length][];
		for (int i = 0; i < state.length; i++) {
			this.state[i] = new int[state[i].length];
			for (int j = 0; j < state[i].length; j++) {
				this.state[i][j] = state[i][j];
			}
		}
	}

	// TODO Use only one constructor.
	public Example(Piece[][] state, Piece piece, int colunm, int rank) {
		super();
		this.piece = piece.getId();
		this.colunm = colunm;
		this.rank = rank;
		this.state = new int[state.length][];
		for (int i = 0; i < state.length; i++) {
			this.state[i] = new int[state[i].length];
			for (int j = 0; j < state[i].length; j++) {
				this.state[i][j] = state[i][j].getId();
			}
		}
	}
}
