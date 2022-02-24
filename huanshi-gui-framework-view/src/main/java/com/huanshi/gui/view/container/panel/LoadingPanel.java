package com.huanshi.gui.view.container.panel;

import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.panel.LoadingPanelModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class LoadingPanel extends AbstractPanel {
    private Timer timer;
    private int startAngle, arcAngle;
    private boolean isReversal, isLink;
    private LoadingPanelModel loadingPanelModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != LoadingPanelModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        loadingPanelModel = (LoadingPanelModel) model;
        setOpaque(false);
        timer = new Timer(loadingPanelModel.getDelay(), e -> {
            if (startAngle < 360) {
                if (isReversal) {
                    startAngle = startAngle - 5;
                } else {
                    startAngle = startAngle + 5;
                }
            } else {
                startAngle = 0;
            }
            if (isLink) {
                if (arcAngle <= 5) {
                    isLink = false;
                    isReversal = false;
                } else if (isReversal) {
                    arcAngle -= 5;
                }
            } else {
                if (arcAngle >= 355) {
                    isLink = true;
                    isReversal = true;
                } else if (!isReversal) {
                    arcAngle += 5;
                }
            }
            repaint();
        });
    }

    @Override
    protected void paintComponent(@NotNull Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
        startAngle = 0;
        arcAngle = 0;
        isReversal = false;
        isLink = false;
    }

    private void draw(@NotNull Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY) {{
            put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }});
        graphics2D.setColor(loadingPanelModel.getStrokeBackground());
        graphics2D.setStroke(new BasicStroke(loadingPanelModel.getLoadingSize().thickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics2D.drawArc(GuiUtils.divide(getLayoutWidth(), 2) - GuiUtils.divide(loadingPanelModel.getLoadingSize().diameter(), 2), GuiUtils.divide(getLayoutHeight(), 2) - GuiUtils.divide(loadingPanelModel.getLoadingSize().diameter(), 2), loadingPanelModel.getLoadingSize().diameter(), loadingPanelModel.getLoadingSize().diameter(), startAngle, arcAngle);
        graphics2D.dispose();
    }
}