package eu.veldsoft.complica4.model;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Random;

import org.encog.neural.networks.BasicNetwork;

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
