package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.panel.AbstractPanelModel;
import com.huanshi.gui.view.container.Container;
import com.huanshi.gui.view.widget.Widget;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.LinkedList;

@SuppressWarnings("all")
public abstract class AbstractPanel extends JPanel implements Container {
    @Getter
    private final LinkedList<Widget> widgetList = new LinkedList<>();
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private AbstractPanelModel panelModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (!(model instanceof AbstractPanelModel panelModel)) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        this.panelModel = panelModel;
        setBorder(null);
        setLayout(null);
        setBackground(panelModel.getBackground());
        key = panelModel.getKey();
        widgetSize = new WidgetSize(panelModel.getSize().clone(), panelModel.getPadding(), panelModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), panelModel.getMargin());
        panelModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "background" -> setBackground((Color) e.getNewValue());
                case "icon" -> {
                    if (getWidgetWidth() != panelModel.getBackgroundIcon().getIconWidth() || getWidgetHeight() != panelModel.getBackgroundIcon().getIconHeight()) {
                        setWidgetSize(panelModel.getBackgroundIcon().getIconWidth(), panelModel.getBackgroundIcon().getIconHeight());
                    } else {
                        repaint();
                    }
                }
            }
        });
        widgetSize.addPropertyChangeListener(e -> {
            if ("size".equals(e.getPropertyName())) {
                try {
                    superUpdateContainerSize();
                    superUpdateContainerPosition();
                } catch (Throwable throwable) {
                    GuiUtils.showErrorDialog(throwable);
                }
                renderContainer();
                firePropertyChange("size", e.getOldValue(), e.getNewValue());
            }
        });
        widgetPosition.addPropertyChangeListener(e -> {
            if ("position".equals(e.getPropertyName())) {
                renderWidget();
                firePropertyChange("position", e.getOldValue(), e.getNewValue());
            }
        });
        for (Widget widget : widgetList) {
            ((Component) widget).addPropertyChangeListener(e -> {
                if ("size".equals(e.getPropertyName())) {
                    try {
                        superUpdateWidgetSize();
                        superUpdateWidgetPosition();
                        superUpdateContainerSize();
                        superUpdateContainerPosition();
                    } catch (Throwable throwable) {
                        GuiUtils.showErrorDialog(throwable);
                    }
                    renderContainer();
                }
            });
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (panelModel.getBackgroundIcon() != null) {
            graphics.drawImage(((ImageIcon) panelModel.getBackgroundIcon()).getImage(), 0, 0, panelModel.getBackgroundIcon().getIconWidth(), panelModel.getBackgroundIcon().getIconHeight(), this);
        }
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}

    @Override
    public void updateContainerSize() {}

    @Override
    public void updateContainerPosition() {}
}