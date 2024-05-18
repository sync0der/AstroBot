package errors;

import utils.Emojis;

/**
 * The {@code DateFormatError} class provides error messages related to date format recognition.
 */
public class DateFormatError {
    public static final String UNRECOGNIZED_DATE = Emojis.WARNING + "Date-format Error!\nUnrecognized date format!";
    public static final String NO_IMAGES_AVAILABLE = Emojis.WARNING + "Date-format Error!\nNo pictures available for this date on the server!";
}
