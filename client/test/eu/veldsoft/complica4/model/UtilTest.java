package eu.veldsoft.complica4.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.encog.neural.networks.BasicNetwork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Georgi
 *
 * @see Util
 */
public class UtilTest {

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

	@Test
	public void testNewNetwork() {
		int inputSize = 3, hiddenSize = 2, outputSize = 3;
		BasicNetwork net = Util.newNetwork(inputSize, hiddenSize, outputSize);

		/*
		 * 0th layer (input)
		 */
		assertEquals(inputSize, net.getInputCount());

		/*
		 * 1st layer (hidden)
		 */
		assertEquals(hiddenSize, net.getLayerNeuronCount(1));

		/*
		 * 2nd layer (output)
		 */
		assertEquals(outputSize, net.getOutputCount());
	}

	@Test
	public void testLoadFromFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveToFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testLog() {
		fail("Not yet implemented");
	}

}
