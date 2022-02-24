package com.huanshi.gui.view.container.frame;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.type.FrameStatus;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.frame.AbstractCustomFrameModel;
import com.huanshi.gui.view.MouseLock;
import com.huanshi.gui.view.container.Container;
import com.huanshi.gui.view.widget.Widget;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

@SuppressWarnings("all")
public abstract class AbstractCustomFrame extends JFrame implements Container {
    @Getter
    private final LinkedList<Widget> widgetList = new LinkedList<>();
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private AbstractCustomFrameModel customFrameModel;
    private MouseAdapter moveMouseAdapter;
    private int moveX, moveY;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (!(model instanceof AbstractCustomFrameModel customFrameModel)) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        this.customFrameModel = customFrameModel;
        setLayout(null);
        setUndecorated(true);
        setBackground(customFrameModel.getBackground());
        setIconImage(customFrameModel.getLogo());
        setTitle(customFrameModel.getTitle());
        key = customFrameModel.getKey();
        widgetSize = new WidgetSize(customFrameModel.getStandardSize().clone(), customFrameModel.getPadding(), customFrameModel.getMargin());
        widgetPosition = new WidgetPosition(customFrameModel.getStandardPosition().clone(), customFrameModel.getMargin());
        customFrameModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "frame-status" -> {
                    if (customFrameModel.isMaxable()) {
                        switch ((FrameStatus) e.getNewValue()) {
                            case STANDARD -> {
                                setWidgetPosition(customFrameModel.getStandardPosition().getX(), customFrameModel.getStandardPosition().getY());
                                setWidgetSize(customFrameModel.getStandardSize().getWidth(), customFrameModel.getStandardSize().getHeight());
                            }
                            case MAX -> {
                                setWidgetPosition(customFrameModel.getMaxPosition().getX(), customFrameModel.getMaxPosition().getY());
                                setWidgetSize(customFrameModel.getMaxSize().getWidth(), customFrameModel.getMaxSize().getHeight());
                            }
                        }
                    }
                }
                case "opacity" -> setOpacity((float) e.getNewValue());
                case "visible" -> {
                    MouseLock.reset();
                    setWidgetPosition(customFrameModel.getStandardPosition().getX(), customFrameModel.getStandardPosition().getY());
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
        moveMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(@NotNull MouseEvent e) {
                moveX = e.getPoint().x;
                moveY = e.getPoint().y;
            }
            @Override
            public void mouseClicked(@NotNull MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (customFrameModel.isMaxable()) {
                        switch (customFrameModel.getFrameStatus()) {
                            case STANDARD -> customFrameModel.setFrameStatus(FrameStatus.MAX);
                            case MAX -> customFrameModel.setFrameStatus(FrameStatus.STANDARD);
                        }
                    }
                }
            }
            @Override
            public void mouseDragged(@NotNull MouseEvent e) {
                switch (customFrameModel.getFrameStatus()) {
                    case STANDARD -> setWidgetPosition(e.getXOnScreen() - moveX, e.getYOnScreen() - moveY);
                    case MAX -> {
                        customFrameModel.setFrameStatus(FrameStatus.STANDARD);
                        moveX = (int) (moveX * ((double) customFrameModel.getStandardSize().getWidth() / (double) customFrameModel.getMaxSize().getWidth()));
                        moveY = (int) (moveY * ((double) customFrameModel.getStandardSize().getHeight() / (double) customFrameModel.getMaxSize().getHeight()));
                    }
                }
            }
        };
    }

    public void addMoveListener(Widget widget) {
        ((Component) widget).addMouseListener(moveMouseAdapter);
        ((Component) widget).addMouseMotionListener(moveMouseAdapter);
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