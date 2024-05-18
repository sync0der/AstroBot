package app;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * The {@code BotInitializer} class initializes {@link AstroBot} by registering it with the Telegram Bots API.
 */
public class BotInitializer {
    private final AstroBot telegramBot;

    /**
     * Constructs a new BotInitializer instance with the provided Telegram bot.
     *
     * @param telegramBot the {@link AstroBot} instance.
     */
    public BotInitializer(AstroBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Initializes and registers {@link AstroBot}.
     *
     * @throws TelegramApiException if an error occurs while registering the bot.
     */
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot);
    }
}
