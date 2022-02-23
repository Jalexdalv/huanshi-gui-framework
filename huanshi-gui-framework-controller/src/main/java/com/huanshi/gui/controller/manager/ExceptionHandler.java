package com.huanshi.gui.controller.manager;

import org.jetbrains.annotations.NotNull;

public interface ExceptionHandler {
    void handle(@NotNull Throwable throwable);
}