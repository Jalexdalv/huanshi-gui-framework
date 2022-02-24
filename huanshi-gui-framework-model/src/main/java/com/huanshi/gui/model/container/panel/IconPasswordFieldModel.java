package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.IconModel;
import com.huanshi.gui.model.widget.PasswordFieldModel;

@SuppressWarnings("all")
public class IconPasswordFieldModel extends AbstractPanelModel {
    @ModelComponent(names = "icon")
    private IconModel iconModel;
    @ModelComponent(names = "password-field")
    private PasswordFieldModel passwordFieldModel;

    public void reset() {
        passwordFieldModel.reset();
    }
}