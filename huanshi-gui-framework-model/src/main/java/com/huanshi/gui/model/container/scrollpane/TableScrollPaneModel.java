package com.huanshi.gui.model.container.scrollpane;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.widget.TableModel;

@SuppressWarnings("all")
public class TableScrollPaneModel extends AbstractScrollPaneModel {
    @ModelComponent(names = "table")
    private TableModel tableModel;
}