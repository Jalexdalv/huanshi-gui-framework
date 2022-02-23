package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.widget.Icon;
import com.huanshi.gui.view.widget.Text;

@SuppressWarnings("all")
public class IconTitle extends AbstractPanel {
    @ViewComponent(names = "icon")
    private Icon icon;
    @ViewComponent(names = "title-text")
    private Text text;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(icon.getLayoutWidth() + text.getLayoutWidth(), Math.max(icon.getLayoutHeight(), text.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        icon.setWidgetPosition(0, getLayoutCenterY(icon));
        text.setWidgetPosition(icon.getLayoutWidth(), getLayoutCenterY(text));
    }
}