package com.huanshi.gui.core;

import com.huanshi.gui.common.type.EnvironmentType;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.view.container.window.Splash;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@SuppressWarnings("all")
public class Starter {
    private static EnvironmentType environmentType;
    private static Splash splash;

    public static void showSplash() {
        if (splash != null) {
            splash.start();
        }
    }

    public static void hideSplash() {
        if (splash != null) {
            splash.destroy();
        }
    }

    public static void start(@NotNull Class<?> startClass) {
        try {
            File settingFile = new File(new File("").getCanonicalPath() + "\\setting.ini");
            try (FileReader fileReader = new FileReader(settingFile);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String environmentSetting = StringUtils.trimToNull(bufferedReader.readLine());
                String[] environmentSettings = environmentSetting.split("=");
                if ("[environment]".equals(StringUtils.trimToNull(environmentSettings[0]))) {
                    switch (StringUtils.trimToNull(environmentSettings[1])) {
                        case "dev" -> environmentType = EnvironmentType.DEV;
                        case "prod" -> environmentType = EnvironmentType.PROD;
                        default -> {
                            bufferedReader.close();
                            fileReader.close();
                            GuiUtils.showErrorDialog("启动配置参数错误", null);
                        }
                    }
                } else {
                    bufferedReader.close();
                    fileReader.close();
                    GuiUtils.showErrorDialog("启动配置参数错误", null);
                }
                String splashSetting = StringUtils.trimToNull(bufferedReader.readLine());
                String[] splashSettings = splashSetting.split("=");
                if ("[splash]".equals(StringUtils.trimToNull(splashSettings[0]))) {
                    try {
                        String splashPath = new File("").getCanonicalPath() + "\\" + StringUtils.trimToNull(splashSettings[1]);
                        Image image = ImageIO.read(new File(splashPath));
                        if (image != null) {
                            splash = new Splash(splashPath);
                        }
                        System.out.println(splashPath);
                    } catch (Throwable throwable) {
                        bufferedReader.close();
                        fileReader.close();
                        GuiUtils.showErrorDialog("启动配置参数错误", throwable);
                    }
                }
            } catch (Throwable throwable) {
                GuiUtils.showErrorDialog("启动配置参数错误", throwable);
            }
        } catch (Throwable throwable) {
            GuiUtils.showErrorDialog("启动配置参数错误", throwable);
        }
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.invokeLater(() -> {
                showSplash();
            });
        } catch (Throwable throwable) {
            GuiUtils.showErrorDialog("启动时出现错误", throwable);
        }
        try {
            Scanner.scan(startClass, environmentType);
        } catch (Throwable throwable) {
            SwingUtilities.invokeLater(() -> {
                hideSplash();
            });
            GuiUtils.showErrorDialog("扫描时出现错误", throwable);
        }
    }
}