package eu.veldsoft.complica4.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.encog.neural.networks.BasicNetwork;
import org.junit.Test;

/**
 * Tests the methods in the Util class.
 * 
 * @author Georgi Gospodinov
 *
 * @see Util
 */
public class UtilTest {

	/**
	 * Test to see if the respective method creates a new basic neural network
	 * and correctly assigns all three layer sizes.
	 */
	@Test
	public void testNewNetwork() {
		/*
		 * Create ANN with random layer sizes. Layer sizes are rarely going to
		 * be larger than 10 neurons each.
		 */
		int inputSize = Util.PRNG.nextInt(10);
		int hiddenSize = Util.PRNG.nextInt(10);
		int outputSize = Util.PRNG.nextInt(10);
		BasicNetwork net = Util.newNetwork(inputSize, hiddenSize, outputSize);

		/*
		 * All layers should have their sizes assigned correctly.
		 */
		assertEquals(inputSize, net.getInputCount());
		assertEquals(hiddenSize, net.getLayerNeuronCount(1));
		assertEquals(outputSize, net.getOutputCount());
	}

	@Test
	public void testLoadFromFile() {
		fail("Not yet implemented");
		// TODO Test is the same as in the testing of saving to a file.
	}

	@Test
	public void testSaveToFile() {
		/*
		 * Create ANN with random layer sizes. Layer sizes are rarely going to
		 * be larger than 10 neurons each.
		 */
		int inputSize = Util.PRNG.nextInt(10);
		int hiddenSize = Util.PRNG.nextInt(10);
		int outputSize = Util.PRNG.nextInt(10);
		BasicNetwork savedNet = Util.newNetwork(inputSize, hiddenSize, outputSize);

		// /*
		// * Build a string of random letters.
		// * Use that as file name.
		// */
		// int size = 10;
		// StringBuilder builder = new StringBuilder();
		// for (int i = 0; i < size; i++) {
		// builder.append((char) (Util.PRNG.nextInt(26) + 97));
		// }
		// TODO What file name should be used? And file path?
		// Util.saveToFile() is only called in NetworkTrainingService.java,
		// where it uses getFilesDir(), which comes from the super class -
		// Service.
		String fileName = Util.ANN_FILE_NAME;
		Util.saveToFile(savedNet, fileName);

		BasicNetwork loadedNet = Util.loadFromFile(fileName);
		/*
		 * The network should have three layers. And in each layer the number of
		 * neurons should be the same as the one saved.
		 */
		assertEquals(3, loadedNet.getLayerCount());
		assertEquals(savedNet.getInputCount(), loadedNet.getInputCount());
		assertEquals(savedNet.getLayerNeuronCount(1), loadedNet.getLayerNeuronCount(1));
		assertEquals(savedNet.getOutputCount(), loadedNet.getOutputCount());

		/*
		 * The weights should remain the same.
		 */
		assertTrue(savedNet.dumpWeights().equals(loadedNet.dumpWeights()));

		/*
		 * The saved and loaded networks should be the same. As defined by the
		 * encog java doc:
		 * "For them to be equal they must be of the same structure, and have the same matrix values."
		 */
		assertTrue(savedNet.equals(loadedNet));
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
		 * Logging adds a new line character, so it needs to be added before the
		 * assertion.
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
