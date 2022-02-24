package com.huanshi.gui.view.container.window;

import lombok.Getter;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

public class SplashPanel extends JPanel {
    private static final GraphicsEnvironment GRAPHICS_ENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
    @Getter
    private static final int SCREEN_WIDTH = (int) GRAPHICS_ENVIRONMENT.getMaximumWindowBounds().getWidth();
    @Getter
    private static final int SCREEN_HEIGHT = (int) GRAPHICS_ENVIRONMENT.getMaximumWindowBounds().getHeight();
    @Getter
    private static final double WIDTH_SCALE = GRAPHICS_ENVIRONMENT.getDefaultScreenDevice().getDefaultConfiguration().getBounds().getWidth() / (double) 1920;
    @Getter
    private static final double HEIGHT_SCALE = GRAPHICS_ENVIRONMENT.getDefaultScreenDevice().getDefaultConfiguration().getBounds().getHeight() / (double) 1080;
    private final ImageIcon imageIcon;

    public SplashPanel(ImageIcon imageIcon) {
        setLayout(null);
        setBackground(new Color(255, 255, 255, 0));
        this.imageIcon = imageIcon;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(imageIcon.getImage(), 0, 0, (int) (imageIcon.getIconWidth() * getWIDTH_SCALE()), (int) (imageIcon.getIconHeight() * getHEIGHT_SCALE()), this);
    }
}