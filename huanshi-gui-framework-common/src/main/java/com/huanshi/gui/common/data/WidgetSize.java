package com.huanshi.gui.common.data;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@SuppressWarnings("all")
public class WidgetSize {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    @Getter
    private final Size size;
    @Getter
    private final Padding padding;
    @Getter
    private final Margin margin;

    public WidgetSize(@NotNull Size size, @NotNull Padding padding, @NotNull Margin margin) {
        this.size = size;
        this.padding = padding;
        this.margin = margin;
        size.addPropertyChangeListener(e -> {
            if ("size".equals(e.getPropertyName())) {
                firePropertyChange("size", e.getNewValue());
            }
        });
    }

    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    public void firePropertyChange(@NotNull String property, @Nullable Object oldValue, @Nullable Object newValue) {
        propertyChangeSupport.firePropertyChange(property, oldValue, newValue);
    }

    public void firePropertyChange(@NotNull String property, @Nullable Object newValue) {
        firePropertyChange(property, null, newValue);
    }

    public int getRealWidth() {
        return size.getWidth() + padding.x();
    }

    public int getRealHeight() {
        return size.getHeight() + padding.y();
    }

    public int getLayoutWidth() {
        return getRealWidth() + margin.left() + margin.right();
    }

    public int getLayoutHeight() {
        return getRealHeight() + margin.top() + margin.bottom();
    }
}