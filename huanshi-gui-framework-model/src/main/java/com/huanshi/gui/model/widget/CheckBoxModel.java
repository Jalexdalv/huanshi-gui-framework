package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.model.AbstractModel;
import java.awt.Color;
import java.awt.Font;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings(value = "all")
@Getter
public class CheckBoxModel extends AbstractModel {
    private Color background;
    private String text;
    private Font font;
    private Color foreground;
    private boolean isEnable;
    private boolean isSelected = false;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        key.addLast("text"); text = parser.parseString(false, false, key); key.removeLast();
        key.addLast("font"); font = parser.parseFont(key); key.removeLast();
        key.addLast("foreground"); foreground = parser.parseColor(key); key.removeLast();
        key.addLast("enable"); isEnable = parser.parseBoolean(false, key); key.removeLast();
    }

    public void setEnable(boolean isEnable) {
        if (this.isEnable != isEnable) {
            this.isEnable = isEnable;
            firePropertyChange("enable", isEnable);
        }
    }

    public void setSelected(boolean isSelected, boolean isFire) {
        if (this.isSelected != isSelected) {
            this.isSelected = isSelected;
            if (isFire) {
                firePropertyChange("selected", isSelected);
            }
        }
    }

    public void reset() {
        setSelected(false, true);
    }
}