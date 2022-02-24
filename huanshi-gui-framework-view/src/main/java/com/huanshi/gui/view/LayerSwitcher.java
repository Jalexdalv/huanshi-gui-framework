package com.huanshi.gui.view;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.dialog.AbstractDialogModel;
import com.huanshi.gui.model.container.frame.AbstractCustomFrameModel;
import com.huanshi.gui.model.container.frame.AbstractFrameModel;
import com.huanshi.gui.model.container.loading.AbstractLoadingModel;
import com.huanshi.gui.model.container.window.AbstractWindowModel;
import com.huanshi.gui.view.container.Container;
import com.huanshi.gui.view.widget.Widget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Stack;

@SuppressWarnings("all")
public class LayerSwitcher {
    private static final HashMap<Key, Widget> LOADED_VIEW_MAP = new HashMap<>();
    private static final Stack<AbstractModel> LAYER_STACK = new Stack<>();

    @Nullable
    public static Container getShowView() {
        return LAYER_STACK.empty() ? null : (Container) LOADED_VIEW_MAP.get(LAYER_STACK.peek().getKey());
    }

    @Nullable
    public static Container getBeforeShowView() {
        return LAYER_STACK.size() < 2 ? null : (Container) LOADED_VIEW_MAP.get(LAYER_STACK.get(LAYER_STACK.size() - 2).getKey());
    }

    public static void show(@NotNull AbstractFrameModel frameModel) {
        while (!LAYER_STACK.empty()) {
            hide();
        }
        LAYER_STACK.push(frameModel);
        frameModel.setVisible(true);
    }

    public static void show(@NotNull AbstractCustomFrameModel customFrameModel) {
        while (!LAYER_STACK.empty()) {
            hide();
        }
        LAYER_STACK.push(customFrameModel);
        customFrameModel.setVisible(true);
    }

    public static void show(@NotNull AbstractDialogModel dialogModel) {
        if (!LAYER_STACK.empty()) {
            if (LAYER_STACK.peek() instanceof AbstractFrameModel frameModel) {
                frameModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractCustomFrameModel customFrameModel) {
                customFrameModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractDialogModel castDialogModel) {
                castDialogModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractWindowModel windowModel) {
                windowModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractLoadingModel loadingModel) {
                hide();
                show(dialogModel);
                return;
            }
        }
        LAYER_STACK.push(dialogModel);
        dialogModel.setVisible(true);
    }

    public static void show(@NotNull AbstractWindowModel windowModel) {
        if (!LAYER_STACK.empty()) {
            if (LAYER_STACK.peek() instanceof AbstractFrameModel frameModel) {
                frameModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractCustomFrameModel customFrameModel) {
                customFrameModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractDialogModel dialogModel) {
                dialogModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractWindowModel castWindowModel) {
                castWindowModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractLoadingModel loadingModel) {
                hide();
                show(windowModel);
                return;
            }
        }
        LAYER_STACK.push(windowModel);
        windowModel.setVisible(true);
    }

    public static void show(@NotNull AbstractLoadingModel loadingModel) {
        if (!LAYER_STACK.empty()) {
            if (LAYER_STACK.peek() instanceof AbstractFrameModel frameModel) {
                frameModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractCustomFrameModel customFrameModel) {
                customFrameModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractDialogModel dialogModel) {
                dialogModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractWindowModel windowModel) {
                windowModel.setOpacity(0.7F);
            } else if (LAYER_STACK.peek() instanceof AbstractLoadingModel castLoadingModel) {
                hide();
                show(castLoadingModel);
                return;
            }
        }
        LAYER_STACK.push(loadingModel);
        loadingModel.start();
        loadingModel.setVisible(true);
    }

    public static void hide() {
        if (!LAYER_STACK.empty()) {
            AbstractModel popModel = LAYER_STACK.pop();
            if (popModel instanceof AbstractFrameModel frameModel) {
                frameModel.setOpacity(1.0F);
                frameModel.setVisible(false);
            } else if (LAYER_STACK.peek() instanceof AbstractCustomFrameModel customFrameModel) {
                customFrameModel.setOpacity(1.0F);
                customFrameModel.setVisible(false);
            } else if (popModel instanceof AbstractDialogModel dialogModel) {
                dialogModel.setOpacity(1.0F);
                dialogModel.setVisible(false);
            } else if (popModel instanceof AbstractWindowModel windowModel) {
                windowModel.setOpacity(1.0F);
                windowModel.setVisible(false);
            } else if (popModel instanceof AbstractLoadingModel loadingModel) {
                loadingModel.stop();
                loadingModel.setVisible(false);
            }
            if (!LAYER_STACK.empty()) {
                if (LAYER_STACK.peek() instanceof AbstractFrameModel frameModel) {
                    frameModel.setOpacity(1.0F);
                    frameModel.setVisible(true);
                } else if (popModel instanceof AbstractDialogModel dialogModel) {
                    dialogModel.setOpacity(1.0F);
                    dialogModel.setVisible(true);
                } else if (LAYER_STACK.peek() instanceof AbstractDialogModel dialogModel) {
                    dialogModel.setOpacity(1.0F);
                    dialogModel.setVisible(true);
                } else if (LAYER_STACK.peek() instanceof AbstractWindowModel windowModel) {
                    windowModel.setOpacity(1.0F);
                    windowModel.setVisible(true);
                }
            }
        }
    }
}