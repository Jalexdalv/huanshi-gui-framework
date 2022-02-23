package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException(@NotNull Key key) {
        super("重复的键 [" + key.getValue() + "]");
    }
}