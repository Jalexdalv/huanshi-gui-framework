package com.huanshi.gui.common.exception;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DuplicateApplicationException extends RuntimeException {
    public DuplicateApplicationException(@NotNull Class<?>... classes) {
        super("重复的应用入口：" + Arrays.toString(classes));
    }
}