package com.huanshi.gui.model;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Margin;
import com.huanshi.gui.common.data.Padding;
import com.huanshi.gui.common.utils.ReflectUtils;
import java.awt.GraphicsEnvironment;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("all")
public abstract class AbstractModel {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    @Getter
    private final int screenWidth = (int) graphicsEnvironment.getMaximumWindowBounds().getWidth();
    @Getter
    private final int screenHeight = (int) graphicsEnvironment.getMaximumWindowBounds().getHeight();
    @Getter
    private Key key;
    @Getter
    private Padding padding;
    @Getter
    private Margin margin;

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

    public void init(@NotNull Parser parser, @NotNull Key key) {
        this.key = key.clone();
        key.addLast("padding"); padding = parser.parsePadding(key); key.removeLast();
        key.addLast("margin"); margin = parser.parseMargin(key); key.removeLast();
    }

    @SneakyThrows
    public void superInit(@NotNull Parser parser, @NotNull Key key) {
        for (Class<?> clazz : ReflectUtils.getSuperClassList(getClass())) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals("init") && method.getParameters().length == 2 && method.getParameters()[0].getType().equals(Parser.class) && method.getParameters()[1].getType().equals(Key.class)) {
                    ReflectUtils.getMethodHandle(method).invoke(this, parser, key);
                }
            }
        }
    }
}