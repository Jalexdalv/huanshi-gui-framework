package com.huanshi.gui.core;

import com.huanshi.gui.application.AbstractApplication;
import com.huanshi.gui.common.annotation.Application;
import com.huanshi.gui.common.annotation.Listener;
import com.huanshi.gui.common.annotation.Manager;
import com.huanshi.gui.common.annotation.Model;
import com.huanshi.gui.common.annotation.View;
import com.huanshi.gui.common.exception.AnnotationTypeException;
import com.huanshi.gui.common.exception.ApplicationNotFoundException;
import com.huanshi.gui.common.exception.DuplicateApplicationException;
import com.huanshi.gui.common.type.EnvironmentType;
import com.huanshi.gui.common.utils.ReflectUtils;
import com.huanshi.gui.controller.manager.AbstractManager;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.view.container.Container;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@SuppressWarnings("all")
public class Scanner {
    @SneakyThrows
    public static void scan(@NotNull Class<?> startClass, @NotNull EnvironmentType environmentType) {
        HashMap<Class<?>, Model> modelMap = new HashMap<>();
        HashMap<Class<?>, View> viewMap = new HashMap<>();
        HashMap<Class<?>, Manager> managerMap = new HashMap<>();
        HashMap<Class<?>, Listener> listenerMap = new HashMap<>();
        Class<?> applicationClass = null;
        for (Class<?> clazz : getClassList(startClass, environmentType)) {
            Model model = clazz.getAnnotation(Model.class);
            View view = clazz.getAnnotation(View.class);
            Manager manager = clazz.getAnnotation(Manager.class);
            Listener listener = clazz.getAnnotation(Listener.class);
            Application application = clazz.getAnnotation(Application.class);
            if (ReflectUtils.hasConflictAnnotation(model, view, manager, listener, application)) {
                throw new AnnotationTypeException(clazz, model, view, manager, listener, application);
            }
            if (model != null) {
                if (!AbstractModel.class.isAssignableFrom(clazz)) {
                    throw new AnnotationTypeException(model, clazz);
                }
                modelMap.put(clazz, model);
            } else if (view != null) {
                if (!Container.class.isAssignableFrom(clazz)) {
                    throw new AnnotationTypeException(view, clazz);
                }
                viewMap.put(clazz, view);
            } else if (manager != null) {
                if (!AbstractManager.class.isAssignableFrom(clazz)) {
                    throw new AnnotationTypeException(manager, clazz);
                }
                managerMap.put(clazz, manager);
            } else if (listener != null) {
                if (!com.huanshi.gui.controller.listener.Listener.class.isAssignableFrom(clazz)) {
                    throw new AnnotationTypeException(listener, clazz);
                }
                listenerMap.put(clazz, listener);
            } else if (application != null) {
                if (!AbstractApplication.class.isAssignableFrom(clazz)) {
                    throw new AnnotationTypeException(application, clazz);
                }
                if (applicationClass != null) {
                    throw new DuplicateApplicationException(applicationClass, clazz);
                }
                applicationClass = clazz;
            }
        }
        modelMap.forEach((clazz, model) -> Loader.loadModel(model.name(), model.config(), clazz));
        viewMap.forEach((clazz, view) -> Loader.loadView(view.name(), clazz));
        managerMap.forEach((clazz, manager) -> Loader.loadManager(manager.name(), clazz));
        listenerMap.forEach((clazz, listener) -> Loader.loadListener(listener.names(), clazz));
        if (applicationClass == null) {
            throw new ApplicationNotFoundException();
        }
        Loader.loadApplication(applicationClass);
    }

    @SneakyThrows
    @NotNull
    private static LinkedList<Class<?>> getClassList(@NotNull Class<?> startClass, @NotNull EnvironmentType environmentType) {
        LinkedList<Class<?>> classList = new LinkedList<>();
        switch (environmentType) {
            case DEV -> {
                for (String classPath : getClassPathList(new File("").getCanonicalFile())) {
                    if (classPath.endsWith(".class")) {
                        classList.add(Class.forName(classPath.split("classes\\\\")[1].replace(".class", "").replace("\\", ".")));
                    }
                }
            }
            case PROD -> {
                JarFile jarFile = new JarFile(startClass.getProtectionDomain().getCodeSource().getLocation().getPath());
                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                while (jarEntryEnumeration.hasMoreElements()) {
                    JarEntry jarEntry = jarEntryEnumeration.nextElement();
                    if (jarEntry.getName().endsWith(".class") && jarEntry.getName().startsWith("com/huanshi/") && !jarEntry.getName().contains("$")) {
                        classList.add(Class.forName(jarEntry.getName().replaceAll("/", ".").replaceAll(".class", "")));
                    }
                }
            }
        }
        return classList;
    }

    @NotNull
    private static LinkedList<String> getClassPathList(@NotNull File startFile) {
        LinkedList<String> classPathList = new LinkedList<>();
        if (startFile.isDirectory()) {
            File[] files = startFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    classPathList.addAll(getClassPathList(file));
                }
            }
        } else {
            classPathList.add(startFile.getAbsolutePath());
        }
        return classPathList;
    }
}