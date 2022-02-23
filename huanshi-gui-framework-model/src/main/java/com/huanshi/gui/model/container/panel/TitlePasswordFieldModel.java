package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.PasswordFieldModel;
import com.huanshi.gui.model.widget.TextModel;

@SuppressWarnings("all")
public class TitlePasswordFieldModel extends AbstractPanelModel {
    @ModelComponent(names = "title-text")
    private TextModel textModel;
    @ModelComponent(names = "password-field")
    private PasswordFieldModel passwordFieldModel;

    public void reset() {
        passwordFieldModel.reset();
    }
}