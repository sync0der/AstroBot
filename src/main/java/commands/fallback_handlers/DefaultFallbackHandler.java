package commands.fallback_handlers;

import app.AstroBot;
import commands.Command;
import errors.UserInputError;
import nasa_services.apod.ApodUtils;
import nasa_services.epic.EpicUtils;
import nasa_services.mars_rover.MarsRoverUtils;

/**
 * The {@code DefaultFallbackHandler} class implements the {@link FallbackHandlerInterface} interface
 * and provides a default fallback mechanism for handling unrecognized commands or inputs.
 */
public class DefaultFallbackHandler implements FallbackHandlerInterface {
    /**
     * @param message the received message.
     * @param chatId  the ID of the chat where the fallback occurs.
     * @param bot     the instance of the {@link AstroBot}.
     */
    @Override
    public void handleFallback(String message, Long chatId, AstroBot bot) {
        if (bot.isApodCalled())
            handleApodFallback(message, chatId, bot);
        else if (bot.isEpicCalled())
            handleEpicFallback(message, chatId, bot);
        else if (bot.isRoverCalled())
            handleRoverFallback(message, chatId, bot);
        else
            handleImageFallback(message, chatId, bot);
    }

    /**
     * Sends the Astronomic Picture of the Day for the received date.
     *
     * @param message the received date.
     * @param chatId  the ID of the chat where the fallback occurs.
     * @param bot     the instance of the {@link AstroBot}.
     */
    private void handleApodFallback(String message, Long chatId, AstroBot bot) {
        ApodUtils.sendApodImageForSpecificDate(chatId, message, bot);
        bot.setMessageSent(false);
        bot.setApodCalled(false);
    }

    /**
     * Sends the snapshots of Earth from the EPIC camera for the received date.
     *
     * @param message the received date.
     * @param chatId  the ID of the chat where the fallback occurs.
     * @param bot     the instance of the {@link AstroBot}.
     */
    private void handleEpicFallback(String message, Long chatId, AstroBot bot) {
        EpicUtils.sendAllEpicImagesForSpecifiedDate(chatId, message, bot);
        bot.setEpicCalled(false);
    }

    /**
     * Sends the image from the Mars Rovers for the received date.
     *
     * @param message the received date
     * @param chatId  the ID of the chat where the fallback occurs.
     * @param bot     the instance of the {@link AstroBot}.
     */
    private void handleRoverFallback(String message, Long chatId, AstroBot bot) {
        MarsRoverUtils.sendMarsRoverImagesForSpecifiedDate(chatId, message, bot);
        bot.setRoverCalled(false);
        bot.setCuriosityCalled(false);
        bot.setPerseveranceCalled(false);
    }

    /**
     * Sends an image based corresponding the search term from NASA Image Library
     * if the received message is {@link Command#IMAGE} followed by a search term.
     * <p>Otherwise, sends {@link UserInputError#UNRECOGNIZED_MESSAGE}.
     *
     * @param message the received message.
     * @param chatId  the ID of the chat where the fallback occurs.
     * @param bot     the instance of the {@link AstroBot}.
     */
    private void handleImageFallback(String message, Long chatId, AstroBot bot) {
        String[] parts = message.split(" ");
        if (parts[0].equals(Command.IMAGE) && parts.length > 1) {
            bot.setMessageSent(false);
            bot.sendImageRequest(chatId, bot.getNasaImage().getNasaImage(message));
        } else
            bot.sendMessage(chatId, UserInputError.UNRECOGNIZED_MESSAGE);
    }
}
