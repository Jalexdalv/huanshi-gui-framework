package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.model.AbstractModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Font;

@Getter
public class TextModel extends AbstractModel {
    private String text;
    private Font font;
    private Color foreground;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("text"); text = parser.parseString(false, true, key); key.removeLast();
        key.addLast("font"); font = parser.parseFont(key); key.removeLast();
        key.addLast("foreground"); foreground = parser.parseColor(key); key.removeLast();
    }

    public void setText(@NotNull String text) {
        if (!this.text.equals(text)) {
            this.text = text;
            firePropertyChange("text", text);
        }
    }
}