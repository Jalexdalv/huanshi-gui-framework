package com.huanshi.gui.common.exception;

import java.lang.annotation.Annotation;
import org.jetbrains.annotations.NotNull;

public class IllegalAnnotationParameterException extends RuntimeException {
    public IllegalAnnotationParameterException(@NotNull Annotation annotation) {
        super("注解 [" + annotation + "] 的参数不合法");
    }
}