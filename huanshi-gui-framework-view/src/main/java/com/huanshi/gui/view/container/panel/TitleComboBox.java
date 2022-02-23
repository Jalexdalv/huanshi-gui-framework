package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.widget.ComboBox;
import com.huanshi.gui.view.widget.Text;

@SuppressWarnings("all")
public class TitleComboBox extends AbstractPanel {
    @ViewComponent(names = "title-text")
    private Text text;
    @ViewComponent(names = "combo-box")
    private ComboBox comboBox;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(text.getLayoutWidth() + comboBox.getLayoutWidth(), Math.max(text.getLayoutHeight(), comboBox.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        text.setWidgetPosition(0, getLayoutCenterY(text));
        comboBox.setWidgetPosition(text.getLayoutWidth(), getLayoutCenterY(comboBox));
    }
}