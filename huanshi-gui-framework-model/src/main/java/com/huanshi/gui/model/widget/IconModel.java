package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.model.AbstractModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.awt.Color;

@Getter
public class IconModel extends AbstractModel {
    private Color background;
    private Icon icon;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        icon = parser.parseIcon(key);
    }

    public void setBackground(@NotNull Color background) {
        if (!this.background.equals(background)) {
            this.background = background;
            firePropertyChange("background", background);
        }
    }

    public void setIcon(@NotNull Icon icon) {
        if (!this.icon.equals(icon)) {
            this.icon = icon;
            firePropertyChange("icon", icon);
        }
    }
}