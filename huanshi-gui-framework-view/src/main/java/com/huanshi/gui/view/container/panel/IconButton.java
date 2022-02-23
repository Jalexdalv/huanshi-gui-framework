package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.panel.IconButtonModel;
import com.huanshi.gui.view.MouseLock;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class IconButton extends IconPanel {
    private IconButtonModel iconButtonModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != IconButtonModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        iconButtonModel = (IconButtonModel) model;
        setEnabled(iconButtonModel.isEnable());
        iconButtonModel.addPropertyChangeListener(e -> {
            if ("enable".equals(e.getPropertyName())) {
                if (MouseLock.getEnteredWidget() == this || MouseLock.getLockedWidget() == this) {
                    MouseLock.reset();
                }
                setEnabled((boolean) e.getNewValue());
            }
        });
    }
}