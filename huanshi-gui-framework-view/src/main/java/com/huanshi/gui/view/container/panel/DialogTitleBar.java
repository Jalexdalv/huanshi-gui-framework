package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;

@SuppressWarnings("all")
public class DialogTitleBar extends AbstractPanel {
    @ViewComponent(names = "icon-title")
    private IconTitle iconTitle;
    @ViewComponent(names = "close-icon-button")
    private IconButton iconButton;

    @Override
    public void updateContainerPosition() {
        iconTitle.setWidgetPosition(0, getLayoutCenterY(iconTitle));
        iconButton.setWidgetPosition(getLayoutWidth() - iconButton.getLayoutWidth(), 0);
    }
}