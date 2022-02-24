package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.IconModel;
import com.huanshi.gui.model.widget.TextFieldModel;

@SuppressWarnings("all")
public class IconTextFieldModel extends AbstractPanelModel {
    @ModelComponent(names = "icon")
    private IconModel iconModel;
    @ModelComponent(names = "text-field")
    private TextFieldModel textFieldModel;

    public void reset() {
        textFieldModel.reset();
    }
}