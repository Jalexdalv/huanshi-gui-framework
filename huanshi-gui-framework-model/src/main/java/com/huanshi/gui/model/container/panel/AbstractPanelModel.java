package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.model.AbstractModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.awt.Color;

@Getter
public abstract class AbstractPanelModel extends AbstractModel {
    private Color background;
    private Icon backgroundIcon;
    private Size size;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        key.addLast("background-icon"); backgroundIcon = parser.parseBackgroundIcon(key); key.removeLast();
        key.addLast("size"); size = parser.parseSize(key); key.removeLast();
    }

    public void setBackground(@NotNull Color background) {
        if (!this.background.equals(background)) {
            this.background = background;
            firePropertyChange("background", background);
        }
    }

    public void setBackgroundIcon(@NotNull Icon backgroundIcon) {
        if (this.backgroundIcon == null || !this.backgroundIcon.equals(backgroundIcon)) {
            this.backgroundIcon = backgroundIcon;
            firePropertyChange("background-icon", backgroundIcon);
        }
    }
}