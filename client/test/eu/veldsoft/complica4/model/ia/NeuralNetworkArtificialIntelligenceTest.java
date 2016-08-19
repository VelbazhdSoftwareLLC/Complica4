package eu.veldsoft.complica4.model.ia;

import static org.junit.Assert.fail;

import org.encog.neural.networks.BasicNetwork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Piece;
import eu.veldsoft.complica4.model.Util;

/**
 * Tests the methods in the NeuralNetworkArtificialIntelligence class.
 * 
 * @author Georgi Gospodinov
 * @see NeuralNetworkArtificialIntelligence
 */
public class NeuralNetworkArtificialIntelligenceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests the move method of the the neural network.
	 */
	@Test
	public void testMove() {
		/*
		 * The size of the working set of values that is statistically
		 * significant and can detect errors.
		 */
		final int STATISTICAL_SIGNIFICANCE = 50000;

		int[][] state = new int[ArtificialIntelligence.STATE_COLS][ArtificialIntelligence.STATE_ROWS];
		int player = Util.PRNG.nextInt(ArtificialIntelligence.NUMBER_OF_PLAYERS + 1);

		// TODO Test with ANN from file and/or ANN from server.
		BasicNetwork net = Util.loadFromFile(Util.ANN_FILE_NAME);
		ArtificialIntelligence ai = new NeuralNetworkArtificialIntelligence(net,
				Board.COLS * Board.ROWS + Board.NUMBER_OF_PLAYERS, Board.COLS * Board.ROWS / 2, Board.COLS,
				Piece.minId(), Piece.maxId());

		// TODO Histogram.
	}

	/**
	 * Tests the constructor of the neural network.
	 */
	@Test
	public void testNeuralNetworkArtificialIntelligence() {
		fail("Not yet implemented");
	}

}
