package com.huanshi.gui.controller.manager;

import jxl.write.WritableSheet;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public interface ExcelHandler {
    void handle(@NotNull WritableSheet writableSheet);
}