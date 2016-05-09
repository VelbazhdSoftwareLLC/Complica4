package eu.veldsoft.complica4.model.ia;

import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

/**
 * Bot based on artificial neural networks.
 * 
 * @author Todor Balabanov
 */
public class NeuralNetworkArtificialIntelligence extends AbstractArtificialIntelligence {

	/**
	 * 
	 */
	private int min;

	/**
	 * 
	 */
	private int max;

	/**
	 * Three layer artificial neural network.
	 */
	private MultiLayerPerceptron net = null;

	/**
	 * 
	 * @param inputSize
	 * @param hiddenSize
	 * @param outputSize
	 * @param minPiece
	 * @param maxPiece
	 */
	public NeuralNetworkArtificialIntelligence(int inputSize, int hiddenSize,
			int outputSize, int minPiece, int maxPiece) {
		min = minPiece;
		max = maxPiece;
		net = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputSize,
				hiddenSize, outputSize);
	}

	/**
	 * 
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
		 * Check ANN input layer size.
		 */
		if (size != net.getInputsCount()) {
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
		 * Feed the ANN input.
		 */
		net.setInput(input);

		/*
		 * ANN calculation.
		 */
		net.calculate();

		/*
		 * Obtain the ANN output.
		 */
		double output[] = net.getOutput();

		/*
		 * Suggest move.
		 */
		int index = 0;
		for(int i=0; i<output.length; i++) {
			if(output[i] > output[index]) {
				index = i;
			}
		}

		return index;
	}
}
