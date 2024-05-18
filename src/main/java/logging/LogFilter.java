package logging;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * The {@code LogFilter} class implements a filter for logging records.
 */
public class LogFilter implements Filter {
    /**
     * Allows only records with severity levels equal to {@link Level#SEVERE}.
     *
     * @param record a LogRecord
     * @return {@code true} if, and only if, the level of the
     * logging record is {@link Level#SEVERE}.
     */
    @Override
    public boolean isLoggable(LogRecord record) {
        return record.getLevel().intValue() == Level.SEVERE.intValue();
    }
}
