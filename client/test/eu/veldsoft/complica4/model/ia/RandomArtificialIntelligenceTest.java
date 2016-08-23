package eu.veldsoft.complica4.model.ia;

import static org.junit.Assert.assertTrue;

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
		for (long amount : histogram) {
			mean += amount;
		}
		mean /= histogram.length;

		final double VARIANCE = 0.01;
		for (long amount : histogram) {
			/*
			 * If this assertion fails, then there is a column that has been
			 * selected a lot more than the other four.
			 */
			assertTrue(Math.abs(amount - mean) / STATISTICAL_SIGNIFICANCE < VARIANCE);
		}

		/*
		 * Calculate the standard deviation.
		 */
		double deviation = 0;
		for (long amount : histogram) {
			deviation += Math.pow(amount - mean, 2);
		}
		deviation /= histogram.length;
		deviation = Math.sqrt(deviation);
		final double MAX_SD = 120;
		assertTrue(deviation < MAX_SD);
	}

}
