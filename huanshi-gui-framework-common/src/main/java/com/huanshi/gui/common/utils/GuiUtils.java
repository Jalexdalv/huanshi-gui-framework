package com.huanshi.gui.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class GuiUtils {
    public static int getMax(@NotNull int... values) {
        if (values.length == 0) {
            return 0;
        }
        int max = values[0];
        for (int i = 1; i < values.length; i++) {
            max = Math.max(values[i], max);
        }
        return max;
    }

    public static int divide(int dividend, int divider) {
        return (int) ((double) dividend / (double) divider);
    }

    public static int divide(double dividend, int divider) {
        return (int) (dividend / (double) divider);
    }

    public static int divide(int dividend, double divider) {
        return (int) ((double) dividend / divider);
    }

    public static void showErrorDialog(@NotNull Throwable throwable) {
        throwable.printStackTrace();
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        JOptionPane.showMessageDialog(new JFrame(), stringWriter.toString(), "错误", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
}