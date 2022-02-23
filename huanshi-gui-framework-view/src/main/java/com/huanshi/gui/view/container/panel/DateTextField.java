package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.view.widget.Text;
import com.huanshi.gui.view.widget.TextField;

@SuppressWarnings("all")
public class DateTextField extends AbstractPanel {
    @ViewComponent(names = "year-text-field")
    private TextField yearTextField;
    @ViewComponent(names = "year-text")
    private Text yearText;
    @ViewComponent(names = "month-text-field")
    private TextField monthTextField;
    @ViewComponent(names = "month-text")
    private Text monthText;
    @ViewComponent(names = "day-text-field")
    private TextField dayTextField;
    @ViewComponent(names = "day-text")
    private Text dayText;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(yearTextField.getLayoutWidth() + yearText.getLayoutWidth() + monthTextField.getLayoutWidth() + monthText.getLayoutWidth() + dayTextField.getLayoutWidth() + dayText.getLayoutWidth(), GuiUtils.getMax(yearTextField.getLayoutHeight(), yearText.getLayoutHeight(), monthTextField.getLayoutHeight(), monthText.getLayoutHeight(), dayTextField.getLayoutHeight(), dayText.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        yearTextField.setWidgetPosition(0, getLayoutCenterY(yearTextField));
        yearText.setWidgetPosition(yearTextField.getLayoutWidth(), getLayoutCenterY(yearText));
        monthTextField.setWidgetPosition(yearTextField.getLayoutWidth() + yearText.getLayoutWidth(), getLayoutCenterY(monthTextField));
        monthText.setWidgetPosition(yearTextField.getLayoutWidth() + yearText.getLayoutWidth() + monthTextField.getLayoutWidth(), getLayoutCenterY(monthText));
        dayTextField.setWidgetPosition(yearTextField.getLayoutWidth() + yearText.getLayoutWidth() + monthTextField.getLayoutWidth() + monthText.getLayoutWidth(), getLayoutCenterY(dayTextField));
        dayText.setWidgetPosition(yearTextField.getLayoutWidth() + yearText.getLayoutWidth() + monthTextField.getLayoutWidth() + monthText.getLayoutWidth() + dayTextField.getLayoutWidth(), getLayoutCenterY(dayText));
    }
}