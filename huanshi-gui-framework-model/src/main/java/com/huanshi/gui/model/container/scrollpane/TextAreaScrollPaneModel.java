package com.huanshi.gui.model.container.scrollpane;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.TextAreaModel;

@SuppressWarnings("all")
public class TextAreaScrollPaneModel extends AbstractScrollPaneModel {
    @ModelComponent(names = "text-area")
    private TextAreaModel textAreaModel;

    @Override
    public void reset() {
        textAreaModel.reset();
        super.reset();
    }
}