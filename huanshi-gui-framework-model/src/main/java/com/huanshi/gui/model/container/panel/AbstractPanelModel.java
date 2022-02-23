package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.model.AbstractModel;
import java.awt.Color;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class AbstractPanelModel extends AbstractModel {
    private Color background;
    private Size size;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        key.addLast("size"); size = parser.parseSize(key); key.removeLast();
    }

    public void setBackground(@NotNull Color background) {
        if (!this.background.equals(background)) {
            this.background = background;
            firePropertyChange("background", background);
        }
    }
}