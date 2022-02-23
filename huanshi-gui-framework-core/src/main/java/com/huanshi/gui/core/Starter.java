package com.huanshi.gui.core;

import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.view.container.window.AbstractSplash;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class Starter {
    private static AbstractSplash splash;

    public static void showSplash() {
        if (splash != null) {
            splash.start();
        }
    }

    public static void hideSplash() {
        if (splash != null) {
            splash.destroy();
        }
    }

    public static void start(@NotNull Class<?> startClass, @NotNull Class<?> splashClass) {
        if (splashClass != null && AbstractSplash.class.isAssignableFrom(splashClass)) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                splash = (AbstractSplash) splashClass.getConstructor().newInstance();
                SwingUtilities.invokeLater(() -> {
                    showSplash();
                });
            } catch (Throwable throwable) {
                GuiUtils.showErrorDialog(throwable);
            }
        }
        try {
            Scanner.scan(startClass);
        } catch (Throwable throwable) {
            SwingUtilities.invokeLater(() -> {
                hideSplash();
            });
            GuiUtils.showErrorDialog(throwable);
        }
    }
}