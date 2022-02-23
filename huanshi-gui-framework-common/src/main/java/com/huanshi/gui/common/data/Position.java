package com.huanshi.gui.common.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("all")
@AllArgsConstructor
public class Position implements Cloneable {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    @Getter
    private int x;
    @Getter
    private int y;

    @SneakyThrows
    @Override
    @NotNull
    public Position clone() {
        Position position = (Position) super.clone();
        position.x = x;
        position.y = y;
        return position;
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

    public void setX(int x) {
        if (this.x != x) {
            this.x = x;
            firePropertyChange("position", this);
        }
    }

    public void setY(int y) {
        if (this.y != y) {
            this.y = y;
            firePropertyChange("position", this);
        }
    }

    public void setPosition(int x, int y) {
        if (this.x != x || this.y != y) {
            this.x = x;
            this.y = y;
            firePropertyChange("position", this);
        }
    }
}