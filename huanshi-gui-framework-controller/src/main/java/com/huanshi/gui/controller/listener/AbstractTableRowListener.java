package com.huanshi.gui.controller.listener;

import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AbstractTableRowListener extends MouseAdapter implements Listener {
    @Override
    public void init() {}

    @Override
    public void mouseClicked(@NotNull MouseEvent e) {
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            doubleClick();
        }
    }

    public abstract void doubleClick();
}