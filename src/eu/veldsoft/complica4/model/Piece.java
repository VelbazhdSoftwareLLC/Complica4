package eu.veldsoft.complica4.model;

/**
 * 
 * @author Todor Balabanov
 */
public enum Piece {
	EMPTY(0), PLAYER1(1), PLAYER2(2), PLAYER3(3), PLAYER4(4);
	
	/**
	 * 
	 */
	private int id;

	/**
	 * 
	 * @param id
	 */
	private Piece(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
}
