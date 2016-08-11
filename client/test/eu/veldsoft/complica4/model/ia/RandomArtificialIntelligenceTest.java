package eu.veldsoft.complica4.model.ia;

import static org.junit.Assert.fail;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;

import eu.veldsoft.complica4.model.Util;

/**
 * Tests the methods in the RandomArtificialIntelligence class.
 * 
 * @author Georgi Gospodinov
 * @see RandomArtificialIntelligence
 */
public class RandomArtificialIntelligenceTest {

	// TODO Is this random testing fine, or should a more complicated algorithm
	// be implemented?

	/**
	 * Tests the randomness of the move method.
	 */
	@Test
	public void testMove() {
		/*
		 * 
		 */
		final int OBSERVATION_LENGTH = 4;

		/*
		 * 
		 */
		final int STATISTICAL_SIGNIFICANCE = 100 - OBSERVATION_LENGTH;

		int[][] state = new int[ArtificialIntelligence.STATE_COLS][ArtificialIntelligence.STATE_ROWS];
		int player = Util.PRNG.nextInt(ArtificialIntelligence.NUMBER_OF_PLAYERS + 1);

		/*
		 * Keep track of the last four values. They should not all be the same.
		 */
		Deque<Integer> values = new LinkedList<Integer>();

		/*
		 * Call the move method a hundred times to have a working set of values.
		 */
		ArtificialIntelligence ai = new RandomArtificialIntelligence();

		/*
		 * Saves the initial values.
		 */
		for (int i = 0; i < OBSERVATION_LENGTH; i++) {
			values.addLast(ai.move(state, player));
		}

		/*
		 * Shift other values.
		 */
		for (int i = 0; i < STATISTICAL_SIGNIFICANCE; i++) {
			/*
			 * Drop first.
			 */
			Integer dropped = values.removeFirst();

			/*
			 * Count duplications.
			 */
			int counter = 1;
			for (Integer value : values) {
				if (dropped == value) {
					counter++;
				}
			}

			/*
			 * Add new.
			 */
			values.addLast(ai.move(state, player));

			/*
			 * If all values are the same as the first value, fail the test.
			 */
			if (counter == OBSERVATION_LENGTH) {
				fail("Last four values returned by the number generator are all the same!");
			}
		}

		// long histogram[] = new long[Board.COLS];
		// Arrays.fill(histogram, 0L);
		// for (int i = 0; i < STATISTICAL_SIGNIFICANCE; i++) {
		// histogram[ai.move(state, player)]++;
		// }

		// TODO 1. Mean value.
		// TODO 2. Standard deviation.
		// TODO 3. Fail if mean and standard deviation are unusual. Compare in
		// epsilon delta.
	}

}
