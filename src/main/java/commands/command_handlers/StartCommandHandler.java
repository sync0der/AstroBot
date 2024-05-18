package commands.command_handlers;

import app.AstroBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.Emojis;

/**
 * {@code StartCommandHandler} class handles the {@code /start} command to provide introductory message.
 */
public class StartCommandHandler implements CommandHandlerInterface {
    /**
     * Sends the introductory message to the user.
     *
     * @param update the update containing the command.
     * @param bot    the instance of the {@link AstroBot}.
     */
    @Override
    public void handleCommand(Update update, AstroBot bot) {
        Long chatId = update.getMessage().getChatId();
        String name = update.getMessage().getChat().getFirstName();
        String answer = "Hi" + Emojis.HELLO + ", " + name + "!\nI'm a bot that provides access to NASA services. \nType /help to get detailed information about my functionality.";
        bot.sendMessage(chatId, answer);
    }
}
