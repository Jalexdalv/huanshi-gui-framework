package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.widget.Icon;
import com.huanshi.gui.view.widget.TextField;

@SuppressWarnings("all")
public class IconTextField extends AbstractPanel {
    @ViewComponent(names = "icon")
    private Icon icon;
    @ViewComponent(names = "text-field")
    private TextField textField;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(icon.getLayoutWidth() + textField.getLayoutWidth(), Math.max(icon.getLayoutHeight(), textField.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        icon.setWidgetPosition(0, getLayoutCenterY(icon));
        textField.setWidgetPosition(icon.getLayoutWidth(), getLayoutCenterY(textField));
    }
}