package commands.command_handlers;

import app.AstroBot;
import buttons.InlineButtonsWithDateOption;
import errors.ServerConnectionError;
import logging.TelegramLog;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

/**
 * The{@code ApodCommandHandler} class handles the {@code /apod}command to retrieve Astronomy Picture of the Day.
 */
public class ApodCommandHandler implements CommandHandlerInterface {
    /**
     * Sends inline keyboard buttons with the option of setting a date by {@link InlineButtonsWithDateOption#sendInlineKeyboardButtonWithDateOption(Long chatId)}</p>
     *
     * @param update the update containing the command.
     * @param bot    the instance of the {@link AstroBot}.
     */
    @Override
    public void handleCommand(Update update, AstroBot bot) {
        bot.setApodCalled(true);
        Long chatId = update.getMessage().getChatId();
        try {
            Message msg = bot.execute(InlineButtonsWithDateOption.sendInlineKeyboardButtonWithDateOption(chatId));
            bot.setLastMessageId(msg.getMessageId());
        } catch (TelegramApiException e) {
            bot.sendMessage(chatId, ServerConnectionError.FAILED_REQUEST);
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }

    }
}
