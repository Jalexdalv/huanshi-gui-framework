package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.widget.IconModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("all")
public class Icon extends JPanel implements Widget {
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private IconModel iconModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (!(model instanceof IconModel iconModel)) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        this.iconModel = iconModel;
        setBorder(null);
        setLayout(null);
        setBackground(iconModel.getBackground());
        key = iconModel.getKey();
        widgetSize = new WidgetSize(new Size(iconModel.getIcon().getIconWidth(), iconModel.getIcon().getIconHeight()), iconModel.getPadding(), iconModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), iconModel.getMargin());
        iconModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "background" -> setBackground((Color) e.getNewValue());
                case "icon" -> {
                    if (getWidgetWidth() != iconModel.getIcon().getIconWidth() || getWidgetHeight() != iconModel.getIcon().getIconHeight()) {
                        setWidgetSize(iconModel.getIcon().getIconWidth(), iconModel.getIcon().getIconHeight());
                    } else {
                        repaint();
                        firePropertyChange("repaint", e.getOldValue(), e.getNewValue());
                    }
                }
            }
        });
        widgetSize.addPropertyChangeListener(e -> {
            if ("size".equals(e.getPropertyName())) {
                renderWidget();
                firePropertyChange("size", e.getOldValue(), e.getNewValue());
            }
        });
        widgetPosition.addPropertyChangeListener(e -> {
            if ("position".equals(e.getPropertyName())) {
                renderWidget();
                firePropertyChange("position", e.getOldValue(), e.getNewValue());
            }
        });
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(((ImageIcon) iconModel.getIcon()).getImage(), 0, 0, iconModel.getIcon().getIconWidth(), iconModel.getIcon().getIconHeight(), this);
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}
}