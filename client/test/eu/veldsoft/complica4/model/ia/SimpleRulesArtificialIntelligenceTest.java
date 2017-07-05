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
		final int STATISTICAL_SIGNIFICANCE = 50000;

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
			// TODO histogram[ai.move(state, player)]++;
		}

		/*
		 * Calculate the mean value.
		 */
		double mean = 0;
		for (long amount : histogram) {
			mean += amount;
		}
		mean /= histogram.length;

		final double VARIANCE = 0.12;
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
		final double MAX_SD = 3050;
		assertTrue(deviation < MAX_SD);
	}

}
