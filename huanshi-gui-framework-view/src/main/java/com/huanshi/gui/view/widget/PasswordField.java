package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.common.utils.GuiUtils;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.widget.PasswordFieldModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PasswordField extends JPasswordField implements Widget {
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private PasswordFieldModel passwordFieldModel;
    private KeyAdapter keyAdapter;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != PasswordFieldModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        passwordFieldModel = (PasswordFieldModel) model;
        setBackground(passwordFieldModel.getBackground());
        setHorizontalAlignment(passwordFieldModel.getAlignment());
        setColumns(passwordFieldModel.getColumns());
        setFont(passwordFieldModel.getFont());
        setForeground(passwordFieldModel.getForeground());
        setEnabled(passwordFieldModel.isEnable());
        key = passwordFieldModel.getKey();
        widgetSize = new WidgetSize(new Size(getPreferredWidth(), getPreferredHeight()), passwordFieldModel.getPadding(), passwordFieldModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), passwordFieldModel.getMargin());
        passwordFieldModel.addPropertyChangeListener(e -> {
            switch (e.getPropertyName()) {
                case "limit" -> {
                    removeKeyListener(keyAdapter);
                    keyAdapter = getKeyAdapter();
                    addKeyListener(keyAdapter);
                }
                case "enable" -> setEnabled((boolean) e.getNewValue());
                case "text" -> setText((String) e.getNewValue());
            }
        });
        widgetSize.addPropertyChangeListener(e -> {
            if ("size".equals(e.getPropertyName())) {
                renderWidget();
                firePropertyChange("size", e.getOldValue(), e.getNewValue());
            }
        });
        widgetPosition.addPropertyChangeListener(e -> {
            if ("position".equals(e.getPropertyName())) {
                renderWidget();
                firePropertyChange("position", e.getOldValue(), e.getNewValue());
            }
        });
        keyAdapter = getKeyAdapter();
        addKeyListener(keyAdapter);
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    passwordFieldModel.setText(e.getDocument().getText(0, e.getDocument().getLength()), false);
                } catch (Throwable throwable) {
                    GuiUtils.showErrorDialog(throwable);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    passwordFieldModel.setText(e.getDocument().getText(0, e.getDocument().getLength()), false);
                } catch (Throwable throwable) {
                    GuiUtils.showErrorDialog(throwable);
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    passwordFieldModel.setText(e.getDocument().getText(0, e.getDocument().getLength()), false);
                } catch (Throwable throwable) {
                    GuiUtils.showErrorDialog(throwable);
                }
            }
        });
    }

    @NotNull
    private KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = String.valueOf(getPassword());
                if (e.getModifiersEx() == 0) {
                    if (getSelectionStart() < getSelectionEnd()) {
                        text = text.substring(0, getSelectionStart()) + e.getKeyChar() + text.substring(getSelectionEnd());
                    } else if (getSelectionStart() > getSelectionEnd()) {
                        text = text.substring(0, getSelectionEnd()) + e.getKeyChar() + text.substring(getSelectionStart());
                    } else {
                        text = text.substring(0, getCaretPosition()) + e.getKeyChar() + text.substring(getCaretPosition());
                    }
                    if (text.length() > passwordFieldModel.getLimit()) {
                        e.consume();
                    }
                } else if (e.getModifiersEx() == InputEvent.CTRL_DOWN_MASK && e.getKeyChar() == 22) {
                    Transferable trans = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                    if (trans != null && trans.isDataFlavorSupported(DataFlavor.stringFlavor) && text.length() > passwordFieldModel.getLimit()) {
                        try {
                            String paste = (String) trans.getTransferData(DataFlavor.stringFlavor);
                            int position = getCaretPosition() - paste.length();
                            String original = text.substring(0, position) + text.substring(getCaretPosition());
                            setText(original.substring(0, position) + paste.substring(0, passwordFieldModel.getLimit() - original.length()) + original.substring(position));
                        } catch (Throwable throwable) {
                            GuiUtils.showErrorDialog(throwable);
                        }
                    }
                }
            }
        };
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}
}