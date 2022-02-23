package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class ComboBoxItemNotFoundException extends RuntimeException {
    public ComboBoxItemNotFoundException(@NotNull Key key, @NotNull String item) {
        super("未找到下拉选择框 [" + key.getValue() + "] 的选项 [" + item + "]");
    }
}