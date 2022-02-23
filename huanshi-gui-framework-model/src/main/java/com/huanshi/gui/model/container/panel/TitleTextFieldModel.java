package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.TextFieldModel;
import com.huanshi.gui.model.widget.TextModel;

@SuppressWarnings("all")
public class TitleTextFieldModel extends AbstractPanelModel {
    @ModelComponent(names = "title-text")
    private TextModel textModel;
    @ModelComponent(names = "text-field")
    private TextFieldModel textFieldModel;

    public void reset() {
        textFieldModel.reset();
    }
}