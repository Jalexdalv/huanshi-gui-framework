package com.huanshi.gui.controller.manager;

import org.jetbrains.annotations.NotNull;

import javax.swing.JFileChooser;

public interface SaveHandler {
    void handle(@NotNull JFileChooser fileChooser);
}