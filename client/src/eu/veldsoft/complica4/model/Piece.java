package eu.veldsoft.complica4.model;

/**
 * Enumeration for board cells content.
 * 
 * @author Todor Balabanov
 */
public enum Piece {
	EMPTY(0), PLAYER1(1), PLAYER2(2), PLAYER3(3), PLAYER4(4);

	/**
	 * Board piece numeric identifier.
	 */
	private int id;

	/**
	 * Minimal numeric identifier.
	 * 
	 * @return Piece minimal numeric identifier.
	 */
	public static int minId() {
		int id = Piece.EMPTY.id;
		for (Piece p : values()) {
			if (p.id < id) {
				id = p.id;
			}
		}
		return id;
	}

	/**
	 * Maximum numeric identifier.
	 * 
	 * @return Piece maximum numeric identifier.
	 */
	public static int maxId() {
		int id = Piece.EMPTY.id;
		for (Piece p : values()) {
			if (p.id > id) {
				id = p.id;
			}
		}
		return id;
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            Numeric identifier.
	 */
	private Piece(int id) {
		this.id = id;
	}

	/**
	 * Numeric identifier getter.
	 * 
	 * @return Piece numeric identifier.
	 */
	public int getId() {
		return id;
	}
}
