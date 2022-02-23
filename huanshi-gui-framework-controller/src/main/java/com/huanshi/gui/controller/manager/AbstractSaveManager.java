package com.huanshi.gui.controller.manager;

import com.huanshi.gui.view.LayerSwitcher;
import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class AbstractSaveManager extends AbstractManager {
    public void save(@NotNull String title, @NotNull SaveHandler saveHandler, @NotNull ExceptionHandler exceptionHandler, @NotNull String... filters) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setDialogTitle(title);
            for (String filter : filters) {
                fileChooser.setFileFilter(new FileNameExtensionFilter("*." + filter, filter));
            }
            if (fileChooser.showSaveDialog((Component) LayerSwitcher.getShowView()) == JFileChooser.APPROVE_OPTION) {
                saveHandler.handle(fileChooser);
            }
        } catch (Throwable throwable) {
            exceptionHandler.handle(throwable);
        }
    }
}