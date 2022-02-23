package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.type.ButtonStatus;
import com.huanshi.gui.model.AbstractModel;
import java.awt.Color;
import java.awt.Font;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings(value = "all")
@Getter
public class ButtonModel extends AbstractModel {
    private Color background;
    private Color defaultBackground;
    private Color touchBackground;
    private Color pressBackground;
    private String text;
    private Font font;
    private Color foreground;
    private boolean isEnable;
    private ButtonStatus buttonStatus = ButtonStatus.DEFAULT;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        defaultBackground = background;
        key.addLast("touch-background"); touchBackground = parser.parseColor(key); key.removeLast();
        key.addLast("press-background"); pressBackground = parser.parseColor(key); key.removeLast();
        key.addLast("text"); text = parser.parseString(false, false, key); key.removeLast();
        key.addLast("font"); font = parser.parseFont(key); key.removeLast();
        key.addLast("foreground"); foreground = parser.parseColor(key); key.removeLast();
        key.addLast("enable"); isEnable = parser.parseBoolean(false, key); key.removeLast();
    }

    public void setBackground(@NotNull Color background) {
        if (!this.background.equals(background)) {
            this.background = background;
            firePropertyChange("background", background);
        }
    }

    public void setButtonStatus(@NotNull ButtonStatus buttonStatus) {
        if (this.buttonStatus != buttonStatus) {
            this.buttonStatus = buttonStatus;
            switch (buttonStatus) {
                case DEFAULT -> setBackground(defaultBackground);
                case TOUCH -> setBackground(touchBackground);
                case PRESS -> setBackground(pressBackground);
            }
        }
    }

    public void setEnable(boolean isEnable) {
        if (this.isEnable != isEnable) {
            this.isEnable = isEnable;
            firePropertyChange("enable", isEnable);
        }
    }
}