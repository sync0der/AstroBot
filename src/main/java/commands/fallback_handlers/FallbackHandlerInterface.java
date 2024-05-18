package commands.fallback_handlers;

import app.AstroBot;

/**
 * The {@code FallbackHandlerInterface} interface defines a contract for
 * handling fallback messages where the bot encounters unrecognized commands or messages.
 */
public interface FallbackHandlerInterface {
    /**
     * Handles fallback for unrecognized commands or inputs.
     *
     * @param message the received message.
     * @param chatId  the ID of the chat where the fallback occurs.
     * @param bot     the instance of the {@link AstroBot}.
     */
    void handleFallback(String message, Long chatId, AstroBot bot);
}
