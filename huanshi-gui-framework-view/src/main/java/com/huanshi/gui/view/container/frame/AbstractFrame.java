package com.huanshi.gui.view.container.frame;

import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.type.FrameStatus;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.frame.AbstractFrameModel;
import com.huanshi.gui.view.MouseLock;
import com.huanshi.gui.view.container.Container;
import com.huanshi.gui.view.container.panel.FrameTitleBar;
import com.huanshi.gui.view.widget.Widget;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

@SuppressWarnings("all")
public abstract class AbstractFrame extends JFrame implements Container {
    @ViewComponent(names = "title-bar")
    private FrameTitleBar frameTitleBar;
    @Getter
    private final LinkedList<Widget> widgetList = new LinkedList<>();
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private AbstractFrameModel frameModel;
    private int moveX, moveY;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (!(model instanceof AbstractFrameModel frameModel)) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        this.frameModel = frameModel;
        setLayout(null);
        setUndecorated(true);
        setBackground(frameModel.getBackground());
        setIconImage(frameModel.getLogo());
        setTitle(frameModel.getTitle());
        key = frameModel.getKey();
        widgetSize = new WidgetSize(frameModel.getStandardSize().clone(), frameModel.getPadding(), frameModel.getMargin());
        widgetPosition = new WidgetPosition(frameModel.getStandardPosition().clone(), frameModel.getMargin());
        frameModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "frame-status" -> {
                    switch ((FrameStatus) e.getNewValue()) {
                        case STANDARD -> {
                            setWidgetPosition(frameModel.getStandardPosition().getX(), frameModel.getStandardPosition().getY());
                            setWidgetSize(frameModel.getStandardSize().getWidth(), frameModel.getStandardSize().getHeight());
                        }
                        case MAX -> {
                            setWidgetPosition(frameModel.getMaxPosition().getX(), frameModel.getMaxPosition().getY());
                            setWidgetSize(frameModel.getMaxSize().getWidth(), frameModel.getMaxSize().getHeight());
                        }
                    }
                }
                case "opacity" -> setOpacity((float) e.getNewValue());
                case "visible" -> {
                    MouseLock.reset();
                    setWidgetPosition(frameModel.getStandardPosition().getX(), frameModel.getStandardPosition().getY());
                    setVisible((boolean) e.getNewValue());
                }
                case "iconified" -> setExtendedState(JFrame.ICONIFIED);
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
        MouseAdapter moveMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(@NotNull MouseEvent e) {
                moveX = e.getPoint().x;
                moveY = e.getPoint().y;
            }
            @Override
            public void mouseClicked(@NotNull MouseEvent e) {
                if (e.getClickCount() == 2) {
                    switch (frameModel.getFrameStatus()) {
                        case STANDARD -> frameModel.setFrameStatus(FrameStatus.MAX);
                        case MAX -> frameModel.setFrameStatus(FrameStatus.STANDARD);
                    }
                }
            }
            @Override
            public void mouseDragged(@NotNull MouseEvent e) {
                switch (frameModel.getFrameStatus()) {
                    case STANDARD -> setWidgetPosition(e.getXOnScreen() - moveX, e.getYOnScreen() - moveY);
                    case MAX -> {
                        frameModel.setFrameStatus(FrameStatus.STANDARD);
                        moveX = (int) (moveX * ((double) frameModel.getStandardSize().getWidth() / (double) frameModel.getMaxSize().getWidth()));
                        moveY = (int) (moveY * ((double) frameModel.getStandardSize().getHeight() / (double) frameModel.getMaxSize().getHeight()));
                    }
                }
            }
        };
        frameTitleBar.addMouseListener(moveMouseAdapter);
        frameTitleBar.addMouseMotionListener(moveMouseAdapter);
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}

    @Override
    public void updateContainerSize() {
        frameTitleBar.setWidgetWidth(getWidgetWidth());
    }

    @Override
    public void updateContainerPosition() {
        frameTitleBar.setWidgetPosition(0, 0);
    }
}