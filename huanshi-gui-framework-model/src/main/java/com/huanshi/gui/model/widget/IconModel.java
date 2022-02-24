package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.model.AbstractModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

@Getter
public class IconModel extends AbstractModel {
    private Icon icon;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        icon = parser.parseIcon(key);
    }

    public void setIcon(@NotNull Icon icon) {
        if (!this.icon.equals(icon)) {
            this.icon = icon;
            firePropertyChange("icon", icon);
        }
    }
}