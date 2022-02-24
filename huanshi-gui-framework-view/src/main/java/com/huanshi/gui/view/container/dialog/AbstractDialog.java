package com.huanshi.gui.view.container.dialog;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.dialog.AbstractDialogModel;
import com.huanshi.gui.view.MouseLock;
import com.huanshi.gui.view.container.Container;
import com.huanshi.gui.view.container.panel.DialogTitleBar;
import com.huanshi.gui.view.widget.Widget;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JDialog;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

@SuppressWarnings("all")
public abstract class AbstractDialog extends JDialog implements Container {
    @ViewComponent(names = "title-bar")
    private DialogTitleBar dialogTitleBar;
    @Getter
    private final LinkedList<Widget> widgetList = new LinkedList<>();
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private AbstractDialogModel dialogModel;
    private int moveX, moveY;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (!(model instanceof AbstractDialogModel dialogModel)) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        this.dialogModel = dialogModel;
        setLayout(null);
        setUndecorated(true);
        setModal(true);
        setBackground(dialogModel.getBackground());
        key = dialogModel.getKey();
        widgetSize = new WidgetSize(dialogModel.getSize().clone(), dialogModel.getPadding(), dialogModel.getMargin());
        widgetPosition = new WidgetPosition(dialogModel.getPosition().clone(), dialogModel.getMargin());
        dialogModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "opacity" -> setOpacity((float) e.getNewValue());
                case "visible" -> {
                    MouseLock.reset();
                    setWidgetPosition(dialogModel.getPosition().getX(), dialogModel.getPosition().getY());
                    setVisible((boolean) e.getNewValue());
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
                    case "repaint" -> repaint();
                }
            });
        }
        MouseAdapter moveMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(@NotNull MouseEvent e) {
                moveX = e.getPoint().x;
                moveY = e.getPoint().y;
            }
            @Override
            public void mouseDragged(@NotNull MouseEvent e) {
                setWidgetPosition(e.getXOnScreen() - moveX, e.getYOnScreen() - moveY);
            }
        };
        dialogTitleBar.addMouseListener(moveMouseAdapter);
        dialogTitleBar.addMouseMotionListener(moveMouseAdapter);
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}

    @Override
    public void updateContainerSize() {
        dialogTitleBar.setWidgetWidth(getWidgetWidth());
    }

    @Override
    public void updateContainerPosition() {
        dialogTitleBar.setWidgetPosition(0, 0);
    }
}