package commands.command_handlers;

import app.AstroBot;
import buttons.InlineButtonsWithMarsRoverOption;
import commands.Command;
import errors.ServerConnectionError;
import logging.TelegramLog;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

/**
 * The {@code RoverCommandHandler} handles the {@code /rover} and {@code /roverinfo} commands to fetch Mars Rover photos and information
 */
public class RoverCommandHandler implements CommandHandlerInterface {
    /**
     * <p>If the update command equals {@link Command#ROVER} or {@link Command#ROVER_INFO} sends inline keyboard
     * buttons with the option of choosing a rover by
     * {@link InlineButtonsWithMarsRoverOption#sendInlineKeyboardButtonWithMarsRoverOption(Long chatId)} </p>
     *
     * @param update the update containing the command.
     * @param bot    the instance of the {@link AstroBot}.
     */
    @Override
    public void handleCommand(Update update, AstroBot bot) {
        Long chatId = update.getMessage().getChatId();
        if (update.getMessage().getText().equals(Command.ROVER))
            bot.setRoverCalled(true);
        else if (update.getMessage().getText().equals(Command.ROVER_INFO))
            bot.setRoverInfoCalled(true);
        try {
            Message msg = bot.execute(InlineButtonsWithMarsRoverOption.sendInlineKeyboardButtonWithMarsRoverOption(chatId));
            bot.setLastMessageId(msg.getMessageId());
        } catch (TelegramApiException e) {
            bot.sendMessage(chatId, ServerConnectionError.FAILED_REQUEST);
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
