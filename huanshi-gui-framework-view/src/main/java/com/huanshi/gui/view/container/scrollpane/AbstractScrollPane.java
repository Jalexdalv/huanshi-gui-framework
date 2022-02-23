package com.huanshi.gui.view.container.scrollpane;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.type.ScrollDirection;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.scrollpane.AbstractScrollPaneModel;
import com.huanshi.gui.view.MouseLock;
import com.huanshi.gui.view.container.Container;
import com.huanshi.gui.view.widget.Widget;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class AbstractScrollPane extends JScrollPane implements Container {
    @Getter
    private final JScrollBar xScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
    @Getter
    private final JScrollBar yScrollBar = new JScrollBar(JScrollBar.VERTICAL);
    @Getter
    private final LinkedList<Widget> widgetList = new LinkedList<>();
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private AbstractScrollPaneModel scrollPaneModel;
    private int moveX, moveY;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (!(model instanceof AbstractScrollPaneModel scrollPaneModel)) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        this.scrollPaneModel = scrollPaneModel;
        setBorder(null);
        setHorizontalScrollBar(xScrollBar);
        setVerticalScrollBar(yScrollBar);
        setHorizontalScrollBarPolicy(scrollPaneModel.getScrollBarSize().x() == 0 ? ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER : ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(scrollPaneModel.getScrollBarSize().y() == 0 ? ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER : ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        xScrollBar.setPreferredSize(new Dimension((int) xScrollBar.getPreferredSize().getWidth(), scrollPaneModel.getScrollBarSize().x()));
        yScrollBar.setPreferredSize(new Dimension(scrollPaneModel.getScrollBarSize().y(), (int) yScrollBar.getPreferredSize().getHeight()));
        key = scrollPaneModel.getKey();
        widgetSize = new WidgetSize(scrollPaneModel.getSize().clone(), scrollPaneModel.getPadding(), scrollPaneModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), scrollPaneModel.getMargin());
        scrollPaneModel.addPropertyChangeListener(e -> {
            if ("reset".equals(e.getPropertyName())) {
                xScrollBar.setValue(0);
                yScrollBar.setValue(0);
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
                xScrollBar.setValue(0);
                yScrollBar.setValue(0);
                firePropertyChange("size", e.getOldValue(), e.getNewValue());
            }
        });
        widgetPosition.addPropertyChangeListener(e -> {
            if ("position".equals(e.getPropertyName())) {
                renderWidget();
                xScrollBar.setValue(0);
                yScrollBar.setValue(0);
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
        MouseAdapter xMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(@NotNull MouseEvent e) {
                MouseLock.lock((AbstractScrollPane) e.getComponent());
                moveX = e.getPoint().x;
            }
            @Override
            public void mouseReleased(@NotNull MouseEvent e) {
                MouseLock.unLock();
                moveX = 0;
            }
            @Override
            public void mouseEntered(@NotNull MouseEvent e) {
                MouseLock.enter((AbstractScrollPane) e.getComponent());
                moveX = e.getPoint().x;
            }
            @Override
            public void mouseExited(@NotNull MouseEvent e) {
                MouseLock.exit();
                moveX = 0;
            }
            @Override
            public void mouseDragged(@NotNull MouseEvent e) {
                if (MouseLock.getEnteredWidget() == e.getComponent()) {
                    xScrollBar.setValue(xScrollBar.getValue() + moveX - e.getX());
                    moveX = e.getX();
                }
            }
            @Override
            public void mouseWheelMoved(@NotNull MouseWheelEvent e) {
                if (scrollPaneModel.getScrollDirection() != ScrollDirection.ALL) {
                    if (e.getWheelRotation() == 1) {
                        xScrollBar.setValue(xScrollBar.getValue() + e.getScrollAmount() * 5);
                    } else if (e.getWheelRotation() == -1) {
                        xScrollBar.setValue(xScrollBar.getValue() - e.getScrollAmount() * 5);
                    }
                }
            }
        };
        MouseAdapter yMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(@NotNull MouseEvent e) {
                MouseLock.lock((AbstractScrollPane) e.getComponent());
                moveY = e.getPoint().y;
            }
            @Override
            public void mouseReleased(@NotNull MouseEvent e) {
                MouseLock.unLock();
                moveY = 0;
            }
            @Override
            public void mouseEntered(@NotNull MouseEvent e) {
                MouseLock.enter((AbstractScrollPane) e.getComponent());
                moveY = e.getPoint().y;
            }
            @Override
            public void mouseExited(@NotNull MouseEvent e) {
                MouseLock.exit();
                moveY = 0;
            }
            @Override
            public void mouseDragged(@NotNull MouseEvent e) {
                if (MouseLock.getEnteredWidget() == e.getComponent()) {
                    yScrollBar.setValue(yScrollBar.getValue() + moveY - e.getY());
                    moveY = e.getY();
                }
            }
            @Override
            public void mouseWheelMoved(@NotNull MouseWheelEvent e) {
                if (scrollPaneModel.getScrollDirection() != ScrollDirection.ALL) {
                    if (e.getWheelRotation() == 1) {
                        yScrollBar.setValue(yScrollBar.getValue() + e.getScrollAmount() * 5);
                    } else if (e.getWheelRotation() == -1) {
                        yScrollBar.setValue(yScrollBar.getValue() - e.getScrollAmount() * 5);
                    }
                }
            }
        };
        switch (scrollPaneModel.getScrollDirection()) {
            case X -> {
                addMouseListener(xMouseAdapter);
                addMouseMotionListener(xMouseAdapter);
                addMouseWheelListener(xMouseAdapter);
            }
            case Y -> {
                addMouseListener(yMouseAdapter);
                addMouseMotionListener(yMouseAdapter);
                addMouseWheelListener(yMouseAdapter);
            }
            case ALL -> {
                addMouseListener(xMouseAdapter);
                addMouseMotionListener(xMouseAdapter);
                addMouseWheelListener(xMouseAdapter);
                addMouseListener(yMouseAdapter);
                addMouseMotionListener(yMouseAdapter);
                addMouseWheelListener(yMouseAdapter);
            }
        }
    }

    public void initViewport(@NotNull Widget viewport) {
        Component component = (Component) viewport;
        component.setPreferredSize(new Dimension(viewport.getLayoutWidth(), viewport.getLayoutHeight()));
        setViewportView(component);
        setBackground(getViewport().getBackground());
        component.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "position", "size" -> component.setPreferredSize(new Dimension(viewport.getLayoutWidth(), viewport.getLayoutHeight()));
            }
        });
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