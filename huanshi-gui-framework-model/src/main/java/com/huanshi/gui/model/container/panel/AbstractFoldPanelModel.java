package com.huanshi.gui.model.container.panel;

import com.huanshi.gui.common.type.FoldStatus;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
@Getter
public abstract class AbstractFoldPanelModel extends AbstractPanelModel {
    private FoldStatus foldStatus = FoldStatus.SPREAD;

    public void setFoldStatus(@NotNull FoldStatus foldStatus) {
        if (this.foldStatus != foldStatus) {
            this.foldStatus = foldStatus;
            switch (foldStatus) {
                case SPREAD -> spread();
                case SHRINK -> shrink();
            }
            firePropertyChange("fold-status", foldStatus);
        }
    }

    public void reset() {
        spread();
    }

    public abstract void shrink();
    public abstract void spread();
}