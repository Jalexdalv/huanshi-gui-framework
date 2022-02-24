package com.huanshi.gui.view;

import com.huanshi.gui.view.widget.Widget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Component;
import java.awt.event.MouseEvent;

public class MouseLock {
    private static Widget enteredWidget;
    private static Widget lockedWidget;
    private static MouseEvent mouseEvent;

    @Nullable
    public static Widget getEnteredWidget() {
        return enteredWidget;
    }

    @Nullable
    public static Widget getLockedWidget() {
        return lockedWidget;
    }

    public static boolean isLock() {
        return lockedWidget != null;
    }

    public static void enter(@NotNull Widget widget) {
        enteredWidget = widget;
    }

    public static void enter(@NotNull Widget widget, @NotNull MouseEvent mouseEvent) {
        enteredWidget = widget;
        MouseLock.mouseEvent = mouseEvent;
    }

    public static void exit() {
        enteredWidget = null;
        mouseEvent = null;
    }

    public static void lock(@NotNull Widget widget) {
        lockedWidget = widget;
    }

    public static void unLock() {
        lockedWidget = null;
        if (mouseEvent != null) {
            ((Component) enteredWidget).dispatchEvent(mouseEvent);
        }
    }

    public static void reset() {
        mouseEvent = null;
        enteredWidget = null;
        lockedWidget = null;
    }
}