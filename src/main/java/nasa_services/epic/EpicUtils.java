package nasa_services.epic;

import app.AstroBot;
import errors.DateFormatError;
import nasa_services.epic.epic_utils.EpicImageCollection;
import utils.DateUtils;

import java.util.List;

/**
 * The {@code EpicUtils} class provides utility methods for handling EPIC-related tasks.
 */
public class EpicUtils {
    /**
     * Generates a formatted message with the specified EPIC image and date information for posting.
     *
     * @param inputDate       the specified date.
     * @param inputDateFormat the format of the specified date.
     * @param imageName       the name of the EPIC image.
     * @param messageToPost   the message to include in the post.
     * @param bot             the instance of the {@link AstroBot}.
     * @return the constructed formatted message.
     */
    public static String generateFormattedMessageForPosting(String inputDate, String inputDateFormat, String imageName, String messageToPost, AstroBot bot) {
        String formattedDate = DateUtils.dateFormatter(inputDate, inputDateFormat, "yyyy/MM/dd", null);
        String response = messageToPost + bot.getEpic().getUrl(formattedDate, imageName);
        bot.setEpicCalled(false);
        return response;
    }

    /**
     * Sends all EPIC images for the specified date to a user.
     *
     * @param chatId    the ID of the chat to send the images to.
     * @param inputDate the specified date (format: yyyy-MM-dd).
     * @param bot       the instance of the {@link AstroBot}.
     */
    public static void sendAllEpicImagesForSpecifiedDate(Long chatId, String inputDate, AstroBot bot) {
        if (!DateUtils.isValidDateFormat("yyyy-MM-dd", inputDate)) {
            bot.sendMessage(chatId, DateFormatError.UNRECOGNIZED_DATE);
            return;
        }
        if (!bot.getEpic().isDateAfterEpicLaunchDate(inputDate) || DateUtils.isFutureDate(inputDate) || !bot.getEpic().checkForExistenceOfDate(inputDate)) {
            bot.sendMessage(chatId, DateFormatError.NO_IMAGES_AVAILABLE);
            return;
        }
        List<EpicImageCollection> listOfEpicImages = bot.getEpic().getEpicImagesForDate(inputDate);
        for (EpicImageCollection listOfEpicImage : listOfEpicImages)
            bot.sendImageRequest(chatId, generateFormattedMessageForPosting(inputDate, "yyyy-MM-dd",
                    listOfEpicImage.getImage(), bot.getEpic().createPost(listOfEpicImage), bot));
        bot.setMessageSent(false);
    }

    /**
     * Sends all EPIC images for the default date to a user.
     *
     * @param chatId the ID of the chat to send the images to.
     * @param bot    the instance of the {@link AstroBot}.
     */
    public static void sendAllEpicImagesFordDefaultDate(Long chatId, AstroBot bot) {
        List<EpicImageCollection> listOfEpicImages = bot.getEpic().getEpicImagesForDefaultDate();
        for (EpicImageCollection listOfEpicImage : listOfEpicImages)
            bot.sendImageRequest(chatId, generateFormattedMessageForPosting(bot.getEpic().getTheMostRecentDate(listOfEpicImage),
                    "yyyy-MM-dd HH:mm:ss", listOfEpicImage.getImage(), bot.getEpic().createPost(listOfEpicImage), bot));
        bot.setMessageSent(false);
    }


}
