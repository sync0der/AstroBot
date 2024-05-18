package commands.callback_query_handlers;

import app.AstroBot;

/**
 * The {@code DateCallbackQueryHandler} class handles
 * {@link buttons.InlineButtonsWithDateOption#DATE} callback query.
 */
public class DateCallbackQueryHandler implements CallbackQueryHandlerInterface {
    /**
     * Handles {@link buttons.InlineButtonsWithDateOption#DATE} callback query prompting the user
     * to enter a date with the appropriate format when triggered.
     *
     * @param callbackData the data associated with the callback query.
     * @param chatId       the ID of the chat where callback query originated.
     * @param bot          the instance  of the {@link AstroBot}.
     */
    @Override
    public void handleCallbackQuery(String callbackData, Long chatId, AstroBot bot) {
        bot.setMessageSent(false);
        bot.deleteMessage(chatId, bot.getLastMessageId());
        bot.sendMessage(chatId, "Please enter the date in the format [yyyy-mm-dd].\nFor example, 2024-04-24.");
    }
}
