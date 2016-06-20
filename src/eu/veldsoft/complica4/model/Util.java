package eu.veldsoft.complica4.model;

import java.util.Random;

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
	 * Log activity. 
	 * 
	 * @param text Text to log.
	 */
	public static void log(String text) {
		if(VERBOSE_LOG == false) {
			return;
		}
		
		System.out.println(text);
	}
}
