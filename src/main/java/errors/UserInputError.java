package errors;

import utils.Emojis;

/**
 * The {@code UserInputError} class provides error messages related to user input.
 */
public class UserInputError {
    public static final String UNRECOGNIZED_MESSAGE = Emojis.WARNING + "User Input Error! Unrecognized Message!\nClick on the menu button or enter /help to see the commands";
    public static final String INVALID_SEARCH_TERM = Emojis.WARNING + "User Input Error!\nNo Images Found With That Search Term!";
}
