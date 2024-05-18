package logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * The {@code LogFormatter} class provides a custom log message formatting.
 */
public class LogFormatter extends Formatter {
    /**
     * It formats log records to include the log level, timestamp, and message.
     * <p>If an exception is associated with the log record, it includes the exception details as well.
     *
     * @param record the log record to be formatted.
     * @return the formatted message of the logged record.
     */
    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(record.getLevel()).append(" ERROR\n")
                .append(new java.util.Date(record.getMillis())).append("\n\n")
                .append(record.getMessage());

        if (record.getThrown() != null)
            builder.append(record.getThrown());
        return builder.toString();
    }
}
