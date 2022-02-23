package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class ViewNotFoundException extends RuntimeException {
    public ViewNotFoundException(@NotNull Key key) {
        super("未找到键 [" + key.getValue() + "] 对应的的视图");
    }
}