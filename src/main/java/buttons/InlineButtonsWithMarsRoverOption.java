package buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code InlineButtonsWithMarsRoverOption} is the utility class to generate
 * inline keyboard buttons with Mars rover options.
 */
public class InlineButtonsWithMarsRoverOption {
    public static final String CURIOSITY = "Curiosity Rover";
    public static final String PERSEVERANCE = "Perseverance Rover";
    public static final String CANCEL = "Cancelled rover parameter";

    /**
     * Generates an inline keyboard with Mars rover options.
     *
     * @param chatId the ID of the chat where the keyboard should be sent.
     * @return the {@link SendMessage} object containing the inline keyboard.
     */
    public static SendMessage sendInlineKeyboardButtonWithMarsRoverOption(Long chatId) {
        List<InlineButtonData> buttonDataList = Arrays.asList(
                new InlineButtonData(
                        new ButtonInfo("Curiosity", CURIOSITY),
                        new ButtonInfo("Perseverance", PERSEVERANCE)
                ),
                new InlineButtonData(new ButtonInfo("Cancel", CANCEL))
        );
        return InlineButtonBuilder.buildInlineKeyboard(chatId, "Choose the Mars Rover", buttonDataList);
    }
}
