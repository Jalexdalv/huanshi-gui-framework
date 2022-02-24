package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.widget.IconButton;

@SuppressWarnings("all")
public class FrameTitleBar extends AbstractPanel {
    @ViewComponent(names = "icon-title")
    private IconTitle iconTitle;
    @ViewComponent(names = "minimize-icon-button")
    private IconButton minimizeIconButton;
    @ViewComponent(names = "maximize-icon-button")
    private IconButton maximizeIconButton;
    @ViewComponent(names = "close-icon-button")
    private IconButton closeIconButton;

    @Override
    public void updateContainerPosition() {
        iconTitle.setWidgetPosition(0, getLayoutCenterY(iconTitle));
        minimizeIconButton.setWidgetPosition(getLayoutWidth() - (minimizeIconButton.getLayoutWidth() + maximizeIconButton.getLayoutWidth() + closeIconButton.getLayoutWidth()), 0);
        maximizeIconButton.setWidgetPosition(getLayoutWidth() - (maximizeIconButton.getLayoutWidth() + closeIconButton.getLayoutWidth()), 0);
        closeIconButton.setWidgetPosition(getLayoutWidth() - closeIconButton.getLayoutWidth(), 0);
    }
}