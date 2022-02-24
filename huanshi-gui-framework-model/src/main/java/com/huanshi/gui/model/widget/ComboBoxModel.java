package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.exception.ComboBoxIndexOutOfBoundsException;
import com.huanshi.gui.common.exception.ComboBoxItemNotFoundException;
import com.huanshi.gui.model.AbstractModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

@SuppressWarnings(value = "all")
@Getter
public class ComboBoxModel extends AbstractModel {
    private Color background;
    private String[] items;
    private Font font;
    private Color foreground;
    private boolean isEnable;
    private int selectedIndex = 0;
    private String selectedItem;
    private boolean isStopItemListener = false;

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        key.addLast("items"); items = parser.parseStringArray(false, false, false, key); key.removeLast();
        key.addLast("font"); font = parser.parseFont(key); key.removeLast();
        key.addLast("foreground"); foreground = parser.parseColor(key); key.removeLast();
        key.addLast("enable"); isEnable = parser.parseBoolean(false, key); key.removeLast();
        selectedItem = items[0];
    }

    public void setEnable(boolean isEnable) {
        if (this.isEnable != isEnable) {
            this.isEnable = isEnable;
            firePropertyChange("enable", isEnable);
        }
    }

    public void setSelectedIndex(int selectedIndex, boolean isFire, boolean isStopItemListener) {
        if (this.selectedIndex != selectedIndex) {
            if (selectedIndex < 0 || selectedIndex >= items.length) {
                throw new ComboBoxIndexOutOfBoundsException(getKey(), selectedIndex);
            }
            setStopItemListener(isStopItemListener);
            this.selectedIndex = selectedIndex;
            if (isFire) {
                firePropertyChange("selected-index", selectedIndex);
            }
            setStopItemListener(false);
        }
    }

    public void setSelectedItem(@NotNull String selectedItem, boolean isFire, boolean isStopItemListener) {
        if (!this.selectedItem.equals(selectedItem)) {
            if (!Arrays.asList(items).contains(selectedItem)) {
                throw new ComboBoxItemNotFoundException(getKey(), selectedItem);
            }
            setStopItemListener(isStopItemListener);
            this.selectedItem = selectedItem;
            if (isFire) {
                firePropertyChange("selected-item", selectedItem);
            }
            setStopItemListener(false);
        }
    }

    public void setStopItemListener(boolean isStopItemListener) {
        if (this.isStopItemListener != isStopItemListener) {
            this.isStopItemListener = isStopItemListener;
            firePropertyChange("stop-item-listener", isStopItemListener);
        }
    }

    public void reset(boolean isStopItemListener) {
        setSelectedIndex(0, true, isStopItemListener);
    }
}