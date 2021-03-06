package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.type.ButtonStatus;
import com.huanshi.gui.common.type.ButtonType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.awt.Color;

@SuppressWarnings("all")
public class IconButtonModel extends IconModel {
    @Getter
    private ButtonType buttonType;
    @Getter
    private Color defaultBackground;
    @Getter
    private Color touchBackground;
    @Getter
    private Color pressBackground;
    @Getter
    private Icon defaultIcon;
    @Getter
    private Icon touchIcon;
    @Getter
    private Icon pressIcon;
    @Getter
    private boolean isEnable;
    @Getter
    private ButtonStatus buttonStatus = ButtonStatus.DEFAULT;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("button-type"); buttonType = parser.parseButtonType(key); key.removeLast();
        switch (buttonType) {
            case BACKGROUND -> {
                defaultBackground = getBackground();
                key.addLast("touch-background"); touchBackground = parser.parseColor(key); key.removeLast();
                key.addLast("press-background"); pressBackground = parser.parseColor(key); key.removeLast();
            }
            case ICON -> {
                defaultIcon = getIcon();
                key.addLast("touch-icon"); touchIcon = parser.parseIcon(false, key); key.removeLast();
                key.addLast("press-icon"); pressIcon = parser.parseIcon(false, key); key.removeLast();
            }
        }
        key.addLast("enable"); isEnable = parser.parseBoolean(false, key); key.removeLast();
    }

    public void setEnable(boolean isEnable) {
        if (this.isEnable != isEnable) {
            this.isEnable = isEnable;
            firePropertyChange("enable", isEnable);
        }
    }

    public void setButtonStatus(@NotNull ButtonStatus buttonStatus) {
        if (this.buttonStatus != buttonStatus) {
            this.buttonStatus = buttonStatus;
            switch (buttonType) {
                case BACKGROUND -> {
                    switch (buttonStatus) {
                        case DEFAULT -> setBackground(defaultBackground);
                        case TOUCH -> setBackground(touchBackground);
                        case PRESS -> setBackground(pressBackground);
                    }
                }
                case ICON -> {
                    switch (buttonStatus) {
                        case DEFAULT -> setIcon(defaultIcon);
                        case TOUCH -> setIcon(touchIcon);
                        case PRESS -> setIcon(pressIcon);
                    }
                }
            }
        }
    }
}