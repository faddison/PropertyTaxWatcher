package ca.ubc.cs310.golddiggers.propertytaxwatcher.util.logging;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.util.ConfigUtility;

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
		String console = ConfigUtility.getString("LOGGING", "console_level");
		String file = ConfigUtility.getString("LOGGING", "file_level");
		String logFolder = ConfigUtility.getString("LOGGING", "output_folder");
		String logFile = ConfigUtility.getString("LOGGING", "output_file");

		// Convert Strings into levels, if fail default to TRACE.
		Level consoleLevel = Level.toLevel(console, Level.TRACE);
		Level fileLevel = Level.toLevel(file, Level.TRACE);

		// In case the output_folder does not exist, create it.
		new File(logFolder).mkdir();

		// Grab the root logger of the system.
		// Note that all loggers inherit from the root logger
		org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger
				.getRootLogger();

		// Set the default logging level to TRACE.
		rootLogger.setLevel(Level.TRACE);

		// Create a console appender. This is only useful in development mode.
		rootLogger.addAppender(createConsoleAppender(consoleLevel));

		// Create a DailyRollingFileAppender. This is meant to log all activity
		// when deployed onto the app engine. If this fails then there will be
		// no log files generated, the system should not halt.
		try {
			rootLogger.addAppender(createDailyRollingFileAppender(fileLevel,
					logFolder + logFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates a ConsoleAppender. All log messages are printed to the output
	 * console. When the project is deployed onto app-engine the console
	 * appender will be useless.
	 * 
	 * @return A ConsoleAppender.
	 */
	private static ConsoleAppender createConsoleAppender(Level level) {
		// Create the layout of the log messages.
		PatternLayout layout = new PatternLayout(
				"%20d{yyyy-MM-dd HH:mm:ss} | %-5p | %-4M | %-40.40c{3} | %m%n");

		// Create the ConsoleAppender with the layout.
		ConsoleAppender appender = new ConsoleAppender(layout);

		// Set the threshold of the appender.
		appender.setThreshold(level);

		return appender;
	}

	/**
	 * Creates a DailyRollingFileAppender. This is a flat file that is written
	 * to the file system with all logging information.
	 * 
	 * @param logFile
	 *            The path of the log file.
	 * @return A DailyRollingFileAppender.
	 * @throws IOException
	 *             Throws IOException if there is a problem logging to the
	 *             specified logFile.
	 */
	private static DailyRollingFileAppender createDailyRollingFileAppender(
			Level level, String logFile) throws IOException {
		// Create the layout of the log messages.
		PatternLayout layout = new PatternLayout(
				"%20d{yyyy-MM-dd HH:mm:ss} | %-5p | %-4M | %-100.100l | %m%n");

		// Create the RollingFileAppender for the layout, output path, and time
		// to roll over. The roll over period is currently set to everyday at
		// midnight.
		DailyRollingFileAppender appender = new DailyRollingFileAppender(
				layout, logFile, "'.'yyy-MM-dd");

		// Set the threshold of the appender.
		appender.setThreshold(level);

		return appender;
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
