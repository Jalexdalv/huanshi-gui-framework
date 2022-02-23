package com.huanshi.gui.model.container.scrollpane;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.ScrollBarSize;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.type.ScrollDirection;
import com.huanshi.gui.model.AbstractModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
@Getter
public abstract class AbstractScrollPaneModel extends AbstractModel {
    private Size size;
    private ScrollBarSize scrollBarSize;
    private ScrollDirection scrollDirection;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("size"); size = parser.parseSize(key); key.removeLast();
        key.addLast("scroll-bar-size"); scrollBarSize = parser.parseScrollBarSize(key); key.removeLast();
        key.addLast("scroll-direction"); scrollDirection = parser.parseScrollDirection(key); key.removeLast();
    }

    public void reset() {
        firePropertyChange("reset", null);
    }
}