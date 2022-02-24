package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.widget.ButtonModel;
import com.huanshi.gui.view.MouseLock;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import java.awt.Color;

@SuppressWarnings("all")
public class Button extends JButton implements Widget {
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private ButtonModel buttonModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != ButtonModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        buttonModel = (ButtonModel) model;
        setBorder(null);
        setFocusPainted(false);
        setBackground(buttonModel.getBackground());
        setText(buttonModel.getText());
        setFont(buttonModel.getFont());
        setForeground(buttonModel.getForeground());
        setEnabled(buttonModel.isEnable());
        key = buttonModel.getKey();
        widgetSize = new WidgetSize(new Size(getPreferredWidth(), getPreferredHeight()), buttonModel.getPadding(), buttonModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), buttonModel.getMargin());
        buttonModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "background" -> setBackground((Color) e.getNewValue());
                case "enable" -> {
                    if (MouseLock.getEnteredWidget() == this || MouseLock.getLockedWidget() == this) {
                        MouseLock.reset();
                    }
                    setEnabled((boolean) e.getNewValue());
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