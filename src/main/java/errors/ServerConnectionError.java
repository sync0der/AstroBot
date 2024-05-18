package errors;

import utils.Emojis;

/**
 * The {@code ServerConnectionError} class provides an error message for server connection failures.
 */
public class ServerConnectionError {
    public static final String FAILED_REQUEST = Emojis.WARNING + "Server Connection Error!\nUnavailable to process your request! Please try again later.";
}
