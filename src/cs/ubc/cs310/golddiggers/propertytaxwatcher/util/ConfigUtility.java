package cs.ubc.cs310.golddiggers.propertytaxwatcher.util;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.exception.ProperyTaxWatcherException;

/**
 * ConfigUtiltiy class. This is implemented using the Singleton pattern to allow
 * static access to a single configuration file.
 * 
 * If there exceeds one configuration file we should re-implement this class to
 * deal with that.
 * 
 * @author Hubert Ngu
 */
public class ConfigUtility {

	// TODO: Create a configuration file.
	private static final String configurationFile = "";

	private static Ini config;

	// Static initialization of the configuration file.
	static {
		config = new Ini();
		loadConfigurationFile(configurationFile);
	}

	/**
	 * Loads a configuration file.
	 * 
	 * @param fileName
	 *            The name of the file.
	 */
	public static void loadConfigurationFile(String fileName) {
		File file = new File(configurationFile);
		try {
			config.load(file);
		} catch (IOException e) {
			throw new ProperyTaxWatcherException(String.format(
					"Failed to load configuration file %s.",
					file.getAbsolutePath()));
		}
	}

	/**
	 * Gets the value in the configuration file given a section and a key.
	 * 
	 * @param section
	 *            The section.
	 * @param key
	 *            The key.
	 * @return The value.
	 */
	public static String getString(String section, String key) {
		return config.get(section, key);
	}

}
