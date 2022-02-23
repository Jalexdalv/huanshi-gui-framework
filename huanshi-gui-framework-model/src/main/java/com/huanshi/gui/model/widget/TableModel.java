package com.huanshi.gui.model.widget;

import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.table.TableData;
import com.huanshi.gui.common.data.table.TableHeader;
import com.huanshi.gui.common.data.table.TablePadding;
import com.huanshi.gui.common.data.table.TableSize;
import com.huanshi.gui.common.exception.TableRowOutOfBoundsException;
import com.huanshi.gui.model.AbstractModel;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings(value = "all")
@Getter
public class TableModel extends AbstractModel {
    private Color background;
    private Color separateBackground;
    private Color selectedBackground;
    private Color headerBackground;
    private Font font;
    private Color foreground;
    private Color headerForeground;
    private TableSize tableSize;
    private TablePadding tablePadding;
    private TableHeader tableHeader;
    private int[] selectedRows;
    private List<? extends TableData> tableDataList = new LinkedList<>();

    @Override
    public void init(@NotNull Parser parser, @NotNull Key key) {
        key.addLast("background"); background = parser.parseColor(key); key.removeLast();
        key.addLast("separate-background"); separateBackground = parser.parseColor(key); key.removeLast();
        key.addLast("selected-background"); selectedBackground = parser.parseColor(key); key.removeLast();
        key.addLast("header-background"); headerBackground = parser.parseColor(key); key.removeLast();
        key.addLast("font"); font = parser.parseFont(key); key.removeLast();
        key.addLast("foreground"); foreground = parser.parseColor(key); key.removeLast();
        key.addLast("header-foreground"); headerForeground = parser.parseColor(key); key.removeLast();
        key.addLast("table-size"); tableSize = parser.parseTableSize(key); key.removeLast();
        key.addLast("table-padding"); tablePadding = parser.parseTablePadding(key); key.removeLast();
        key.addLast("table-header"); tableHeader = parser.parseTableHeader(key); key.removeLast();
    }

    public void setSelectedRows(@NotNull int[] selectedRows) {
        if (!Arrays.equals(this.selectedRows, selectedRows)) {
            for (int selectedRow : selectedRows) {
                if (selectedRow < 0 || selectedRow >= tableDataList.size()) {
                    throw new TableRowOutOfBoundsException(getKey(), selectedRow);
                }
            }
            this.selectedRows = selectedRows;
        }
    }

    public void setTableDataList(@NotNull List<? extends TableData> tableDataList) {
        if (!this.tableDataList.equals(tableDataList)) {
            this.tableDataList = tableDataList;
            firePropertyChange("table-data-list", tableDataList);
        }
    }
}