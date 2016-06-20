package eu.veldsoft.complica4.model;

/**
 * ANN training data.
 * 
 * @author Todor Balabanov
 */
public class Example {
	public int state[][] = { {} };
	public int piece;
	public int index;

	public Example(Piece[][] state, Piece piece, int index) {
		super();
		this.piece = piece.getId();
		this.index = index;
		this.state = new int[state.length][];
		for (int i = 0; i < state.length; i++) {
			this.state[i] = new int[state[i].length];
			for (int j = 0; j < state[i].length; j++) {
				this.state[i][j] = state[i][j].getId();
			}
		}
	}
}
