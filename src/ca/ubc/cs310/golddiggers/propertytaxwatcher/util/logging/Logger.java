package ca.ubc.cs310.golddiggers.propertytaxwatcher.util.logging;

import org.apache.log4j.Level;
import org.slf4j.helpers.MessageFormatter;

/**
 * sl4j over log4j Logger implementation rewritten to allow varargs instead of
 * using Object[] for multiple format arguments.
 * 
 * A wrapper over {@link org.apache.log4j.Logger org.apache.log4j.Logger} in
 * conforming to the {@link Logger} interface.
 * 
 * Note that the logging levels mentioned in this class refer to those defined
 * in the <a
 * href="http://logging.apache.org/log4j/docs/api/org/apache/log4j/Level.html">
 * org.apache.log4j.Level class.
 * 
 * The TRACE level was introduced in log4j version 1.2.12. In order to avoid
 * crashing the host application, in the case the log4j version in use predates
 * 1.2.12, the TRACE level will be mapped as DEBUG. .
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @modifiedBy Hubert Ngu
 */
public final class Logger {

	// The underlying log4j logger.
	private final transient org.apache.log4j.Logger logger;

	final static String FQCN = Logger.class.getName();

	// Does the log4j version in use recognize the TRACE level?
	// The trace level was introduced in log4j 1.2.12.
	final boolean traceCapable;

	// WARN: Log4jLoggerAdapter constructor should have only package access so
	// that
	// only Log4jLoggerFactory be able to create one.
	Logger(org.apache.log4j.Logger logger) {
		this.logger = logger;
		// this.name = logger.getName();
		this.traceCapable = isTraceCapable();
	}

	private boolean isTraceCapable() {
		try {
			logger.isTraceEnabled();
			return true;
		} catch (NoSuchMethodError e) {
			return false;
		}
	}

	/**
	 * Is this logger instance enabled for the TRACE level?
	 * 
	 * @return True if this Logger is enabled for level TRACE, false otherwise.
	 */
	public boolean isTraceEnabled() {
		return this.traceCapable ? this.logger.isTraceEnabled() : this.logger
				.isDebugEnabled();
	}

	/**
	 * Is this logger instance enabled for the DEBUG level?
	 * 
	 * @return True if this Logger is enabled for level DEBUG, false otherwise.
	 */
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/**
	 * Is this logger instance enabled for the INFO level?
	 * 
	 * @return True if this Logger is enabled for the INFO level, false
	 *         otherwise.
	 */
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/**
	 * Is this logger instance enabled for the WARN level?
	 * 
	 * @return True if this Logger is enabled for the WARN level, false
	 *         otherwise.
	 */
	public boolean isWarnEnabled() {
		return logger.isEnabledFor(Level.WARN);
	}

	/**
	 * Is this logger instance enabled for level ERROR?
	 * 
	 * @return True if this Logger is enabled for level ERROR, false otherwise.
	 */
	public boolean isErrorEnabled() {
		return logger.isEnabledFor(Level.ERROR);
	}

	/**
	 * Log a message at level TRACE.
	 * 
	 * @param msg
	 *            The message to be logged.
	 */
	public void trace(String msg) {
		this.trace(null, msg, (Object[]) null);
	}

	/**
	 * Logs an error at level TRACE.
	 * 
	 * @param e
	 *            The throwable e error to log.
	 */
	public void trace(Throwable e) {
		this.trace(e, e.getMessage(), (Object[]) null);
	}

	/**
	 * Log a message at the level TRACE.
	 * 
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void trace(String format, Object... args) {
		this.trace(null, format, args);
	}

	/**
	 * Log a message at level TRACE.
	 * 
	 * @param e
	 *            The throwable.
	 * @param msg
	 *            The message to be logged.
	 */
	public void trace(Throwable e, String msg) {
		this.trace(e, msg, (Object[]) null);
	}

	/**
	 * Log a message at the level TRACE
	 * 
	 * @param e
	 *            The throwable.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void trace(Throwable e, String format, Object... args) {
		if (this.isTraceEnabled()) {
			String message = args.length == 0 ? format : MessageFormatter
					.format(format, args).getMessage();
			logger.log(FQCN, traceCapable ? Level.TRACE : Level.DEBUG, message,
					e);
		}
	}

	/**
	 * Log a message at level DEBUG.
	 * 
	 * @param msg
	 *            The message to be logged.
	 */
	public void debug(String msg) {
		this.debug(null, msg, (Object[]) null);
	}

