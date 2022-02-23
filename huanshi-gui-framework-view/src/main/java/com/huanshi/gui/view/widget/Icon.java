package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.widget.IconModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class Icon extends JLabel implements Widget {
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private IconModel iconModel;

    @Override
    public ImageIcon getIcon() {
        return (ImageIcon) super.getIcon();
    }

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != IconModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        iconModel = (IconModel) model;
        setIcon(iconModel.getIcon());
        key = iconModel.getKey();
        widgetSize = new WidgetSize(new Size(getIcon().getIconWidth(), getIcon().getIconHeight()), iconModel.getPadding(), iconModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), iconModel.getMargin());
        iconModel.addPropertyChangeListener(e -> {
            if ("icon".equals(e.getPropertyName())) {
                setIcon((ImageIcon) e.getNewValue());
                if (getWidgetWidth() != getIcon().getIconWidth() || getWidgetHeight() != getIcon().getIconHeight()) {
                    setWidgetSize(getIcon().getIconWidth(), getIcon().getIconHeight());
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
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}
}