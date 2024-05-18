package buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code InlineButtonBuilder} class provides utility methods for building
 * inline keyboards with inline buttons.
 */
public class InlineButtonBuilder {
    /**
     * Builds an inline keyboard with the provided message and button data.
     *
     * @param chatId         the ID of the chat where keyboard should be sent.
     * @param message        the message to be displayed above the inline keyboard.
     * @param buttonDataList the list of {@link InlineButtonData} containing {@link ButtonInfo}.
     * @return the {@link SendMessage} object containing the inline keyboard.
     */
    public static SendMessage buildInlineKeyboard(Long chatId, String message, List<InlineButtonData> buttonDataList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (InlineButtonData buttonData : buttonDataList) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (ButtonInfo buttonInfo : buttonData.getButtonInfoList()) {
                InlineKeyboardButton button = InlineKeyboardButton.builder().text(buttonInfo.text()).callbackData(buttonInfo.callbackData()).build();
                row.add(button);
            }
            rows.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        return SendMessage.builder().chatId(chatId).text(message).replyMarkup(inlineKeyboardMarkup).build();
    }
}
