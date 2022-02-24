package com.huanshi.gui.common.exception;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class IllegalSubKeyException extends RuntimeException {
    public IllegalSubKeyException(@NotNull String subKey) {
        super("子键 [" + subKey + "] 不合法");
    }

    public IllegalSubKeyException(@NotNull String[] subKey) {
        super("子键：" + Arrays.toString(subKey) + " 不合法");
    }
}