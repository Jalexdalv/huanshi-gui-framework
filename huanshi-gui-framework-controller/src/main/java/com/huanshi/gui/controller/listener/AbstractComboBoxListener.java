package com.huanshi.gui.controller.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractComboBoxListener implements ItemListener, Listener {
    @Override
    public void init() {}

    @Override
    public void itemStateChanged(@NotNull ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            select((String) e.getItem());
        }
    }

    public abstract void select(@NotNull String item);
}