package buttons;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code InlineButtonData} class represents a set of button information
 * for building inline keyboards.
 */
@Getter
public class InlineButtonData {
    private final List<ButtonInfo> buttonInfoList;

    /**
     * Constructs an instance of {@code InlineButtonData} with the provided button information.
     *
     * @param buttonInfoList the list of button information.
     */
    public InlineButtonData(ButtonInfo... buttonInfoList) {
        this.buttonInfoList = Arrays.asList(buttonInfoList);
    }

}
