package com.huanshi.gui.view.container.window;

import lombok.Getter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import java.awt.Color;
import java.awt.GraphicsEnvironment;

public class Splash extends JWindow {
    private static final GraphicsEnvironment GRAPHICS_ENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
    @Getter
    private static final int SCREEN_WIDTH = (int) GRAPHICS_ENVIRONMENT.getMaximumWindowBounds().getWidth();
    @Getter
    private static final int SCREEN_HEIGHT = (int) GRAPHICS_ENVIRONMENT.getMaximumWindowBounds().getHeight();
    @Getter
    private static final double WIDTH_SCALE = GRAPHICS_ENVIRONMENT.getDefaultScreenDevice().getDefaultConfiguration().getBounds().getWidth() / (double) 1920;
    @Getter
    private static final double HEIGHT_SCALE = GRAPHICS_ENVIRONMENT.getDefaultScreenDevice().getDefaultConfiguration().getBounds().getHeight() / (double) 1080;

    public Splash(String path) {
        setLayout(null);
        setBackground(new Color(255, 255, 255));
        getContentPane().setBackground(new Color(255, 255, 255));
        ImageIcon imageIcon = new ImageIcon(path);
        JLabel icon = new JLabel(imageIcon);
        getContentPane().add(icon);
        setBounds((int) ((getSCREEN_WIDTH() - imageIcon.getIconWidth() * getWIDTH_SCALE()) / (double) 2), (int) ((getSCREEN_HEIGHT() - imageIcon.getIconHeight() * getHEIGHT_SCALE()) / (double) 2), (int) (imageIcon.getIconWidth() * getWIDTH_SCALE()), (int) (imageIcon.getIconHeight() * getHEIGHT_SCALE()));
    }

    public void start() {
        setVisible(true);
    }

    public void destroy() {
        setVisible(false);
        dispose();
    }
}