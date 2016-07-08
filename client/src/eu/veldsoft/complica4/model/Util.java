package eu.veldsoft.complica4.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

/**
 * Utilities class. It is used everywhere in the object model.
 * 
 * @author Todor Balabanov
 */
public class Util {
	/**
	 * Pseudo-random number generator instance.
	 */
	public static final Random PRNG = new Random();

	/**
	 * ANN file name.
	 */
	public static final String ANN_FILE_NAME = "ann.bin";

	/**
	 * ANN file name.
	 */
	public static final boolean VERBOSE_LOG = true;

	/**
	 * Find better way for giving value of this constant.
	 */
	public static final int ALARM_REQUEST_CODE = 0;

	/**
	 * Fixed number of examples to be trained in a single training.
	 */
	public static final int NUMBER_OF_SINGLE_TRAINING_EXAMPLES = 11;

	/**
	 * Create new artificial neural network.
	 * 
	 * @param inputSize
	 *            Size of the input layer.
	 * @param hiddenSize
	 *            Size of the hidden layer.
	 * @param outputSize
	 *            Size of the output layer.
	 * 
	 * @return Neural network created object.
	 */
	public static BasicNetwork newNetwork(int inputSize, int hiddenSize,
			int outputSize) {
		BasicNetwork net = new BasicNetwork();

		net.addLayer(new BasicLayer(null, true, inputSize));
		net.addLayer(new BasicLayer(new ActivationSigmoid(), true, hiddenSize));
		net.addLayer(new BasicLayer(new ActivationSigmoid(), false, outputSize));
		net.getStructure().finalizeStructure();
		net.reset();

		return net;
	}

	/**
	 * Load ANN from a file.
	 * 
	 * @param name
	 *            File name.
	 * 
	 * @return True if the loading is successful, false otherwise.
	 */
	public static BasicNetwork loadFromFile(String name) {
		BasicNetwork ann = null;

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					name));
			ann = (BasicNetwork) in.readObject();
			in.close();
		} catch (Exception ex) {
		}

		return ann;
	}

	/**
	 * Save ANN to a file.
	 * 
	 * @param ann
	 *            Artificial Neural Network object.
	 * @param name
	 *            File name.
	 */
	public static void saveToFile(BasicNetwork ann, String name) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(name));
			out.writeObject(ann);
			out.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * Log activity.
	 * 
	 * @param text
	 *            Text to log.
	 */
	public static void log(String text) {
		if (VERBOSE_LOG == false) {
			return;
		}

		System.out.println(text);
	}
}
