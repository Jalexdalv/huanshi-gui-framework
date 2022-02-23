package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class ModelNotMatchedException extends RuntimeException {
    public ModelNotMatchedException(@NotNull Key key, @NotNull Class<?> clazz) {
        super("键 [" + key.getValue() + "] 对应的数据模型与控件 [" + clazz.getSimpleName() + "] 不匹配");
    }
}