package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.model.container.scrollpane.TextAreaScrollPaneModel;
import com.huanshi.gui.model.widget.TextModel;

@SuppressWarnings("all")
public class TitleTextAreaScrollPaneModel extends AbstractPanelModel {
    @ModelComponent(names = "title-text")
    private TextModel textModel;
    @ModelComponent(names = "text-area-scroll-pane")
    private TextAreaScrollPaneModel textAreaScrollPaneModel;

    public void reset() {
        textAreaScrollPaneModel.reset();
    }
}