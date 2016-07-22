package eu.veldsoft.complica4.model.ia;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

import eu.veldsoft.complica4.model.Util;

/**
 * Bot based on artificial neural networks.
 * 
 * @author Todor Balabanov
 */
public class NeuralNetworkArtificialIntelligence extends
		AbstractArtificialIntelligence {

	/**
	 * Minimum value for normalization.
	 */
	private double min;

	/**
	 * Maximum value for normalization.
	 */
	private double max;

	/**
	 * Three layer artificial neural network.
	 */
	private BasicNetwork net = new BasicNetwork();

	/**
	 * Constructor with all needed parameters.
	 * 
	 * @param inputSize
	 *            Input layer size.
	 * @param hiddenSize
	 *            Hidden layer size.
	 * @param outputSize
	 *            Output layer size.
	 * @param minPiece
	 *            Minimum integer identifier of a piece.
	 * @param maxPiece
	 *            Maximum integer identifier of a piece.
	 */
	public NeuralNetworkArtificialIntelligence(BasicNetwork ann, int inputSize,
			int hiddenSize, int outputSize, int minPiece, int maxPiece) {
		min = minPiece;
		max = maxPiece;

		if (ann == null) {
			net = Util.newNetwork(inputSize, hiddenSize, outputSize);
		} else {
			net = ann;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int move(int[][] state, int player) throws NoValidMoveException {
		super.move(state, player);

		/*
		 * State matrix should be with valid dimensions.
		 */
		int size = 0;
		for (int i = 0; i < state.length; i++) {
			size += state[i].length;
		}

		/*
		 * Encode in the input the player who is playing.
		 */
		size += NUMBER_OF_PLAYERS;

		/*
		 * Check ANN input layer size.
		 */
		if (size != net.getInputCount()) {
			throw new NoValidMoveException();
		}

		/*
		 * Scale input in the range of [0.0-1.0].
		 */
		double input[] = new double[size];
		for (int i = 0, k = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++, k++) {
				input[k] = (state[i][j] - min) / (max - min);
			}
		}

		/*
		 * Mark the player who is playing.
		 */
		for (int i = input.length - NUMBER_OF_PLAYERS, p = 1; i < input.length; i++, p++) {
			if (player == p) {
				input[i] = 1;
			} else {
				input[i] = 0;
			}
		}

		/*
		 * Feed the ANN input. ANN calculation.
		 */
		MLData data = new BasicMLData(input);
		data = net.compute(data);

		/*
		 * Obtain the ANN output.
		 */
		double output[] = data.getData();

		/*
		 * Suggest move.
		 */
		int index = 0;
		for (int i = 0; i < output.length; i++) {
			if (output[i] > output[index]) {
				index = i;
			}
		}

		return index;
	}
}
