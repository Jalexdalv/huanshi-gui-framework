package com.huanshi.gui.view.container.scrollpane;

import com.huanshi.gui.common.annotation.ViewportComponent;
import com.huanshi.gui.view.widget.TextArea;

@SuppressWarnings("all")
public class TextAreaScrollPane extends AbstractScrollPane {
    @ViewportComponent(names = "text-area")
    private TextArea textArea;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(textArea.getLayoutWidth(), textArea.getLayoutHeight());
    }

    @Override
    public void updateContainerPosition() {
        textArea.setWidgetPosition(0, 0);
    }
}