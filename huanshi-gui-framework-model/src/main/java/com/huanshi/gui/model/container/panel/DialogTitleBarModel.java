package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.IconButtonModel;

@SuppressWarnings("all")
public class DialogTitleBarModel extends AbstractPanelModel {
    @ModelComponent(names = "icon-title")
    private IconTitleModel iconTitleModel;
    @ModelComponent(names = "close-icon-button")
    private IconButtonModel iconButtonModel;
}