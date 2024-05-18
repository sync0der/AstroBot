package commands.command_handlers;

import app.AstroBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * {@code CommandHandlerInterface} interface handles a command received by the bot.
 */
public interface CommandHandlerInterface {
    /**
     * Handles the received command.
     *
     * @param update the update containing the command.
     * @param bot    the instance of the {@link AstroBot}.
     */
    void handleCommand(Update update, AstroBot bot);
}
