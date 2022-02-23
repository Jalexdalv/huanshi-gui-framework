package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(@NotNull Key key) {
        super("未找到键 [" + key.getValue() + "] 对应的数据模型");
    }
}