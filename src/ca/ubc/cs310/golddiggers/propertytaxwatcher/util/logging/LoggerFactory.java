package ca.ubc.cs310.golddiggers.propertytaxwatcher.util.logging;

import org.apache.log4j.Level;

import cs.ubc.cs310.golddiggers.propertytaxwatcher.util.ConfigUtility;

/**
 * LoggerFactory class. This class creates a new Logger object for use within
 * the system.
 * 
 * For example,
 * 
 * private static final Logger logger = LoggerFactory.getLogger(Property.class);
 * 
 * @author Hubert Ngu
 */
public class LoggerFactory {

	// Static initialization to give default logging options.
	static {
		// Grab the configuration options.
		String level = ConfigUtility.getString("LOGGING", "level");

		// Grab the root logger of the system.
		// Note that all loggers inherit from the root logger
		org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger
				.getRootLogger();

		// Set the level of the logger to the level specified in the
		// configuration file. If the level is not set in the configuration file
		// then default to DEBUG.
		rootLogger.setLevel(Level.toLevel(level, Level.DEBUG));

		// TODO: Add appenders and format them nicely.
		// rootLogger.addAppender(newAppender)

	}

	/**
	 * Gets a Logger object for the given class.
	 * 
	 * @param clazz
	 *            The class to get a Logger for.
	 * @return A Logger.
	 */
	public static Logger getLogger(Class<?> clazz) {
		return new Logger(org.apache.log4j.Logger.getLogger(clazz));
	}

}
