package com.huanshi.gui.view.container.scrollpane;

import com.huanshi.gui.common.annotation.ViewportComponent;
import com.huanshi.gui.view.widget.Table;

@SuppressWarnings("all")
public class TableScrollPane extends AbstractScrollPane {
    @ViewportComponent(names = "table")
    private Table table;

    @Override
    public void updateContainerPosition() {
        table.setWidgetPosition(0, 0);
    }
}