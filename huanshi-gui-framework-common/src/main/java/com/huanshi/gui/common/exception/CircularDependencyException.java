package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CircularDependencyException extends RuntimeException {
    public CircularDependencyException(@NotNull Key... keys) {
        super("键：" + Arrays.toString(keys) + " 之间发生了循环依赖");
    }
}