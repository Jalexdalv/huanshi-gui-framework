package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.view.widget.Text;

@SuppressWarnings("all")
public class TitleDateTextField extends AbstractPanel {
    @ViewComponent(names = "title-text")
    private Text text;
    @ViewComponent(names = "date-text-field")
    private DateTextField dateTextField;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(text.getLayoutWidth() + dateTextField.getLayoutWidth(), GuiUtils.getMax(text.getLayoutHeight(), dateTextField.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        text.setWidgetPosition(0, getLayoutCenterY(text));
        dateTextField.setWidgetPosition(text.getLayoutWidth(), getLayoutCenterY(dateTextField));
    }
}