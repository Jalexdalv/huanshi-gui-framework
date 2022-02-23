package com.huanshi.gui.common.exception;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public class IllegalSubKeyException extends RuntimeException {
    public IllegalSubKeyException(@NotNull String subKey) {
        super("子键 [" + subKey + "] 不合法");
    }

    public IllegalSubKeyException(@NotNull String[] subKey) {
        super("子键：" + Arrays.toString(subKey) + " 不合法");
    }
}