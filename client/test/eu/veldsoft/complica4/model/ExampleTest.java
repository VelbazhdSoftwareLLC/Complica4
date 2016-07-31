package eu.veldsoft.complica4.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

/**
 * Tests the methods in the Example class.
 *
 * @author Georgi Gospodinov
 * @see Example
 */
public class ExampleTest {

    private static final int DEFAULT_PIECE_ID = Piece.PLAYER1.getId ();
    private static final int DEFAULT_COLUMN = 1;
    private static final int DEFAULT_RANK = 30;
    private static final int[][] DEFAULT_STATE = {{0}};

    private Example example = new Example ( DEFAULT_STATE, DEFAULT_PIECE_ID, DEFAULT_COLUMN, DEFAULT_RANK );

    /**
     * Reconstructs the example field with default values before each test is run.
     */
    @Before
    public void setUp () {
        example = new Example ( DEFAULT_STATE, DEFAULT_PIECE_ID, DEFAULT_COLUMN, DEFAULT_RANK );
    }

    /**
     * Tests to see if the common constructor assigns the correct values to the fields.
     */
    @Test
    public void testExampleIntIntInt () {

        Example example1 = new Example ( new int[][]{{}}, DEFAULT_PIECE_ID, DEFAULT_COLUMN, DEFAULT_RANK );
        Example example2 = new Example ( new Piece[][]{{}}, Piece.PLAYER1, DEFAULT_COLUMN, DEFAULT_RANK );

        assertEquals ( DEFAULT_PIECE_ID, example1.getPiece () );
        assertEquals ( DEFAULT_COLUMN, example1.getColunm () );
        assertEquals ( DEFAULT_RANK, example1.getRank () );

        assertEquals ( DEFAULT_PIECE_ID, example2.getPiece () );
        assertEquals ( DEFAULT_COLUMN, example2.getColunm () );
        assertEquals ( DEFAULT_RANK, example2.getRank () );

    }

    /**
     * Tests to see if the constructor with the state-as-integers assigns the correct values to the fields.
     */
    @Test
    public void testExampleIntArrayArrayIntIntInt () {

        int[][] state = new int[Board.COLS][Board.ROWS];

        Random rng = new Random ();
        /*
        Five random pairs of (column,row) to put random pieces in.
         */
        for ( int i = 0; i < 5; i++ ) {
            int c = rng.nextInt ( Board.COLS );
            int r = rng.nextInt ( Board.ROWS );

            state[c][r] = rng.nextInt ( Piece.maxId () + 1 );
        }

        example = new Example ( state, DEFAULT_PIECE_ID, DEFAULT_COLUMN, DEFAULT_RANK );

        assertEquals ( DEFAULT_PIECE_ID, example.getPiece () );
        assertEquals ( DEFAULT_COLUMN, example.getColunm () );
        assertEquals ( DEFAULT_RANK, example.getRank () );

        int[][] exampleState = example.getState ();

        for ( int i = 0; i < exampleState.length; i++ ) {
            for ( int j = 0; j < exampleState[i].length; j++ ) {
                assertEquals ( state[i][j], exampleState[i][j] );
            }
        }

    }

    /**
     * Tests to see if the constructor with the state-as-pieces assigns the correct values to the fields.
     */
    @Test
    public void testExamplePieceArrayArrayPieceIntInt () {
        Piece[][] state = new Piece[Board.COLS][Board.ROWS];

        Random rng = new Random ();
        /*
        Five random pairs of (column,row) to put random pieces in.
         */
        for ( int i = 0; i < 5; i++ ) {
            int c = rng.nextInt ( Board.COLS );
            int r = rng.nextInt ( Board.ROWS );

            state[c][r] = Piece.values ()[rng.nextInt ( Piece.maxId () + 1 )];
        }

        example = new Example ( state, Piece.values ()[DEFAULT_PIECE_ID], DEFAULT_COLUMN, DEFAULT_RANK );

        assertEquals ( DEFAULT_PIECE_ID, example.getPiece () );
        assertEquals ( DEFAULT_COLUMN, example.getColunm () );
        assertEquals ( DEFAULT_RANK, example.getRank () );

        int[][] exampleState = example.getState ();

        for ( int i = 0; i < exampleState.length; i++ ) {
            for ( int j = 0; j < exampleState[i].length; j++ ) {
                assertEquals ( state[i][j].getId (), exampleState[i][j] );
            }
        }
    }

    @Test
    public void testGetPiece () {
        fail ( "Not yet implemented" );
    }

    @Test
    public void testSetPiece () {
        fail ( "Not yet implemented" );
    }

    @Test
    public void testGetState () {
        fail ( "Not yet implemented" );
    }

    @Test
    public void testSetState () {
        fail ( "Not yet implemented" );
    }

    /*
    Example - get/set Column and Rank are not used
	or my code is not updated
	 */

    @Test
    public void testGetColumn () {
        fail ( "Not yet implemented" );
    }

    @Test
    public void testSetColumn () {
        fail ( "Not yet implemented" );
    }

    @Test
    public void testGetRank () {
        fail ( "Not yet implemented" );
    }

    @Test
    public void testSetRank () {
        fail ( "Not yet implemented" );
    }

}
