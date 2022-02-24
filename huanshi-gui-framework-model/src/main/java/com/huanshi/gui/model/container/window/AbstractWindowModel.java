package com.huanshi.gui.model.container.window;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

@SuppressWarnings("all")
@Getter
public abstract class AbstractWindowModel extends AbstractModel {
    private Color background;
    private Size size;
    private Position position;
    private float opacity = 1.0F;
    private boolean isVisible = false;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        key.addLast("size"); size = parser.parseSize(key); key.removeLast();
        position = new Position(GuiUtils.divide(getScreenWidth() - size.getWidth(), 2), GuiUtils.divide(getScreenHeight() - size.getHeight(), 2));
    }

    public void setOpacity(float opacity) {
        if (this.opacity != opacity) {
            this.opacity = opacity;
            firePropertyChange("opacity", opacity);
        }
    }

    public void setVisible(boolean isVisible) {
        if (this.isVisible != isVisible) {
            this.isVisible = isVisible;
            firePropertyChange("visible", isVisible);
        }
    }
}