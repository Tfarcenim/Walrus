package walrus.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import walrus.Constants;

public class Log {

	public static void info(String message, Object... params) {
		log(Level.INFO, message, params);
	}

	private static void log(Level logLevel, String message, Object... params) {
		LogManager.getLogger(Constants.MOD_ID).log(logLevel, message, params);
	}

}
