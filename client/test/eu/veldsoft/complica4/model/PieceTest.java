package eu.veldsoft.complica4.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the methods is the Piece enumeration.
 * 
 * @author Georgi Gospodinov
 * 
 * @see Piece
 */
public class PieceTest {

	/**
	 * Tests to see if the identifier of each enumeration is returned correctly.
	 */
	@Test
	public void testGetId() {
		assertEquals(0, Piece.EMPTY.getId());
		assertEquals(1, Piece.PLAYER1.getId());
		assertEquals(2, Piece.PLAYER2.getId());
		assertEquals(3, Piece.PLAYER3.getId());
		assertEquals(4, Piece.PLAYER4.getId());
	}

	/**
	 * Tests to see if the minimum identifier is returned correctly.
	 */
	@Test
	public void testMinId() {
		assertEquals(0, Piece.minId());
		
		assertTrue (Piece.minId ()<Piece.maxId ());
	}

	/**
	 * Tests to see if the maximum identifier is returned correctly.
	 */
	@Test
	public void testMaxId() {
		assertEquals(4, Piece.maxId());

		assertTrue (Piece.maxId ()>Piece.minId ());
	}

}
