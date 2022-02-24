package com.huanshi.gui.controller.manager;

import jxl.write.WritableSheet;
import org.jetbrains.annotations.NotNull;

public interface ExcelHandler {
    void handle(@NotNull WritableSheet writableSheet);
}