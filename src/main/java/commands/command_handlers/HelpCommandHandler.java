package commands.command_handlers;

import app.AstroBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.Emojis;

/**
 * The {@code HelpCommandHandler} handles the {@code /help} command by providing detailed information about the bot's functionality.
 */
public class HelpCommandHandler implements CommandHandlerInterface {
    /**
     * Sends a detailed description of the bot's functionality to the user
     *
     * @param update the update containing the command.
     * @param bot    the instance of the {@link AstroBot}.
     */
    @Override
    public void handleCommand(Update update, AstroBot bot) {
        Long chatId = update.getMessage().getChatId();
        String answer = "This Astro Explorer Bot uses NASA APIs to provide users with access to various NASA services.\n\n" +
                "It enables users to fetch:\n" +
                Emojis.ROUND_PUSHPIN + " Astronomy picture of the day\n" +
                Emojis.ROUND_PUSHPIN + " Earth snapshots from the EPIC camera\n" +
                Emojis.ROUND_PUSHPIN + " Mars Rover photos\n" +
                Emojis.ROUND_PUSHPIN + " Mars Rovers information \n" +
                Emojis.ROUND_PUSHPIN + " Images from the NASA Image Library. \n\n" +
                "Below are the commands the bot can process:\n" +
                "/apod" +
                "\nGets the Astronomy Picture of the Day for the specified date (in format: yyyy-mm-dd) or for default (the latest) date.\n\n" +
                "/epic" +
                "\nGets snapshots of Earth from the Earth Polychromatic Imaging Camera for the specified date (in format: yyyy-mm-dd) or for default (the latest) date." +
                "\nThe service is available starting from 2015-06-13.\n\n" +
                "/rover" +
                "\nFetches photos from the Mars Rovers for the specified date (in format: yyyy-mm-dd) or for default (the latest) day." +
                "\nCuriosity Rover is available starting from 2012-06-08. " +
                "\nPerseverance Rover - from 2021-02-18.\n\n" +
                "/roverinfo" +
                "\nObtains information about the Curiosity or Perseverance rover.\n\n" +
                "/image [topic]" +
                "\nReceives a random image from the NASA Image Library based on the provided topic." +
                "\nEx: /image Galaxy";
        bot.sendMessage(chatId, answer);
    }
}
