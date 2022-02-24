package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.widget.ComboBoxModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

@SuppressWarnings("all")
public class ComboBox extends JComboBox<String> implements Widget {
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private ComboBoxModel comboBoxModel;
    private ItemListener[] itemListeners;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != ComboBoxModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        comboBoxModel = (ComboBoxModel) model;
        setBorder(null);
        setBackground(comboBoxModel.getBackground());
        setModel(new DefaultComboBoxModel<>(comboBoxModel.getItems()));
        setFont(comboBoxModel.getFont());
        setForeground(comboBoxModel.getForeground());
        setEnabled(comboBoxModel.isEnable());
        key = comboBoxModel.getKey();
        widgetSize = new WidgetSize(new Size(getPreferredWidth(), getPreferredHeight()), comboBoxModel.getPadding(), comboBoxModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), comboBoxModel.getMargin());
        comboBoxModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "enable" -> setEnabled((boolean) e.getNewValue());
                case "selected-index" -> setSelectedIndex((int) e.getNewValue());
                case "selected-item" -> setSelectedItem(e.getNewValue());
                case "stop-item-listener" -> {
                    if ((boolean) e.getNewValue()) {
                        itemListeners = getItemListeners();
                        for (ItemListener itemListener : itemListeners) {
                            removeItemListener(itemListener);
                        }
                    } else {
                        for (ItemListener itemListener : itemListeners) {
                            addItemListener(itemListener);
                        }
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
        addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                comboBoxModel.setSelectedIndex(getSelectedIndex(), false, false);
                comboBoxModel.setSelectedItem((String) getSelectedItem(), false, false);
            }
        });
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}
}