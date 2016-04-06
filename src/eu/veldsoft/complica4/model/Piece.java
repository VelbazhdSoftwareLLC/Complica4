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

	/**
	 * 
	 * @return
	 */
	public static int getMinId() {
		int id = Piece.EMPTY.id;
		for (Piece p : values()) {
			if (p.id < id) {
				id = p.id;
			}
		}
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public static int getMaxId() {
		int id = Piece.EMPTY.id;
		for (Piece p : values()) {
			if (p.id > id) {
				id = p.id;
			}
		}
		return id;
	}
}
