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
 * The{@code EpicCommandHandler} class handles the {@code /epic} command to retrieve snapshots of Earth from EPIC camera.
 */
public class EpicCommandHandler implements CommandHandlerInterface {
    /**
     * <p>Retrieves all available dates only once to check for existence of input date in {@link nasa_services.epic.Epic#listOfAllAvailableDates}</p>
     * <p>Sends inline keyboard buttons with the option of setting a date {@link InlineButtonsWithDateOption#sendInlineKeyboardButtonWithDateOption(Long chatId)}</p>
     *
     * @param update the update containing the command.
     * @param bot    the instance of the {@link AstroBot}.
     */
    @Override
    public void handleCommand(Update update, AstroBot bot) {
        Long chatId = update.getMessage().getChatId();
        if (bot.isEpicDateListRetrieved()) {
            bot.getEpic().retrieveAllAvailableDates();
            bot.setEpicDateListRetrieved(false);
        }
        bot.setEpicCalled(true);
        try {
            Message msg = bot.execute(InlineButtonsWithDateOption.sendInlineKeyboardButtonWithDateOption(chatId));
            bot.setLastMessageId(msg.getMessageId());
        } catch (TelegramApiException e) {
            bot.sendMessage(chatId, ServerConnectionError.FAILED_REQUEST);
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }

    }
}
