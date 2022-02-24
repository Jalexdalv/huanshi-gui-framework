package com.huanshi.gui.controller.manager;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@SuppressWarnings("all")
public abstract class AbstractExcelManager extends AbstractManager {
    public void write(@NotNull File file, @NotNull ExcelHandler excelHandler, @NotNull ExceptionHandler exceptionHandler) {
        try {
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
            excelHandler.handle(writableWorkbook.createSheet("sheet1", 0));
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (Throwable throwable) {
            exceptionHandler.handle(throwable);
        }
    }
}