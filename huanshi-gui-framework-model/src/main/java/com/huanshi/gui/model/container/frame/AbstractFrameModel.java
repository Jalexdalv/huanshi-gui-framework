package com.huanshi.gui.model.container.frame;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.type.FrameStatus;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.panel.FrameTitleBarModel;
import java.awt.Color;
import java.awt.Image;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class AbstractFrameModel extends AbstractModel {
    @ModelComponent(names = "title-bar")
    private FrameTitleBarModel frameTitleBarModel;
    @Getter
    private Color background;
    @Getter
    private Image logo;
    @Getter
    private String title;
    @Getter
    private Size standardSize;
    @Getter
    private Position standardPosition;
    @Getter
    private final Size maxSize = new Size(getScreenWidth(), getScreenHeight());
    @Getter
    private final Position maxPosition = new Position(0, 0);
    @Getter
    private FrameStatus frameStatus = FrameStatus.STANDARD;
    @Getter
    private float opacity = 1.0F;
    @Getter
    private boolean isVisible = false;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        key.addLast("logo"); logo = parser.parseImage(key); key.removeLast();
        key.addLast("title"); title = parser.parseString(false, false, key); key.removeLast();
        key.addLast("size"); standardSize = parser.parseSize(key); key.removeLast();
        standardPosition = new Position(GuiUtils.divide(maxSize.getWidth() - standardSize.getWidth(), 2), GuiUtils.divide(maxSize.getHeight() - standardSize.getHeight(), 2));
    }

    public void setFrameStatus(@NotNull FrameStatus frameStatus) {
        if (this.frameStatus != frameStatus) {
            this.frameStatus = frameStatus;
            frameTitleBarModel.setFrameStatus(frameStatus);
            firePropertyChange("frame-status", frameStatus);
        }
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

    public void iconified() {
        firePropertyChange("iconified", null);
    }
}