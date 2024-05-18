package commands.callback_query_handlers;

import app.AstroBot;
import buttons.InlineButtonsWithDateOption;
import errors.ServerConnectionError;
import errors.UserInputError;
import logging.TelegramLog;
import nasa_services.mars_rover.MarsRover;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

/**
 * The {@code CuriosityRoverCallbackQueryHandler} class handles
 * {@link buttons.InlineButtonsWithMarsRoverOption#CURIOSITY} callback query.
 */
public class CuriosityRoverCallbackQueryHandler implements CallbackQueryHandlerInterface {
    /**
     * Handles {@link buttons.InlineButtonsWithMarsRoverOption#CURIOSITY} callback query received by
     * {@link commands.Command#ROVER} and {@link commands.Command#ROVER_INFO} commands and
     * sends {@link InlineButtonsWithDateOption#sendInlineKeyboardButtonWithDateOption(Long chatId)} and
     * {@link MarsRover#MARS_CURIOSITY} rover information respectively.
     *
     * @param callbackData the data associated with the callback query.
     * @param chatId       the ID of the chat where callback query originated.
     * @param bot          the instance  of the {@link AstroBot}.
     */
    @Override
    public void handleCallbackQuery(String callbackData, Long chatId, AstroBot bot) {
        if (bot.isRoverCalled()) {
            bot.setMessageSent(false);
            bot.deleteMessage(chatId, bot.getLastMessageId());
            bot.setCuriosityCalled(true);
            try {
                Message msg = bot.execute(InlineButtonsWithDateOption.sendInlineKeyboardButtonWithDateOption(chatId));
                bot.setLastMessageId(msg.getMessageId());
            } catch (TelegramApiException e) {
                bot.sendMessage(chatId, ServerConnectionError.FAILED_REQUEST);
                TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            }
        } else if (bot.isRoverInfoCalled()) {
            bot.deleteMessage(chatId, bot.getLastMessageId());
            bot.sendMessage(chatId, bot.getMarsRover().getRoverInfo(MarsRover.MARS_CURIOSITY));
            bot.setRoverInfoCalled(false);
        } else
            bot.sendMessage(chatId, UserInputError.UNRECOGNIZED_MESSAGE);
    }
}
