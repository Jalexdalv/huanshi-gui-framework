package com.huanshi.gui.controller.listener;

import org.jetbrains.annotations.NotNull;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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