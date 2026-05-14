package eu.veldsoft.complica4.model;

/**
 * Invalid move exetpion.
 * 
 * @author Todor Balabanov
 */
@SuppressWarnings("serial")
public class InvalidMoveException extends RuntimeException {

	/**
	 * Constructor with textual explanation.
	 * 
	 * @param reason
	 *            Explanation text.
	 */
	public InvalidMoveException(String reason) {
		super(reason);
	}
}
