package com.huanshi.gui.common.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public class AnnotationTypeException extends RuntimeException {
    public AnnotationTypeException(@NotNull Annotation annotation, @NotNull Field field) {
        super("注解 [" + annotation + "] 不能加在变量 [" + field.getName() + "] 上");
    }

    public AnnotationTypeException(@NotNull Annotation annotation, @NotNull Class<?> clazz) {
        super("注解 [" + annotation + "] 不能加在类 [" + clazz.getSimpleName() + "] 上");
    }

    public AnnotationTypeException(@NotNull Field field, @NotNull Annotation... annotations) {
        super("注解：" + Arrays.toString(annotations) + " 不能同时加在变量 [" + field.getName() + "] 上");
    }

    public AnnotationTypeException(@NotNull Class<?> clazz, @NotNull Annotation... annotations) {
        super("注解：" + Arrays.toString(annotations) + " 不能同时加在类 [" + clazz.getSimpleName() + "] 上");
    }
}