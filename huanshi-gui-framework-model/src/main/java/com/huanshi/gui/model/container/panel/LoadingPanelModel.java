package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.LoadingSize;
import com.huanshi.gui.model.AbstractModel;
import java.awt.Color;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
@Getter
public class LoadingPanelModel extends AbstractPanelModel {
    private Color strokeBackground;
    private int delay;
    private LoadingSize loadingSize;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("stroke-background"); strokeBackground = parser.parseColor(key); key.removeLast();
        key.addLast("delay"); delay = parser.parseInteger(false, key); key.removeLast();
        key.addLast("loading-size"); loadingSize = parser.parseLoadingSize(key); key.removeLast();
    }
}