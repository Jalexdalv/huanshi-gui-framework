package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.model.AbstractModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Font;

@SuppressWarnings(value = "all")
@Getter
public class TextFieldModel extends AbstractModel {
    private Color background;
    private int alignment;
    private int columns;
    private Font font;
    private Color foreground;
    private int limit;
    private boolean isEnable;
    private String text = "";

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        key.addLast("alignment"); alignment = parser.parseTextFieldAlignment(key); key.removeLast();
        key.addLast("columns"); columns = parser.parseInteger(false, key); key.removeLast();
        key.addLast("font"); font = parser.parseFont(key); key.removeLast();
        key.addLast("foreground"); foreground = parser.parseColor(key); key.removeLast();
        key.addLast("limit"); limit = parser.parseInteger(false, key); key.removeLast();
        key.addLast("enable"); isEnable = parser.parseBoolean(false, key); key.removeLast();
    }

    public void setLimit(int limit) {
        if (this.limit != limit) {
            this.limit = limit;
            firePropertyChange("limit", isEnable);
        }
    }

    public void setEnable(boolean isEnable) {
        if (this.isEnable != isEnable) {
            this.isEnable = isEnable;
            firePropertyChange("enable", isEnable);
        }
    }

    public void setText(@NotNull String text, boolean isFire) {
        if (!this.text.equals(text) && text.length() <= limit) {
            this.text = text;
            if (isFire) {
                firePropertyChange("text", text);
            }
        }
    }

    public void reset() {
        setText("", true);
    }
}