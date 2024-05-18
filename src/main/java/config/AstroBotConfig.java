package config;

import app.AstroBot;
import logging.TelegramLog;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * The {@code AstroBotConfig} class represents the configuration for the {@link AstroBot},
 * including its name and token.
 */
@Data
public class AstroBotConfig {
    private String botName;
    private String botToken;

    public AstroBotConfig() {
        Properties properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream("src/main/resources/astrobot.properties");
            properties.load(inputStream);
            inputStream.close();

            botName = properties.getProperty("bot.name");
            botToken = properties.getProperty("bot.token");
        } catch (IOException e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
