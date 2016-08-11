package eu.veldsoft.complica4.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.encog.neural.networks.BasicNetwork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
		/*
		 * Build a string of random letters.
		 */
		int size = 10;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append((char) (Util.PRNG.nextInt(26) + 97));
		}

		String generatedMessage = builder.toString();
		System.out.println(generatedMessage);

		/*
		 * Hold the output.
		 */
		ByteArrayOutputStream outputHolder = new ByteArrayOutputStream();
		PrintStream newStream = new PrintStream(outputHolder);
		PrintStream oldStream = System.out;
		System.out.flush();
		System.setOut(newStream);

		/*
		 * The message logged should be the same as the one saved in the holder.
		 * Logging adds a new line characters, so it needs to be added before
		 * the assertion.
		 */
		Util.log(generatedMessage);
		String savedMessage = outputHolder.toString();
		generatedMessage += "\n";

		System.out.flush();
		System.setOut(oldStream);
		System.out.println(generatedMessage);
		System.out.println(savedMessage);

		assertTrue(generatedMessage.equals(savedMessage));
	}

}
