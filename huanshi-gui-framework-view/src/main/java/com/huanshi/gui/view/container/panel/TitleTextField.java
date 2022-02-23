package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.widget.Text;
import com.huanshi.gui.view.widget.TextField;

@SuppressWarnings("all")
public class TitleTextField extends AbstractPanel {
    @ViewComponent(names = "title-text")
    private Text text;
    @ViewComponent(names = "text-field")
    private TextField textField;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(text.getLayoutWidth() + textField.getLayoutWidth(), Math.max(text.getLayoutHeight(), textField.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        text.setWidgetPosition(0, getLayoutCenterY(text));
        textField.setWidgetPosition(text.getLayoutWidth(), getLayoutCenterY(textField));
    }
}