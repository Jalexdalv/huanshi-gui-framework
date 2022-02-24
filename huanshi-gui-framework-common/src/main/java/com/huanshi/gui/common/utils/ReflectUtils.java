package com.huanshi.gui.common.utils;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;

public class ReflectUtils {
    @NotNull
    public static LinkedList<Class<?>> getSuperClassList(@NotNull Class<?> clazz) {
        LinkedList<Class<?>> classList = new LinkedList<>();
        while (clazz != null && clazz.getPackageName().startsWith("com.huanshi")) {
            classList.addFirst(clazz);
            clazz = clazz.getSuperclass();
        }
        return classList;
    }

    @NotNull
    public static LinkedList<Field> getFieldList(@NotNull Class<?> clazz) {
        LinkedList<Field> fieldList = new LinkedList<>();
        while (clazz != null && clazz.getPackageName().startsWith("com.huanshi")) {
            fieldList.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    public static boolean hasConflictAnnotation(@NotNull Annotation... annotations) {
        boolean hasAnnotation = false;
        for (Annotation annotation : annotations) {
            if (annotation != null) {
                if (hasAnnotation) {
                    return true;
                }
                hasAnnotation = true;
            }
        }
        return false;
    }

    @SneakyThrows
    @NotNull
    public static MethodHandle getMethodHandle(@NotNull Method method) {
        return ((MethodHandles.Lookup) MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class).invoke(MethodHandles.class, method.getDeclaringClass(), MethodHandles.lookup())).unreflectSpecial(method, method.getDeclaringClass());
    }
}