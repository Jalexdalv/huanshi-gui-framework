package com.huanshi.gui.common.exception;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException() {
        super("未找到应用入口");
    }
}