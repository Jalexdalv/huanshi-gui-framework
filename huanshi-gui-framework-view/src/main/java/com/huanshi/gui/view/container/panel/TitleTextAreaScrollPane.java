package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.view.container.scrollpane.TextAreaScrollPane;
import com.huanshi.gui.view.widget.Text;

@SuppressWarnings("all")
public class TitleTextAreaScrollPane extends AbstractPanel {
    @ViewComponent(names = "title-text")
    private Text text;
    @ViewComponent(names = "text-area-scroll-pane")
    private TextAreaScrollPane textAreaScrollPane;

    @Override
    public void updateWidgetSize() {
        setWidgetSize(text.getLayoutWidth() + textAreaScrollPane.getLayoutWidth(), Math.max(text.getLayoutHeight(), textAreaScrollPane.getLayoutHeight()));
    }

    @Override
    public void updateContainerPosition() {
        text.setWidgetPosition(0, 0);
        textAreaScrollPane.setWidgetPosition(text.getLayoutWidth(), 0);
    }
}