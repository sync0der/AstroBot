package config;

import app.AstroBot;
import logging.TelegramLog;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * The {@code LoggingBotConfig} class represents the configuration for logging
 * the {@link AstroBot}'s activity.
 */
@Data
public class LoggingBotConfig {
    public static String chatId;
    public static String botToken;
    public static String sendMessageUrl;

    static {
        try {
            Properties properties = new Properties();
            FileInputStream inputStream = new FileInputStream("src/main/resources/applog.properties");
            properties.load(inputStream);
            inputStream.close();

            chatId = properties.getProperty("chatId");
            botToken = properties.getProperty("bot.token");
            sendMessageUrl = "https://api.telegram.org/bot" + botToken + "/sendMessage";
        } catch (IOException e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
