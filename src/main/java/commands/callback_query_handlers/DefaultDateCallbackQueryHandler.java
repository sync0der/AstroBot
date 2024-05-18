package commands.callback_query_handlers;

import app.AstroBot;
import nasa_services.apod.ApodUtils;
import nasa_services.epic.EpicUtils;
import nasa_services.mars_rover.MarsRoverUtils;

/**
 * The {@code DefaultDateCallbackQueryHandler} class handles
 * {@link buttons.InlineButtonsWithDateOption#DEFAULT} callback query.
 */
public class DefaultDateCallbackQueryHandler implements CallbackQueryHandlerInterface {
    /**
     * Handles {@link buttons.InlineButtonsWithDateOption#DEFAULT} callback query by
     * sending APOD, EPIC, ROVER images with default dates respectively when {@link commands.Command#APOD},
     * {@link commands.Command#EPIC} and {@link commands.Command#ROVER} commands called.
     *
     * @param callbackData the data associated with the callback query.
     * @param chatId       the ID of the chat where callback query originated.
     * @param bot          the instance  of the {@link AstroBot}.
     */
    @Override
    public void handleCallbackQuery(String callbackData, Long chatId, AstroBot bot) {
        bot.setMessageSent(false);
        bot.deleteMessage(chatId, bot.getLastMessageId());
        if (bot.isApodCalled()) {
            ApodUtils.sendApodImageForDefaultDate(chatId, bot);
            bot.setApodCalled(false);
        } else if (bot.isEpicCalled()) {
            EpicUtils.sendAllEpicImagesFordDefaultDate(chatId, bot);
            bot.setEpicCalled(false);
        } else {
            MarsRoverUtils.sendMarsRoverImagesForDefaultDate(chatId, bot);
            bot.setRoverCalled(false);
        }
    }
}
