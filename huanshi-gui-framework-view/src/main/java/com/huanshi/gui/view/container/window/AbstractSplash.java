package com.huanshi.gui.view.container.window;

import java.awt.GraphicsEnvironment;
import javax.swing.JWindow;
import lombok.Getter;

public abstract class AbstractSplash extends JWindow {
    private static final GraphicsEnvironment GRAPHICS_ENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
    @Getter
    private static final int SCREEN_WIDTH = (int) GRAPHICS_ENVIRONMENT.getMaximumWindowBounds().getWidth();
    @Getter
    private static final int SCREEN_HEIGHT = (int) GRAPHICS_ENVIRONMENT.getMaximumWindowBounds().getHeight();
    @Getter
    private static final double WIDTH_SCALE = GRAPHICS_ENVIRONMENT.getDefaultScreenDevice().getDefaultConfiguration().getBounds().getWidth() / (double) 1920;
    @Getter
    private static final double HEIGHT_SCALE = GRAPHICS_ENVIRONMENT.getDefaultScreenDevice().getDefaultConfiguration().getBounds().getHeight() / (double) 1080;

    public void start() {
        setVisible(true);
    }

    public void destroy() {
        setVisible(false);
        dispose();
    }
}