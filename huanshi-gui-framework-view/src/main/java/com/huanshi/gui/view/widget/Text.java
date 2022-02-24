package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.widget.TextModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JLabel;

@SuppressWarnings("all")
public class Text extends JLabel implements Widget {
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private TextModel textModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != TextModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        textModel = (TextModel) model;
        setBorder(null);
        setText(textModel.getText());
        setFont(textModel.getFont());
        setForeground(textModel.getForeground());
        key = textModel.getKey();
        widgetSize = new WidgetSize(new Size(getPreferredWidth(), getPreferredHeight()), textModel.getPadding(), textModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), textModel.getMargin());
        textModel.addPropertyChangeListener(e -> {
            if ("text".equals(e.getPropertyName())) {
                setText((String) e.getNewValue());
                if (getWidgetWidth() != getPreferredWidth() || getWidgetHeight() != getPreferredHeight()) {
                    setWidgetSize(getPreferredWidth(), getPreferredHeight());
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