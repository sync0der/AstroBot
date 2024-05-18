package app;

import commands.command_handlers.CommandHandler;
import config.AstroBotConfig;
import errors.ServerConnectionError;
import logging.TelegramLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nasa_services.apod.Apod;
import nasa_services.epic.Epic;
import nasa_services.mars_rover.MarsRover;
import nasa_services.nasa_image.NasaImage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.Emojis;

import java.util.Arrays;

/**
 * The {@code AstroBot} class represents the Telegram bot implementation for handling
 * various commands and sending messages, images, and updates to users. It extends the
 * {@link TelegramLongPollingBot} class provided by the Telegram Bots API.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class AstroBot extends TelegramLongPollingBot {
    private final AstroBotConfig astroBotConfig;
    private final CommandHandler handler;
    private final Apod apod;
    private final Epic epic;
    private final NasaImage nasaImage;
    private final MarsRover marsRover;
    private boolean isApodCalled = false;
    private boolean isEpicCalled = false;
    private boolean isMessageSent = false;
    private boolean isEpicDateListRetrieved = true;
    private boolean isRoverCalled = false;
    private boolean isCuriosityCalled = false;
    private boolean isPerseveranceCalled = false;
    private boolean isRoverInfoCalled = false;
    private Integer lastMessageId;

    /**
     * Construct a new AstroBot instance with the provided configuration.
     *
     * @param astroBotConfig the configuration for the bot.
     */
    public AstroBot(AstroBotConfig astroBotConfig) {
        this.astroBotConfig = astroBotConfig;
        this.handler = new CommandHandler();
        this.apod = new Apod();
        this.epic = new Epic();
        this.nasaImage = new NasaImage();
        this.marsRover = new MarsRover();
    }

    /**
     * Handles incoming updates received by the bot.
     *
     * @param update the incoming update.
     */
    @Override
    public void onUpdateReceived(Update update) {
        handler.handleUpdate(update, this);
    }

    /**
     * Retrieves the bot's username.
     *
     * @return the bot's username.
     */
    @Override
    public String getBotUsername() {
        return astroBotConfig.getBotName();
    }

    /**
     * Retrieves the bot's token.
     *
     * @return the bot's token.
     */
    @Override
    public String getBotToken() {
        return astroBotConfig.getBotToken();
    }

    /**
     * Sends a message to a specified chat.
     *
     * @param chatId     the ID of the chat where the message should be sent.
     * @param textToSend the text to be sent.
     */
    public void sendMessage(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        try {
            Message msg = execute(message);
            if (textToSend.equals(Emojis.SAND_WATCHES + "Fetching..."))
                lastMessageId = msg.getMessageId();
        } catch (TelegramApiException e) {
            sendMessage(chatId, ServerConnectionError.FAILED_REQUEST);
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Sends a request to fetch and send an image to the specified chat.
     *
     * @param chatId   the ID of the chat where the image should be sent.
     * @param imageUrl the URL of the image to be sent.
     */
    public void sendImageRequest(Long chatId, String imageUrl) {
        if (!isMessageSent) {
            sendMessage(chatId, Emojis.SAND_WATCHES + "Fetching...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                sendMessage(chatId, ServerConnectionError.FAILED_REQUEST);
                TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            }
            deleteMessage(chatId, lastMessageId);
        }
        isMessageSent = true;
        try {
            sendMessage(chatId, imageUrl);
        } catch (Exception e) {
            sendMessage(chatId, ServerConnectionError.FAILED_REQUEST);
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Deletes a message in the specified chat.
     *
     * @param chatId    the ID of the chat where the message should be deleted.
     * @param messageId the ID of the message to be deleted.
     */
    public void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

}



