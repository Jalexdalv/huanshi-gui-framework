package com.huanshi.gui.common.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@SuppressWarnings("all")
@AllArgsConstructor
public class Size implements Cloneable {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    @Getter
    private int width;
    @Getter
    private int height;

    @SneakyThrows
    @Override
    @NotNull
    public Size clone() {
        Size size = (Size) super.clone();
        size.width = width;
        size.height = height;
        return size;
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

    public void setWidth(int width) {
        if (this.width != width) {
            this.width = width;
            firePropertyChange("size", this);
        }
    }

    public void setHeight(int height) {
        if (this.height != height) {
            this.height = height;
            firePropertyChange("size", this);
        }
    }

    public void setSize(int width, int height) {
        if (this.height != height || this.width != width) {
            this.width = width;
            this.height = height;
            firePropertyChange("size", this);
        }
    }
}