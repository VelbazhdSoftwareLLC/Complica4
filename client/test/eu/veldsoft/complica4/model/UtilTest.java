package eu.veldsoft.complica4.model;

import static org.junit.Assert.*;

import org.encog.neural.networks.BasicNetwork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilTest {

	// TELL Todor that in Util.java lines 25 and 30 have the same comment. It is
	// ambiguous.

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

		assertEquals(inputSize, net.getInputCount());// 0th layer (input)
		assertEquals(hiddenSize, net.getLayerNeuronCount(1));// 1st layer
																// (hidden)
		assertEquals(outputSize, net.getOutputCount());// 2nd layer (output)
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
