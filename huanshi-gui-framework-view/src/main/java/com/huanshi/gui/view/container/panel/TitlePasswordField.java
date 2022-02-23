package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.widget.PasswordField;
import com.huanshi.gui.view.widget.Text;

@SuppressWarnings("all")
public class TitlePasswordField extends AbstractPanel {
    @ViewComponent(names = "title-text")
    private Text text;
    @ViewComponent(names = "password-field")
    private PasswordField passwordField;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(text.getLayoutWidth() + passwordField.getLayoutWidth(), Math.max(text.getLayoutHeight(), passwordField.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        text.setWidgetPosition(0, getLayoutCenterY(text));
        passwordField.setWidgetPosition(text.getLayoutWidth(), getLayoutCenterY(passwordField));
    }
}