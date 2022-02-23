package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.TextFieldModel;
import com.huanshi.gui.model.widget.TextModel;

@SuppressWarnings("all")
public class DateTextFieldModel extends AbstractPanelModel {
    @ModelComponent(names = "year-text-field")
    private TextFieldModel yearTextFieldModel;
    @ModelComponent(names = "year-text")
    private TextModel yearTextModel;
    @ModelComponent(names = "month-text-field")
    private TextFieldModel monthTextFieldModel;
    @ModelComponent(names = "month-text")
    private TextModel monthTextModel;
    @ModelComponent(names = "day-text-field")
    private TextFieldModel dayTextFieldModel;
    @ModelComponent(names = "day-text")
    private TextModel dayTextModel;

    public void reset() {
        yearTextFieldModel.reset();
        monthTextFieldModel.reset();
        dayTextFieldModel.reset();
    }
}