package com.huanshi.gui.view.container.loading;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.loading.AbstractLoadingModel;
import com.huanshi.gui.view.LayerSwitcher;
import com.huanshi.gui.view.container.Container;
import com.huanshi.gui.view.container.panel.LoadingPanel;
import com.huanshi.gui.view.widget.Widget;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JDialog;
import java.awt.Component;
import java.util.LinkedList;

@SuppressWarnings("all")
public abstract class AbstractLoading extends JDialog implements Container {
    @ViewComponent(names = "loading-panel")
    private LoadingPanel loadingPanel;
    @Getter
    private final LinkedList<Widget> widgetList = new LinkedList<>();
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private AbstractLoadingModel loadingModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (!(model instanceof AbstractLoadingModel loadingModel)) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        this.loadingModel = loadingModel;
        setLayout(null);
        setUndecorated(true);
        setModal(true);
        setBackground(loadingModel.getBackground());
        key = loadingModel.getKey();
        widgetSize = new WidgetSize(loadingModel.getSize().clone(), loadingModel.getPadding(), loadingModel.getMargin());
        widgetPosition = new WidgetPosition(loadingModel.getPosition().clone(), loadingModel.getMargin());
        loadingModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "visible" -> {
                    if ((boolean) e.getNewValue()) {
                        if (LayerSwitcher.getBeforeShowView() != null) {
                            setWidgetPosition(LayerSwitcher.getBeforeShowView().getLayoutX() + GuiUtils.divide(LayerSwitcher.getBeforeShowView().getLayoutWidth() - getLayoutWidth(), 2), LayerSwitcher.getBeforeShowView().getLayoutY() + GuiUtils.divide(LayerSwitcher.getBeforeShowView().getLayoutHeight() - getLayoutHeight(), 2));
                        } else {
                            setWidgetPosition(loadingModel.getPosition().getX(), loadingModel.getPosition().getY());
                        }
                    }
                    setVisible((boolean) e.getNewValue());
                }
                case "start" -> loadingPanel.start();
                case "stop" -> loadingPanel.stop();
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
                switch (e.getPropertyName()) {
                    case "size" -> {
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
                    case "repaint" -> {
                        repaint();
                        firePropertyChange("repaint", e.getOldValue(), e.getNewValue());
                    }
                }
            });
        }
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}

    @Override
    public void updateContainerSize() {
        loadingPanel.setWidgetSize(getWidgetWidth(), getWidgetHeight());
    }

    @Override
    public void updateContainerPosition() {
        loadingPanel.setWidgetPosition(0, 0);
    }
}