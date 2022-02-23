package com.huanshi.gui.controller.manager;

import javax.swing.JFileChooser;
import org.jetbrains.annotations.NotNull;

public interface SaveHandler {
    void handle(@NotNull JFileChooser fileChooser);
}