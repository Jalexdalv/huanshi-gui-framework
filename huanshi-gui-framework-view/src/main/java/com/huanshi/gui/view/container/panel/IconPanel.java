package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.widget.Icon;

@SuppressWarnings("all")
public class IconPanel extends AbstractPanel {
    @ViewComponent(names = "icon")
    private Icon icon;

    @Override
    public void updateContainerPosition() {
        icon.setWidgetPosition(getLayoutCenterX(icon), getLayoutCenterY(icon));
    }
}