package com.huanshi.gui.common.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("all")
public class WidgetPosition {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    @Getter
    private final Position position;
    @Getter
    private final Margin margin;

    public WidgetPosition(@NotNull Position position, @NotNull Margin margin) {
        this.position = position;
        this.margin = margin;
        position.addPropertyChangeListener(e -> {
            if ("position".equals(e.getPropertyName())) {
                firePropertyChange("position", e.getNewValue());
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

    public int getLayoutX() {
        return position.getX() + margin.left();
    }

    public int getLayoutY() {
        return position.getY() + margin.top();
    }
}