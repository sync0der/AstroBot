package buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code InlineButtonsWithDateOption} is the utility class to generate
 * inline keyboard buttons with date options.
 */
public class InlineButtonsWithDateOption {
    public static final String DEFAULT = "Default date parameter";
    public static final String DATE = "Specific date parameter";
    public static final String CANCEL = "Cancelled date parameter";

    /**
     * Generates an inline keyboard with date options.
     *
     * @param chatId the ID of the chat where the keyboard should be sent.
     * @return the {@link SendMessage} object containing the inline keyboard.
     */
    public static SendMessage sendInlineKeyboardButtonWithDateOption(Long chatId) {
        List<InlineButtonData> buttonDataList = Arrays.asList(
                new InlineButtonData(
                        new ButtonInfo("Set Custom Date", DATE),
                        new ButtonInfo("Set Default Date", DEFAULT)
                ),
                new InlineButtonData(new ButtonInfo("Cancel", CANCEL))
        );
        return InlineButtonBuilder.buildInlineKeyboard(chatId, "Choose Date Parameters:", buttonDataList);
    }

}
