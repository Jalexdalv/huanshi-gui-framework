package com.huanshi.gui.controller.listener;

import com.huanshi.gui.common.annotation.AutowiredModelComponent;
import com.huanshi.gui.common.type.ButtonStatus;
import com.huanshi.gui.model.widget.ButtonModel;
import com.huanshi.gui.view.MouseLock;
import com.huanshi.gui.view.widget.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class AbstractButtonListener extends MouseAdapter implements Listener {
    @AutowiredModelComponent
    private ButtonModel buttonModel;

    @Override
    public void init() {}

    @Override
    public void mousePressed(@NotNull MouseEvent e) {
        MouseLock.lock((Button) e.getComponent());
        buttonModel.setButtonStatus(ButtonStatus.PRESS);
    }

    @Override
    public void mouseReleased(@NotNull MouseEvent e) {
        if (MouseLock.getEnteredWidget() == e.getComponent()) {
            click();
        }
        buttonModel.setButtonStatus(ButtonStatus.DEFAULT);
        MouseLock.unLock();
    }

    @Override
    public void mouseEntered(@NotNull MouseEvent e) {
        MouseLock.enter((Button) e.getComponent(), e);
        if (MouseLock.isLock()) {
            if (MouseLock.getLockedWidget() == e.getComponent()) {
                buttonModel.setButtonStatus(ButtonStatus.PRESS);
            }
        } else {
            buttonModel.setButtonStatus(ButtonStatus.TOUCH);
        }
    }

    @Override
    public void mouseExited(@NotNull MouseEvent e) {
        MouseLock.exit();
        if (MouseLock.isLock()) {
            if (MouseLock.getLockedWidget() == e.getComponent()) {
                buttonModel.setButtonStatus(ButtonStatus.TOUCH);
            }
        } else {
            buttonModel.setButtonStatus(ButtonStatus.DEFAULT);
        }
    }

    public abstract void click();
}