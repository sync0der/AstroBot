package commands.callback_query_handlers;

import app.AstroBot;
import buttons.InlineButtonsWithDateOption;
import buttons.InlineButtonsWithMarsRoverOption;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code CallbackQueryHandler} class routes callback queries to appropriate handlers based on the callback data.
 */
public class CallbackQueryHandler {
    /**
     * Map of callback data strings to their respective handlers.
     */
    private final Map<String, CallbackQueryHandlerInterface> callbackQueryHandler;

    /**
     * Constructs a new {@code CallbackQueryHandler} and initializes callback query handlers.
     */
    public CallbackQueryHandler() {
        callbackQueryHandler = new HashMap<>();
        callbackQueryHandler.put(InlineButtonsWithDateOption.DATE, new DateCallbackQueryHandler());
        callbackQueryHandler.put(InlineButtonsWithDateOption.DEFAULT, new DefaultDateCallbackQueryHandler());
        callbackQueryHandler.put(InlineButtonsWithMarsRoverOption.CURIOSITY, new CuriosityRoverCallbackQueryHandler());
        callbackQueryHandler.put(InlineButtonsWithMarsRoverOption.PERSEVERANCE, new PerseveranceRoverCallbackQueryHandler());
        callbackQueryHandler.put(InlineButtonsWithDateOption.CANCEL, new CancelCallbackQueryHandler());
        callbackQueryHandler.put(InlineButtonsWithMarsRoverOption.CANCEL, new CancelCallbackQueryHandler());
    }

    /**
     * Handles the received callback query by delegating to the appropriate handler.
     *
     * @param callbackData the data associated with the callback query.
     * @param chatId       the ID of the chat where callback query originated.
     * @param bot          the instance  of the {@link AstroBot}.
     */
    public void handleCallbackQuery(String callbackData, Long chatId, AstroBot bot) {
        CallbackQueryHandlerInterface handler = callbackQueryHandler.get(callbackData);
        if (handler != null)
            handler.handleCallbackQuery(callbackData, chatId, bot);
    }
}