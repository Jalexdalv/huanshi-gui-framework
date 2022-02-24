package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.utils.ReflectUtils;
import com.huanshi.gui.model.AbstractModel;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.Method;

@SuppressWarnings("all")
public interface Widget {
    void init(@NotNull AbstractModel model);
    void updateWidgetSize();
    void updateWidgetPosition();
    @NotNull
    Key getKey();
    @NotNull
    WidgetSize getWidgetSize();
    @NotNull
    WidgetPosition getWidgetPosition();

    @SneakyThrows
    default void superInit(@NotNull AbstractModel model) {
        for (Class<?> clazz : ReflectUtils.getSuperClassList(getClass())) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals("init") && method.getParameters().length == 1 && method.getParameters()[0].getType().equals(AbstractModel.class)) {
                    ReflectUtils.getMethodHandle(method).invoke(this, model);
                }
            }
        }
    }

    @SneakyThrows
    default void superUpdateWidgetSize() {
        for (Class<?> clazz : ReflectUtils.getSuperClassList(getClass())) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals("updateWidgetSize") && method.getParameters().length == 0) {
                    ReflectUtils.getMethodHandle(method).invoke(this);
                }
            }
        }
    }

    @SneakyThrows
    default void superUpdateWidgetPosition() {
        for (Class<?> clazz : ReflectUtils.getSuperClassList(getClass())) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals("updateWidgetPosition") && method.getParameters().length == 0) {
                    ReflectUtils.getMethodHandle(method).invoke(this);
                }
            }
        }
    }

    default int getPreferredWidth() {
        return (int) ((Component) this).getPreferredSize().getWidth();
    }

    default int getPreferredHeight() {
        return (int) ((Component) this).getPreferredSize().getHeight();
    }

    default int getWidgetWidth() {
        return getWidgetSize().getSize().getWidth();
    }

    default int getWidgetHeight() {
        return getWidgetSize().getSize().getHeight();
    }

    default int getWidgetX() {
        return getWidgetPosition().getPosition().getX();
    }

    default int getWidgetY() {
        return getWidgetPosition().getPosition().getY();
    }

    default int getRealWidth() {
        return getWidgetSize().getRealWidth();
    }

    default int getRealHeight() {
        return getWidgetSize().getRealHeight();
    }

    default int getLayoutWidth() {
        return getWidgetSize().getLayoutWidth();
    }

    default int getLayoutHeight() {
        return getWidgetSize().getLayoutHeight();
    }

    default int getLayoutX() {
        return getWidgetPosition().getLayoutX();
    }

    default int getLayoutY() {
        return getWidgetPosition().getLayoutY();
    }

    default void setPreferredSize(int width, int height) {
        ((Component) this).setPreferredSize(new Dimension(width, height));
    }

    default void setWidgetWidth(int width) {
        getWidgetSize().getSize().setWidth(width);
    }

    default void setWidgetHeight(int height) {
        getWidgetSize().getSize().setHeight(height);
    }

    default void setWidgetSize(int width, int height) {
        getWidgetSize().getSize().setSize(width, height);
    }

    default void setWidgetX(int x) {
        getWidgetPosition().getPosition().setX(x);
    }

    default void setWidgetY(int y) {
        getWidgetPosition().getPosition().setY(y);
    }

    default void setWidgetPosition(int x, int y) {
        getWidgetPosition().getPosition().setPosition(x, y);
    }

    default void renderWidget() {
        ((Component) this).setBounds(getLayoutX(), getLayoutY(), getRealWidth(), getRealHeight());
    }
}