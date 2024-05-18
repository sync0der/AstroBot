package nasa_services.apod;

import app.AstroBot;
import errors.DateFormatError;
import utils.DateUtils;

/**
 * The {@code ApodUtils} class provides utility methods for handling APOD requests.
 */
public class ApodUtils {
    /**
     * Sends the APOD image with data for the specified date to the given chat ID.
     *
     * @param chatId    the ID of the chat to send the message to.
     * @param inputDate the date for which the APOD image is requested.
     * @param bot       the instance of the {@link AstroBot}.
     */
    public static void sendApodImageForSpecificDate(Long chatId, String inputDate, AstroBot bot) {
        bot.setApodCalled(false);
        if (!DateUtils.isValidDateFormat("yyyy-MM-dd", inputDate)) {
            bot.sendMessage(chatId, DateFormatError.UNRECOGNIZED_DATE);
            return;
        } else if (DateUtils.isFutureDate(inputDate)) {
            bot.sendMessage(chatId, DateFormatError.NO_IMAGES_AVAILABLE);
            return;
        }
        bot.sendImageRequest(chatId, bot.getApod().getUrlForSpecifiedDate(inputDate));
    }

    /**
     * Sends the APOD image with data for the default date to the given chat ID.
     *
     * @param chatId the ID of the chat to send the message to.
     * @param bot    the instance of the {@link AstroBot}.
     */
    public static void sendApodImageForDefaultDate(Long chatId, AstroBot bot) {
        bot.sendImageRequest(chatId, bot.getApod().getUrlForDefaultDate());
    }
}
