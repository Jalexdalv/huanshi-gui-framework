package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class ManagerNotFoundException extends RuntimeException {
    public ManagerNotFoundException(@NotNull Key key) {
        super("未找到键 [" + key.getValue() + "] 对应的管理器");
    }
}