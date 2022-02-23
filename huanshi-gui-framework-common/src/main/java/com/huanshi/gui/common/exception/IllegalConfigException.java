package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class IllegalConfigException extends RuntimeException {
    public IllegalConfigException(@NotNull Key key) {
        super("键 [" + key.getValue() + "] 对应的配置不合法");
    }
}