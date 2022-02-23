package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.type.FoldStatus;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.panel.AbstractFoldPanelModel;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class AbstractFoldPanel extends AbstractPanel {
    private AbstractFoldPanelModel foldPanelModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (!(model instanceof AbstractFoldPanelModel foldPanelModel)) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        this.foldPanelModel = foldPanelModel;
        foldPanelModel.addPropertyChangeListener(e -> {
            if ("fold-status".equals(e.getPropertyName())) {
                switch (((FoldStatus) e.getNewValue())) {
                    case SHRINK -> shrink();
                    case SPREAD -> spread();
                }
            }
        });
    }

    public abstract void shrink();
    public abstract void spread();
}