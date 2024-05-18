package logging;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * A utility class for logging errors and exceptions to a Telegram chat
 * using the configured logging properties.
 * <p>The {@code TelegramLog} class initializes a logger with a custom {@link LogHandler}
 * and adds it to the root logger.
 */
public class TelegramLog {
    private static final Logger logger;

    static {
        String file = "src/main/resources/logging.properties";
        System.setProperty("java.util.logging.config.file", file);
        logger = Logger.getLogger(TelegramLog.class.getSimpleName());
        try {
            LogManager.getLogManager().readConfiguration();
        } catch (IOException e) {
            TelegramLog.logging(e.fillInStackTrace() + "\nFailed to load logging configuration");
        }
        Handler handler = new LogHandler();
        logger.addHandler(handler);
    }

    /**
     * Logs the specified error message at the {@link Level#SEVERE} level
     * using the configured logger.
     *
     * @param errorMessage the error message to be logged.
     */
    public static void logging(String errorMessage) {
        logger.log(Level.SEVERE, errorMessage);
    }

}
