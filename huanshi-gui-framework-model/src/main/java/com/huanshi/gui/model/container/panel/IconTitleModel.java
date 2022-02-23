package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.IconModel;
import com.huanshi.gui.model.widget.TextModel;

@SuppressWarnings("all")
public class IconTitleModel extends AbstractPanelModel {
    @ModelComponent(names = "icon")
    private IconModel iconModel;
    @ModelComponent(names = "title-text")
    private TextModel textModel;
}