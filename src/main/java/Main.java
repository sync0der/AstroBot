import app.AstroBot;
import app.BotInitializer;
import config.AstroBotConfig;

public class Main {
    public static void main(String[] args) throws Exception {
        AstroBotConfig astroBotConfig = new AstroBotConfig();
        AstroBot telegramBot = new AstroBot(astroBotConfig);
        BotInitializer botInitializer = new BotInitializer(telegramBot);
        botInitializer.init();

    }
}