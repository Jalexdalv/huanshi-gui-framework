package com.huanshi.gui.common.exception;

import com.huanshi.gui.common.data.Key;
import org.jetbrains.annotations.NotNull;

public class TableRowOutOfBoundsException extends RuntimeException {
    public TableRowOutOfBoundsException(@NotNull Key key, int row) {
        super("表格 [" + key.getValue() + "] 的行号 [" + row + "] 超出界限");
    }
}