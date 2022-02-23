package com.huanshi.gui.common.data.table;

import org.jetbrains.annotations.NotNull;

public abstract class TableData {
    @NotNull
    public abstract Object[] convertToArray();
    @Override
    public abstract boolean equals(@NotNull Object object);
}