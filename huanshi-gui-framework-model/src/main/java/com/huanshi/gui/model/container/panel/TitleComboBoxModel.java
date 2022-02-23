package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.ComboBoxModel;
import com.huanshi.gui.model.widget.TextModel;

@SuppressWarnings("all")
public class TitleComboBoxModel extends AbstractPanelModel {
    @ModelComponent(names = "title-text")
    private TextModel textModel;
    @ModelComponent(names = "combo-box")
    private ComboBoxModel comboBoxModel;

    public void reset(boolean isStopItemListener) {
        comboBoxModel.reset(isStopItemListener);
    }
}