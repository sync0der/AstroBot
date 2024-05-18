package commands.callback_query_handlers;

import app.AstroBot;

/**
 * The {@code CallbackQueryHandlerInterface} interface defines the contract for
 * handling callback queries received from inline buttons.
 */
public interface CallbackQueryHandlerInterface {
    /**
     * Handles the received callback query.
     *
     * @param callbackData the data associated with the callback query.
     * @param chatId       the ID of the chat where callback query originated.
     * @param bot          the instance  of the {@link AstroBot}.
     */
    void handleCallbackQuery(String callbackData, Long chatId, AstroBot bot);
}
