package nasa_services.mars_rover;

import app.AstroBot;
import errors.DateFormatError;
import utils.DateUtils;

/**
 * The {@code MarsRoverUtils} class provides utility methods for sending
 * Mars rover images via the {@link AstroBot}.
 */
public class MarsRoverUtils {
    /**
     * Sends Mars rover images for a specified date.
     *
     * @param chatId    the ID of the chat where the images will be sent.
     * @param inputDate the specific date in the format "yyyy-MM-dd" for fetching images.
     * @param bot       the instance of the {@link AstroBot}.
     */
    public static void sendMarsRoverImagesForSpecifiedDate(Long chatId, String inputDate, AstroBot bot) {
        if (!DateUtils.isValidDateFormat("yyyy-MM-dd", inputDate)) {
            bot.sendMessage(chatId, DateFormatError.UNRECOGNIZED_DATE);
            return;
        } else if (DateUtils.isFutureDate(inputDate)) {
            bot.sendMessage(chatId, DateFormatError.NO_IMAGES_AVAILABLE);
            return;
        }
        if (bot.isCuriosityCalled())
            bot.sendImageRequest(chatId, bot.getMarsRover().getMarsRoverImage(MarsRover.MARS_CURIOSITY, inputDate));
        else if (bot.isPerseveranceCalled())
            bot.sendImageRequest(chatId, bot.getMarsRover().getMarsRoverImage(MarsRover.MARS_PERSEVERANCE, inputDate));
        bot.setCuriosityCalled(false);
        bot.setPerseveranceCalled(false);
    }

    /**
     * Sends Mars rover images for the default date (the latest available images).
     *
     * @param chatId the ID of the chat where the images will be sent.
     * @param bot    the instance of the {@link AstroBot}.
     */
    public static void sendMarsRoverImagesForDefaultDate(Long chatId, AstroBot bot) {
        if (bot.isCuriosityCalled())
            bot.sendImageRequest(chatId, bot.getMarsRover().getMarsRoverImage(MarsRover.MARS_CURIOSITY, null));
        else if (bot.isPerseveranceCalled())
            bot.sendImageRequest(chatId, bot.getMarsRover().getMarsRoverImage(MarsRover.MARS_PERSEVERANCE, null));
        bot.setCuriosityCalled(false);
        bot.setPerseveranceCalled(false);
    }

}
