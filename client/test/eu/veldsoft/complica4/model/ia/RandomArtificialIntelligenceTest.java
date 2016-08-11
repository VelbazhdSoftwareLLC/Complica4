package eu.veldsoft.complica4.model.ia;

import static org.junit.Assert.fail;

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
		int[][] state = new int[ArtificialIntelligence.STATE_COLS][ArtificialIntelligence.STATE_ROWS];
		int player = Util.PRNG.nextInt(ArtificialIntelligence.NUMBER_OF_PLAYERS + 1);

		/*
		 * Keep track of the last four values. They should not all be the same.
		 */
		int[] lastFourValues = new int[4];
		int lastIndex = lastFourValues.length - 1;

		/*
		 * Call the move method a hundred times to have a working set of values.
		 */
		RandomArtificialIntelligence randomAI = new RandomArtificialIntelligence();
		for (int i = 0; i < 100; i++) {
			int returnedValue = randomAI.move(state, player);

			/*
			 * Saves the first four values.
			 */
			if (i < 4) {
				lastFourValues[i] = returnedValue;
			} else {
				/*
				 * If all values are the same as the first value, fail the test.
				 */
				int firstValue = lastFourValues[0];
				int counter = 1;
				for (int j = 0; j < lastIndex; j++) {
					lastFourValues[j] = lastFourValues[j + 1];
					if (lastFourValues[j] == firstValue) {
						counter++;
					}
				}
				if (counter == lastFourValues.length) {
					fail("Last four values returned by the number generator are all the same!");
				}
				lastFourValues[lastIndex] = returnedValue;
			}
		}
	}

}