	/**
	 * Logs an error at level DEBUG.
	 * 
	 * @param e
	 *            The throwable e error to log.
	 */
	public void debug(Throwable e) {
		this.debug(e, e.getMessage(), (Object[]) null);
	}

	/**
	 * Log a message at the level DEBUG.
	 * 
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void debug(String format, Object... args) {
		this.debug(null, format, args);
	}

	/**
	 * Log a message at level DEBUG.
	 * 
	 * @param e
	 *            The throwable.
	 * @param msg
	 *            The message to be logged.
	 */
	public void debug(Throwable e, String msg) {
		this.debug(e, msg, (Object[]) null);
	}

	/**
	 * Log a message at the level DEBUG
	 * 
	 * @param e
	 *            The throwable.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void debug(Throwable e, String format, Object... args) {
		if (this.isTraceEnabled()) {
			String message = args.length == 0 ? format : MessageFormatter
					.format(format, args).getMessage();
			logger.log(FQCN, Level.DEBUG, message, e);
		}
	}

	/**
	 * Log a message at level INFO.
	 * 
	 * @param msg
	 *            The message to be logged.
	 */
	public void info(String msg) {
		this.info(null, msg, (Object[]) null);
	}

	/**
	 * Logs an error at level INFO.
	 * 
	 * @param e
	 *            The throwable e error to log.
	 */
	public void info(Throwable e) {
		this.info(e, e.getMessage(), (Object[]) null);
	}

	/**
	 * Log a message at the level INFO.
	 * 
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void info(String format, Object... args) {
		this.info(null, format, args);
	}

	/**
	 * Log a message at level INFO.
	 * 
	 * @param e
	 *            The throwable.
	 * @param msg
	 *            The message to be logged.
	 */
	public void info(Throwable e, String msg) {
		this.info(e, msg, (Object[]) null);
	}

	/**
	 * Log a message at the level INFO
	 * 
	 * @param e
	 *            The throwable.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void info(Throwable e, String format, Object... args) {
		if (this.isTraceEnabled()) {
			String message = args.length == 0 ? format : MessageFormatter
					.format(format, args).getMessage();
			logger.log(FQCN, Level.INFO, message, e);
		}
	}

	/**
	 * Log a message at level WARN.
	 * 
	 * @param msg
	 *            The message to be logged.
	 */
	public void warn(String msg) {
		this.warn(null, msg, (Object[]) null);
	}

	/**
	 * Logs an error at level WARN.
	 * 
	 * @param e
	 *            The throwable e error to log.
	 */
	public void warn(Throwable e) {
		this.warn(e, e.getMessage(), (Object[]) null);
	}

	/**
	 * Log a message at the level WARN.
	 * 
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void warn(String format, Object... args) {
		this.warn(null, format, args);
	}

	/**
	 * Log a message at level WARN.
	 * 
	 * @param e
	 *            The throwable.
	 * @param msg
	 *            The message to be logged.
	 */
	public void warn(Throwable e, String msg) {
		this.warn(e, msg, (Object[]) null);
	}

	/**
	 * Log a message at the level WARN
	 * 
	 * @param e
	 *            The throwable.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void warn(Throwable e, String format, Object... args) {
		if (this.isTraceEnabled()) {
			String message = args.length == 0 ? format : MessageFormatter
					.format(format, args).getMessage();
			logger.log(FQCN, Level.WARN, message, e);
		}
	}

	/**
	 * Log a message at level ERROR.
	 * 
	 * @param msg
	 *            The message to be logged.
	 */
	public void error(String msg) {
		this.error(null, msg, (Object[]) null);
	}

	/**
	 * Logs an error at level ERROR.
	 * 
	 * @param e
	 *            The throwable e error to log.
	 */
	public void error(Throwable e) {
		this.error(e, e.getMessage(), (Object[]) null);
	}

	/**
	 * Log a message at the level ERROR.
	 * 
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void error(String format, Object... args) {
		this.error(null, format, args);
	}

	/**
	 * Log a message at level ERROR.
	 * 
	 * @param e
	 *            The throwable.
	 * @param msg
	 *            The message to be logged.
	 */
	public void error(Throwable e, String msg) {
		this.error(e, msg, (Object[]) null);
	}

	/**
	 * Log a message at the level ERROR
	 * 
	 * @param e
	 *            The throwable.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The format arguments.
	 */
	public void error(Throwable e, String format, Object... args) {
		if (this.isTraceEnabled()) {
			String message = args.length == 0 ? format : MessageFormatter
					.format(format, args).getMessage();
			logger.log(FQCN, Level.ERROR, message, e);
		}
	}

}
