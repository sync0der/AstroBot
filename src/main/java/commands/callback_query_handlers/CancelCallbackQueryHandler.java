package commands.callback_query_handlers;

import app.AstroBot;

/**
 * {@code CancelCallbackQueryHandler} class handles the {@link buttons.InlineButtonsWithDateOption#CANCEL}
 * and {@link buttons.InlineButtonsWithMarsRoverOption#CANCEL} callback queries.
 */
public class CancelCallbackQueryHandler implements CallbackQueryHandlerInterface {
    /**
     * Cancels the ongoing action by deleting the sent inline keyboard buttons.
     *
     * @param callbackData the data associated with the callback query.
     * @param chatId       the ID of the chat where callback query originated.
     * @param bot          the instance  of the {@link AstroBot}.
     */
    @Override
    public void handleCallbackQuery(String callbackData, Long chatId, AstroBot bot) {
        bot.deleteMessage(chatId, bot.getLastMessageId());
    }
}
