package eu.veldsoft.complica4.model.ia;

import java.util.Arrays;

import org.junit.Test;

import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Util;

/**
 * Tests the methods in the RandomArtificialIntelligence class.
 * 
 * @author Georgi Gospodinov
 * @see RandomArtificialIntelligence
 */
public class RandomArtificialIntelligenceTest {

	/**
	 * Tests the randomness of the move method. Uses a histogram with a
	 * pre-calculated range of possible values for the mean and standard
	 * deviation.
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
		ArtificialIntelligence ai = new RandomArtificialIntelligence();

		/*
		 * Create a histogram of the data.
		 */
		long histogram[] = new long[Board.COLS];
		Arrays.fill(histogram, 0L);
		for (int i = 0; i < STATISTICAL_SIGNIFICANCE; i++) {
			histogram[ai.move(state, player)]++;
		}

		/*
		 * Calculate the mean value.
		 */
		double mean = 0;
		for (int i = 0; i < histogram.length; i++) {
			mean += histogram[i];
		}
		mean /= histogram.length;

		// final double EPSILON = 0.001;
		for (int i = 0; i < histogram.length; i++) {
			// TODO Math.abs(histogram[i]-mean)/STATISTICAL_SIGNIFICANCE >
			// EPSILON;
			// assertTrue(minimumMean < mean);
			// assertTrue(maximumMean > mean);
		}

		/*
		 * Calculate the standard deviation.
		 */
		double deviation = 0;
		for (int i = 0; i < histogram.length; i++) {
			deviation += Math.pow(histogram[i] - mean, 2);
		}
		deviation /= histogram.length;
		deviation = Math.sqrt(deviation);

		// final double MIN_SD = 1.40;
		// final double MAX_SD = 1.43;
		// assertTrue(minimumDeviation < deviation);
		// assertTrue(maximumDeviation > deviation);
	}

}
