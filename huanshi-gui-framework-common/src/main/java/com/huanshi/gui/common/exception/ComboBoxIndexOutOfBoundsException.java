package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class ComboBoxIndexOutOfBoundsException extends RuntimeException {
    public ComboBoxIndexOutOfBoundsException(@NotNull Key key, int index) {
        super("下拉选择框 [" + key.getValue() + "] 的选项序号 [" + index + "] 超出界限");
    }
}