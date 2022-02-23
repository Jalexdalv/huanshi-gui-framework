package com.huanshi.gui.model.container.dialog;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.panel.DialogTitleBarModel;
import java.awt.Color;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class AbstractDialogModel extends AbstractModel {
    @ModelComponent(names = "title-bar")
    private DialogTitleBarModel dialogTitleBarModel;
    @Getter
    private Color background;
    @Getter
    private Size size;
    @Getter
    private Position position;
    @Getter
    private float opacity = 1.0F;
    @Getter
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