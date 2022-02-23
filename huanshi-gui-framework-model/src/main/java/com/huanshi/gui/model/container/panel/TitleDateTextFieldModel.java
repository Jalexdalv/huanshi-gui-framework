package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.TextModel;

@SuppressWarnings("all")
public class TitleDateTextFieldModel extends AbstractPanelModel {
    @ModelComponent(names = "title-text")
    private TextModel textModel;
    @ModelComponent(names = "date-text-field")
    private DateTextFieldModel dateTextFieldModel;

    public void reset() {
        dateTextFieldModel.reset();
    }
}