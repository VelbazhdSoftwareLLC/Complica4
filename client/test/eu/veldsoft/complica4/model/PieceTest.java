package eu.veldsoft.complica4.model;

import static org.junit.Assert.assertEquals;

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

//		piece = Piece.PLAYER1;
//		assertEquals(1, piece.getId());
//		
//		piece = Piece.PLAYER2;
//		assertEquals(2, piece.getId());
//		
//		piece = Piece.PLAYER3;
//		assertEquals(3, piece.getId());
//		
//		piece = Piece.PLAYER4;
//		assertEquals(4, piece.getId());
	}

	/**
	 * Tests to see if the minimum identifier is returned correctly.
	 */
	@Test
	public void testMinId() {
		assertEquals(0, Piece.minId());
		
		//TODO Check min<=max!
	}

	/**
	 * Tests to see if the maximum identifier is returned correctly.
	 */
	@Test
	public void testMaxId() {
		assertEquals(4, Piece.maxId());
		
		//TODO Check max>=min!
	}

}
