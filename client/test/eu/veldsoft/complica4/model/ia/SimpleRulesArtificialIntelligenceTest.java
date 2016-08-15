package eu.veldsoft.complica4.model.ia;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import eu.veldsoft.complica4.Utility;
import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Util;

/**
 * Tests the methods in the SimpleRulesArtificialIntelligence class.
 * 
 * @author Georgi Gospodinov
 * @see SimpleRulesArtificialIntelligence
 */
public class SimpleRulesArtificialIntelligenceTest {

	/**
	 * Tests the move method. Uses a histogram with a pre-calculated range of
	 * possible values for the mean and standard deviation.
	 */
	@Test
	public void testMove() {

		/*
		 * The size of the working set of values that is statistically
		 * significant and can detect errors.
		 */
		final int STATISTICAL_SIGNIFICANCE = 10000;

		Board board = new Board();
		int[][] state = null;
		int player;
		ArtificialIntelligence ai = new SimpleRulesArtificialIntelligence();

		/*
		 * Create a histogram of the data.
		 */
		long histogram[] = new long[Board.COLS];
		Arrays.fill(histogram, 0L);
		for (int i = 0; i < STATISTICAL_SIGNIFICANCE; i++) {
			/*
			 * Create a random board on which the rule is applied.
			 */
			board.reset();
			Utility.fillRandomly(board);
			state = board.getState();
			player = Util.PRNG.nextInt(ArtificialIntelligence.NUMBER_OF_PLAYERS + 1);
			histogram[ai.move(state, player)]++;
		}

		/*
		 * Calculate the mean value.
		 */
		int sum = 0;
		for (int i = 0; i < histogram.length; i++) {
			sum += histogram[i] * i;
		}
		double mean = sum * 1.0 / STATISTICAL_SIGNIFICANCE;
		System.out.println("mean = " + mean);
		final double minimumMean = 1.59;
		final double maximumMean = 1.63;
		assertTrue(minimumMean < mean);
		assertTrue(maximumMean > mean);

		/*
		 * Calculate the standard deviation.
		 */
		double deviation = 0;
		for (int i = 0; i < histogram.length; i++) {
			deviation += Math.pow(i - mean, 2) * histogram[i];
		}
		deviation /= STATISTICAL_SIGNIFICANCE;
		deviation = Math.sqrt(deviation);
		System.out.println("deviation = " + deviation);

		final double minimumDeviation = 1.40;
		final double maximumDeviation = 1.43;
		assertTrue(minimumDeviation < deviation);
		assertTrue(maximumDeviation > deviation);
	}

}
