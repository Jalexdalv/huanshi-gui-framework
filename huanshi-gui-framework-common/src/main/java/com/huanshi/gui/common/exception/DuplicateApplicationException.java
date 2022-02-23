package com.huanshi.gui.common.exception;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public class DuplicateApplicationException extends RuntimeException {
    public DuplicateApplicationException(@NotNull Class<?>... classes) {
        super("重复的应用入口：" + Arrays.toString(classes));
    }
}