package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.widget.Icon;
import com.huanshi.gui.view.widget.PasswordField;

@SuppressWarnings("all")
public class IconPasswordField extends AbstractPanel {
    @ViewComponent(names = "icon")
    private Icon icon;
    @ViewComponent(names = "password-field")
    private PasswordField passwordField;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(icon.getLayoutWidth() + passwordField.getLayoutWidth(), Math.max(icon.getLayoutHeight(), passwordField.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        icon.setWidgetPosition(0, getLayoutCenterY(icon));
        passwordField.setWidgetPosition(icon.getLayoutWidth(), getLayoutCenterY(passwordField));
    }
}