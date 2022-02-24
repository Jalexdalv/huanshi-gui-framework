package com.huanshi.gui.view.widget;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.Position;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.WidgetPosition;
import com.huanshi.gui.common.data.WidgetSize;
import com.huanshi.gui.common.data.table.TableData;
import com.huanshi.gui.common.exception.ModelNotMatchedException;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.widget.TableModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

public class Table extends JTable implements Widget {
    @Getter
    private Key key;
    @Getter
    private WidgetSize widgetSize;
    @Getter
    private WidgetPosition widgetPosition;
    private TableModel tableModel;

    @Override
    public void init(@NotNull AbstractModel model) {
        if (model.getClass() != TableModel.class) {
            throw new ModelNotMatchedException(model.getKey(), getClass());
        }
        tableModel = (TableModel) model;
        setLayout(null);
        setBorder(null);
        setFocusable(false);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setBackground(tableModel.getBackground());
        setSelectionBackground(tableModel.getSelectedBackground());
        setFont(tableModel.getFont());
        ((DefaultTableModel) getModel()).setColumnIdentifiers(tableModel.getTableHeader().header());
        setRowHeight(tableModel.getTableSize().rowHeight() + tableModel.getTablePadding().row());
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            @NotNull
            public Component getTableCellRendererComponent(@NotNull JTable table, @NotNull Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setBackground(row % 2 == 0 ? tableModel.getBackground() : tableModel.getSeparateBackground());
                setForeground(tableModel.getForeground());
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            @NotNull
            public Component getTableCellRendererComponent(@NotNull JTable table, @NotNull Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setBackground(tableModel.getHeaderBackground());
                setForeground(tableModel.getHeaderForeground());
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
        key = tableModel.getKey();
        widgetSize = new WidgetSize(new Size(getTableWidth(), getModel().getRowCount() * getRowHeight()), tableModel.getPadding(), tableModel.getMargin());
        widgetPosition = new WidgetPosition(new Position(0, 0), tableModel.getMargin());
        setPreferredSize(getLayoutWidth(), getLayoutHeight());
        tableModel.addPropertyChangeListener(e -> {
            if ("table-data-list".equals(e.getPropertyName())) {
                ((DefaultTableModel) getModel()).setRowCount(0);
                for (TableData tableData : tableModel.getTableDataList()) {
                    ((DefaultTableModel) getModel()).addRow(tableData.convertToArray());
                }
                setWidgetSize(getTableWidth(), getModel().getRowCount() * getRowHeight());
            }
        });
        widgetSize.addPropertyChangeListener(e -> {
            if ("size".equals(e.getPropertyName())) {
                setPreferredSize(getLayoutWidth(), getLayoutHeight());
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
        getSelectionModel().addListSelectionListener(e -> tableModel.setSelectedRows(getSelectedRows()));
    }

    private int getTableWidth() {
        Enumeration<TableColumn> tableColumnEnumeration = getColumnModel().getColumns();
        int tableWidth = 0;
        while (tableColumnEnumeration.hasMoreElements()) {
            TableColumn tableColumn = tableColumnEnumeration.nextElement();
            int tableColumnWidth = (int) (tableModel.getTablePadding().column() + tableModel.getFont().getStringBounds(getModel().getColumnName(tableColumn.getModelIndex()), new FontRenderContext(new AffineTransform(), true, true)).getWidth());
            for (int i = 0; i < getModel().getRowCount(); i++) {
                tableColumnWidth = (int) Math.max(tableModel.getTablePadding().column() + tableModel.getFont().getStringBounds(String.valueOf(getModel().getValueAt(i, tableColumn.getModelIndex())), new FontRenderContext(new AffineTransform(), true, true)).getWidth(), tableColumnWidth);
            }
            tableColumn.setWidth(Math.max(tableColumnWidth, tableModel.getTableHeader().minColumnWidth()[tableColumn.getModelIndex()]));
            getTableHeader().setResizingColumn(tableColumn);
            tableWidth += tableColumn.getWidth();
        }
        return tableWidth;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void updateWidgetSize() {}

    @Override
    public void updateWidgetPosition() {}
}