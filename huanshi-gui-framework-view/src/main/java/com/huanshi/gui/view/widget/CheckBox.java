package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.widget.CheckBoxModel;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class CheckBox extends JCheckBox implements Widget {
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private CheckBoxModel checkBoxModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != CheckBoxModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        checkBoxModel = (CheckBoxModel) model;
        setBorder(null);
        setFocusPainted(false);
        setSelected(false);
        setBackground(checkBoxModel.getBackground());
        setText(checkBoxModel.getText());
        setFont(checkBoxModel.getFont());
        setForeground(checkBoxModel.getForeground());
        setEnabled(checkBoxModel.isEnable());
        key = checkBoxModel.getKey();
        widgetSize = new WidgetSize(new Size(getPreferredWidth(), getPreferredHeight()), checkBoxModel.getPadding(), checkBoxModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), checkBoxModel.getMargin());
        checkBoxModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "enable" -> setEnabled((boolean) e.getNewValue());
                case "selected" -> setSelected((boolean) e.getNewValue());
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
        addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                checkBoxModel.setSelected(true, false);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                checkBoxModel.setSelected(false, false);
            }
        });
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}
}