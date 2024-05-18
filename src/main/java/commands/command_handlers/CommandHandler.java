package commands.command_handlers;

import app.AstroBot;
import commands.Command;
import commands.callback_query_handlers.CallbackQueryHandler;
import commands.fallback_handlers.DefaultFallbackHandler;
import commands.fallback_handlers.FallbackHandlerInterface;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code CommandHandler} class handles incoming updates and routes them to appropriate command handlers.
 */
public class CommandHandler {
    /**
     * Map of command strings to their respective handlers.
     */
    private final Map<String, CommandHandlerInterface> commandHandlers;

    /**
     * Handler for handling default commands.
     *
     * @see commands.fallback_handlers.FallbackHandlerInterface
     */
    private final FallbackHandlerInterface fallbackHandler;

    /**
     * Handler for processing callback queries from buttons.
     *
     * @see commands.callback_query_handlers.CallbackQueryHandler
     */
    private final CallbackQueryHandler callbackQueryHandler;

    /**
     * Constructs a new {@code CommandHandler} and initializes handlers.
     */
    public CommandHandler() {
        commandHandlers = new HashMap<>();
        commandHandlers.put(Command.START, new StartCommandHandler());
        commandHandlers.put(Command.APOD, new ApodCommandHandler());
        commandHandlers.put(Command.EPIC, new EpicCommandHandler());
        commandHandlers.put(Command.ROVER, new RoverCommandHandler());
        commandHandlers.put(Command.ROVER_INFO, new RoverCommandHandler());
        commandHandlers.put(Command.HELP, new HelpCommandHandler());

        fallbackHandler = new DefaultFallbackHandler();
        callbackQueryHandler = new CallbackQueryHandler();
    }

    /**
     * Handles the incoming update and delegates to appropriate handlers.
     *
     * @param update the incoming update containing the command.
     * @param bot    the instance of the {@link AstroBot}.
     */
    public void handleUpdate(Update update, AstroBot bot) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            CommandHandlerInterface handler = commandHandlers.get(command);
            if (handler != null)
                handler.handleCommand(update, bot);
            else
                fallbackHandler.handleFallback(command, update.getMessage().getChatId(), bot);
        } else if (update.hasCallbackQuery()) {
            String callback = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            callbackQueryHandler.handleCallbackQuery(callback, chatId, bot);
        }
    }
}
