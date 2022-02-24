package com.huanshi.gui.controller.listener;

import com.huanshi.gui.common.annotation.AutowiredModelComponent;
import com.huanshi.gui.common.type.ButtonStatus;
import com.huanshi.gui.model.container.panel.IconButtonModel;
import com.huanshi.gui.view.MouseLock;
import com.huanshi.gui.view.container.panel.IconButton;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("all")
public abstract class AbstractIconButtonListener extends MouseAdapter implements Listener {
    @AutowiredModelComponent
    private IconButtonModel iconButtonModel;

    @Override
    public void init() {}

    @Override
    public void mousePressed(@NotNull MouseEvent e) {
        MouseLock.lock((IconButton) e.getComponent());
        iconButtonModel.setButtonStatus(ButtonStatus.PRESS);
    }

    @Override
    public void mouseReleased(@NotNull MouseEvent e) {
        if (MouseLock.getEnteredWidget() == e.getComponent()) {
            click();
        }
        iconButtonModel.setButtonStatus(ButtonStatus.DEFAULT);
        MouseLock.unLock();
    }

    @Override
    public void mouseEntered(@NotNull MouseEvent e) {
        MouseLock.enter((IconButton) e.getComponent(), e);
        if (MouseLock.isLock()) {
            if (MouseLock.getLockedWidget() == e.getComponent()) {
                iconButtonModel.setButtonStatus(ButtonStatus.PRESS);
            }
        } else {
            iconButtonModel.setButtonStatus(ButtonStatus.TOUCH);
        }
    }

    @Override
    public void mouseExited(@NotNull MouseEvent e) {
        MouseLock.exit();
        if (MouseLock.isLock()) {
            if (MouseLock.getLockedWidget() == e.getComponent()) {
                iconButtonModel.setButtonStatus(ButtonStatus.TOUCH);
            }
        } else {
            iconButtonModel.setButtonStatus(ButtonStatus.DEFAULT);
        }
    }

    public abstract void click();
}