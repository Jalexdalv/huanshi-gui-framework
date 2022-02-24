package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.AutowiredModelComponent;
import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.type.FrameStatus;
import com.huanshi.gui.model.widget.IconModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

@SuppressWarnings("all")
public class FrameTitleBarModel extends AbstractPanelModel {
    @AutowiredModelComponent(names = {"maximize-icon-button", "icon"})
    private IconModel iconModel;
    @ModelComponent(names = "icon-title")
    private IconTitleModel iconTitleModel;
    @ModelComponent(names = "minimize-icon-button")
    private IconButtonModel minimizeIconButtonModel;
    @ModelComponent(names = "maximize-icon-button")
    private IconButtonModel maximizeIconButtonModel;
    @ModelComponent(names = "close-icon-button")
    private IconButtonModel closeIconButtonModel;
    @Getter
    private Icon standardMaximizeIcon;
    @Getter
    private Icon maxMaximizeIcon;
    @Getter
    private FrameStatus frameStatus = FrameStatus.STANDARD;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        standardMaximizeIcon = iconModel.getIcon();
        key.addLast("max-icon"); maxMaximizeIcon = parser.parseIcon(key); key.removeLast();
    }

    public void setFrameStatus(@NotNull FrameStatus frameStatus) {
        if (this.frameStatus != frameStatus) {
            this.frameStatus = frameStatus;
            switch (frameStatus) {
                case STANDARD -> iconModel.setIcon(standardMaximizeIcon);
                case MAX -> iconModel.setIcon(maxMaximizeIcon);
            }
            firePropertyChange("frame-status", frameStatus);
        }
    }
}