package com.huanshi.gui.view.container;

import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.common.utils.ReflectUtils;
import com.huanshi.gui.view.widget.Widget;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.awt.Component;
import java.lang.reflect.Method;
import java.util.LinkedList;

@SuppressWarnings("all")
public interface Container extends Widget {
    @NotNull
    LinkedList<Widget> getWidgetList();
    void updateContainerSize();
    void updateContainerPosition();

    @SneakyThrows
    default void superUpdateContainerSize() {
        for (Class<?> clazz : ReflectUtils.getSuperClassList(getClass())) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals("updateContainerSize") && method.getParameters().length == 0) {
                    ReflectUtils.getMethodHandle(method).invoke(this);
                }
            }
        }
    }

    @SneakyThrows
    default void superUpdateContainerPosition() {
        for (Class<?> clazz : ReflectUtils.getSuperClassList(getClass())) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals("updateContainerPosition") && method.getParameters().length == 0) {
                    ReflectUtils.getMethodHandle(method).invoke(this);
                }
            }
        }
    }

    default void addWidget(@NotNull Widget widget) {
        getWidgetList().add(widget);
        ((java.awt.Container) this).add((Component) widget);
    }

    default void removeWidget(@NotNull Widget widget) {
        getWidgetList().remove(widget);
        ((java.awt.Container) this).remove((Component) widget);
    }

    default void renderContainer() {
        for (Widget widget : getWidgetList()) {
            if (widget instanceof Container container) {
                container.renderContainer();
            } else {
                widget.renderWidget();
            }
        }
        renderWidget();
    }

    @Override
    default void renderWidget() {
        Widget.super.renderWidget();
        ((java.awt.Container) this).validate();
    }

    default int getLayoutCenterX(@NotNull Widget widget) {
        return GuiUtils.divide(getRealWidth() - widget.getLayoutWidth(), 2);
    }

    default int getLayoutCenterY(@NotNull Widget widget) {
        return GuiUtils.divide(getRealHeight() - widget.getLayoutHeight(), 2);
    }
}