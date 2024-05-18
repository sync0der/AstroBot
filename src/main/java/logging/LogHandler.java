package logging;

import config.LoggingBotConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * The {@code LogHandler} class extends {@link java.util.logging.StreamHandler} to handle logging records.
 */
public class LogHandler extends StreamHandler {
    /**
     * Initializes the {@code LogHandler} with a {@link LogFilter} and a {@link LogFormatter}.
     * These components filter and format log records, respectively.
     */
    public LogHandler() {
        super.setFilter(new LogFilter());
        super.setFormatter(new LogFormatter());
    }

    /**
     * Publishes the specified log record by formatting it using the
     * configured formatter, then sends the formatted message to the specified
     * chat via HTTP POST request.
     *
     * <p>If an error occurs during the process, it is logged using {@link TelegramLog}.
     *
     * @param record description of the log event. A null record is
     *               silently ignored and is not published.
     */
    @Override
    public void publish(LogRecord record) {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            String formattedMessage = getFormatter().format(record);
            String bodyMessage = """
                    {
                    "chat_id":"%s",
                    "text":"%s" 
                    }
                    """.formatted(LoggingBotConfig.chatId, formattedMessage);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(bodyMessage))
                    .uri(URI.create(LoggingBotConfig.sendMessageUrl))
                    .header("Content-Type", "application/json")
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Determines if a given log record should be logged or not.
     *
     * @param record a {@code LogRecord} (may be null).
     * @return {@code true} if the log record should be logged, false otherwise.
     */
    @Override
    public boolean isLoggable(LogRecord record) {
        return super.isLoggable(record);
    }
}
