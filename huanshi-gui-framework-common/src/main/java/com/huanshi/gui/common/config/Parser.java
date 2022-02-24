package com.huanshi.gui.common.config;

import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.data.LoadingSize;
import com.huanshi.gui.common.data.Margin;
import com.huanshi.gui.common.data.Padding;
import com.huanshi.gui.common.data.ScrollBarSize;
import com.huanshi.gui.common.data.Size;
import com.huanshi.gui.common.data.table.TableHeader;
import com.huanshi.gui.common.data.table.TablePadding;
import com.huanshi.gui.common.data.table.TableSize;
import com.huanshi.gui.common.exception.IllegalConfigException;
import com.huanshi.gui.common.type.ButtonType;
import com.huanshi.gui.common.type.ScrollDirection;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class Parser {
    private static final GraphicsEnvironment GRAPHICS_ENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static final double WIDTH_SCALE = GRAPHICS_ENVIRONMENT.getDefaultScreenDevice().getDefaultConfiguration().getBounds().getWidth() / (double) 1920;
    private static final double HEIGHT_SCALE = GRAPHICS_ENVIRONMENT.getDefaultScreenDevice().getDefaultConfiguration().getBounds().getHeight() / (double) 1080;
    private final HashMap<?, ?> yaml;

    @SneakyThrows
    public Parser(@NotNull String path) {
        yaml = new Yaml().loadAs(new BufferedReader(new FileReader(path, StandardCharsets.UTF_8)), HashMap.class);
    }

    @Nullable
    private Object getProperty(@NotNull Key key) {
        if (yaml == null || yaml.isEmpty()) {
            return null;
        }
        Map<?, ?> map = yaml;
        for (String name : key) {
            Object value = map.get(name);
            if (!(value instanceof Map<?, ?> castMap)) {
                return value;
            }
            map = castMap;
        }
        return map;
    }

    @Nullable
    public String parseString(boolean nullable, boolean emptiable, @NotNull Key key) {
        Object value = getProperty(key);
        if (value == null && nullable) {
            return null;
        }
        if (!(value instanceof String string)) {
            throw new IllegalConfigException(key);
        }
        string = StringUtils.trim((String) value);
        if (string.isEmpty() && !emptiable) {
            throw new IllegalConfigException(key);
        }
        return string;
    }

    @Nullable
    public Boolean parseBoolean(boolean nullable, @NotNull Key key) {
        Object value = getProperty(key);
        if (value == null && nullable) {
            return null;
        }
        if (!(value instanceof Boolean castBoolean)) {
            throw new IllegalConfigException(key);
        }
        return castBoolean;
    }

    @Nullable
    public Integer parseInteger(boolean nullable, @NotNull Key key) {
        Object value = getProperty(key);
        if (value == null && nullable) {
            return null;
        }
        if (!(value instanceof Integer integer)) {
            throw new IllegalConfigException(key);
        }
        return integer;
    }

    @Nullable
    public Double parseDouble(boolean nullable, @NotNull Key key) {
        Object value = getProperty(key);
        if (value == null && nullable) {
            return null;
        }
        if (!(value instanceof Double castDouble)) {
            throw new IllegalConfigException(key);
        }
        return castDouble;
    }

    @Nullable
    public String[] parseStringArray(boolean nullable, boolean emptiable, boolean duplicatable, @NotNull Key key) {
        Object value = getProperty(key);
        if (value == null && nullable) {
            return null;
        }
        if (!(value instanceof List<?> list)) {
            throw new IllegalConfigException(key);
        }
        String[] strings = new String[list.size()];
        for (int i = 0; i < strings.length; i++) {
            if (!(list.get(i) instanceof String string)) {
                throw new IllegalConfigException(key);
            }
            string = StringUtils.trim((String) list.get(i));
            if (string.isEmpty() && !emptiable) {
                throw new IllegalConfigException(key);
            }
            strings[i] = string;
        }
        if (new HashSet<>(Arrays.asList(strings)).size() != strings.length && !duplicatable) {
            throw new IllegalConfigException(key);
        }
        return strings;
    }

    @Nullable
    public boolean[] parseBooleanArray(boolean nullable, boolean duplicatable, @NotNull Key key) {
        Object value = getProperty(key);
        if (value == null && nullable) {
            return null;
        }
        if (!(value instanceof List<?> list)) {
            throw new IllegalConfigException(key);
        }
        boolean[] booleans = new boolean[list.size()];
        for (int i = 0; i < booleans.length; i++) {
            if (!(list.get(i) instanceof Boolean castBoolean)) {
                throw new IllegalConfigException(key);
            }
            booleans[i] = castBoolean;
        }
        if (new HashSet<>(Arrays.asList(booleans)).size() != booleans.length && !duplicatable) {
            throw new IllegalConfigException(key);
        }
        return booleans;
    }

    @Nullable
    public int[] parseIntegerArray(boolean nullable, boolean duplicatable, @NotNull Key key) {
        Object value = getProperty(key);
        if (value == null && nullable) {
            return null;
        }
        if (!(value instanceof List<?> list)) {
            throw new IllegalConfigException(key);
        }
        int[] ints = new int[list.size()];
        for (int i = 0; i < ints.length; i++) {
            if (!(list.get(i) instanceof Integer integer)) {
                throw new IllegalConfigException(key);
            }
            ints[i] = integer;
        }
        if (new HashSet<>(Arrays.asList(ints)).size() != ints.length && !duplicatable) {
            throw new IllegalConfigException(key);
        }
        return ints;
    }

    @Nullable
    public double[] parseDoubleArray(boolean nullable, boolean duplicatable, @NotNull Key key) {
        Object value = getProperty(key);
        if (value == null && nullable) {
            return null;
        }
        if (!(value instanceof List<?> list)) {
            throw new IllegalConfigException(key);
        }
        double[] doubles = new double[list.size()];
        for (int i = 0; i < doubles.length; i++) {
            if (!(list.get(i) instanceof Double castDouble)) {
                throw new IllegalConfigException(key);
            }
            doubles[i] = castDouble;
        }
        if (new HashSet<>(Arrays.asList(doubles)).size() != doubles.length && !duplicatable) {
            throw new IllegalConfigException(key);
        }
        return doubles;
    }

    @NotNull
    public Image parseImage(@NotNull Key key) {
        if (!(getProperty(key) instanceof Map<?, ?> map) || !(map.get("path") instanceof String path)) {
            throw new IllegalConfigException(key);
        }
        return new ImageIcon(StringUtils.trim(path)).getImage();
    }

    @NotNull
    public Icon parseIcon(@NotNull Key key) {
        if (!(getProperty(key) instanceof Map<?, ?> map) || !(map.get("path") instanceof String path) || !(map.get("width") instanceof Integer width) || !(map.get("height") instanceof Integer height)) {
            throw new IllegalConfigException(key);
        }
        ImageIcon imageIcon = new ImageIcon(StringUtils.trim(path));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance((int) (width * WIDTH_SCALE), (int) (height * HEIGHT_SCALE), Image.SCALE_SMOOTH));
        return imageIcon;
    }

    @NotNull
    public Color parseColor(@NotNull Key key) {
        if (!(getProperty(key) instanceof Map<?, ?> map) || !(map.get("r") instanceof Integer r) || !(map.get("g") instanceof Integer g) || !(map.get("b") instanceof Integer b) || !(map.get("a") instanceof Integer a)) {
            throw new IllegalConfigException(key);
        }
        return new Color(r, g, b, a);
    }

    @NotNull
    public Font parseFont(@NotNull Key key) {
        if (!(getProperty(key) instanceof Map<?, ?> map) || !(map.get("type") instanceof String type) || !(map.get("bold") instanceof Boolean bold) || !(map.get("size") instanceof Integer size)) {
            throw new IllegalConfigException(key);
        }
        return new Font(StringUtils.trim(type), bold ? Font.BOLD : Font.PLAIN, (int) (size * HEIGHT_SCALE));
    }

    @NotNull
    public Size parseSize(@NotNull Key key) {
        Object value = getProperty(key);
        if (value == null) {
            return new Size(0, 0);
        }
        if (!(value instanceof Map<?, ?> map)) {
            throw new IllegalConfigException(key);
        }
        int width = 0, height = 0;
        if (map.get("width") != null) {
            if (!(map.get("width") instanceof Integer castWidth)) {
                throw new IllegalConfigException(key);
            }
            width = (int) (castWidth * WIDTH_SCALE);
        }
        if (map.get("height") != null) {
            if (!(map.get("height") instanceof Integer castHeight)) {
                throw new IllegalConfigException(key);
            }
            height = (int) (castHeight * HEIGHT_SCALE);
        }
        return new Size(width, height);
    }

    @NotNull
    public Padding parsePadding(@NotNull Key key) {
        Object value = getProperty(key);
        if (value == null) {
            return new Padding(0, 0);
        }
        if (!(value instanceof Map<?, ?> map)) {
            throw new IllegalConfigException(key);
        }
        int x = 0, y = 0;
        if (map.get("x") != null) {
            if (!(map.get("x") instanceof Integer castX)) {
                throw new IllegalConfigException(key);
            }
            x = (int) (castX * WIDTH_SCALE);
        }
        if (map.get("y") != null) {
            if (!(map.get("y") instanceof Integer castY)) {
                throw new IllegalConfigException(key);
            }
            y = (int) (castY * HEIGHT_SCALE);
        }
        return new Padding(x, y);
    }

    @NotNull
    public Margin parseMargin(@NotNull Key key) {
        Object value = getProperty(key);
        if (value == null) {
            return new Margin(0, 0, 0, 0);
        }
        if (!(value instanceof Map<?, ?> map)) {
            throw new IllegalConfigException(key);
        }
        int left = 0, right = 0, top = 0, bottom = 0;
        if (map.get("left") != null) {
            if (!(map.get("left") instanceof Integer castLeft)) {
                throw new IllegalConfigException(key);
            }
            left = (int) (castLeft * WIDTH_SCALE);
        }
        if (map.get("right") != null) {
            if (!(map.get("right") instanceof Integer castRight)) {
                throw new IllegalConfigException(key);
            }
            right = (int) (castRight * WIDTH_SCALE);
        }
        if (map.get("top") != null) {
            if (!(map.get("top") instanceof Integer castTop)) {
                throw new IllegalConfigException(key);
            }
            top = (int) (castTop * HEIGHT_SCALE);
        }
        if (map.get("bottom") != null) {
            if (!(map.get("bottom") instanceof Integer castBottom)) {
                throw new IllegalConfigException(key);
            }
            bottom = (int) (castBottom * HEIGHT_SCALE);
        }
        return new Margin(left, right, top, bottom);
    }

    public int parseTextFieldAlignment(@NotNull Key key) {
        return switch (parseString(false, false, key)) {
            case "center" -> JTextField.CENTER;
            case "left" -> JTextField.LEFT;
            case "right" -> JTextField.RIGHT;
            default -> throw new IllegalConfigException(key);
        };
    }

    @NotNull
    public ButtonType parseButtonType(@NotNull Key key) {
        return switch (parseString(false, false, key)) {
            case "background" -> ButtonType.BACKGROUND;
            case "icon" -> ButtonType.ICON;
            default -> throw new IllegalConfigException(key);
        };
    }

    @NotNull
    public TableSize parseTableSize(@NotNull Key key) {
        if (!(getProperty(key) instanceof Map<?, ?> map) || !(map.get("row-height") instanceof Integer rowHeight)) {
            throw new IllegalConfigException(key);
        }
        return new TableSize((int) (rowHeight * HEIGHT_SCALE));
    }

    @NotNull
    public TablePadding parseTablePadding(@NotNull Key key) {
        Object value = getProperty(key);
        if (value == null) {
            return new TablePadding(0, 0);
        }
        if (!(value instanceof Map<?, ?> map) || !(map.get("row") instanceof Integer row) || !(map.get("column") instanceof Integer column)) {
            throw new IllegalConfigException(key);
        }
        return new TablePadding((int) (row * HEIGHT_SCALE), (int) (column * WIDTH_SCALE));
    }

    @NotNull
    public TableHeader parseTableHeader(@NotNull Key key) {
        if (!(getProperty(key) instanceof Map<?, ?> map) || !(map.get("header") instanceof List<?> headerList) || !(map.get("min-column-width") instanceof List<?> minColumnWidthList)) {
            throw new IllegalConfigException(key);
        }
        String[] headers = new String[headerList.size()];
        for (int i = 0; i < headers.length; i++) {
            if (!(headerList.get(i) instanceof String header)) {
                throw new IllegalConfigException(key);
            }
            header = StringUtils.trim(header);
            if (header.isEmpty()) {
                throw new IllegalConfigException(key);
            }
            headers[i] = header;
        }
        if (new HashSet<>(Arrays.asList(headers)).size() != headers.length) {
            throw new IllegalConfigException(key);
        }
        int[] minColumnWidths = new int[minColumnWidthList.size()];
        for (int i = 0; i < minColumnWidths.length; i++) {
            if (!(minColumnWidthList.get(i) instanceof Integer minColumnWidth)) {
                throw new IllegalConfigException(key);
            }
            minColumnWidths[i] = minColumnWidth;
        }
        return new TableHeader(headers, minColumnWidths);
    }

    @NotNull
    public ScrollDirection parseScrollDirection(@NotNull Key key) {
        return switch (parseString(false, false, key)) {
            case "none" -> ScrollDirection.NONE;
            case "x" -> ScrollDirection.X;
            case "y" -> ScrollDirection.Y;
            case "all" -> ScrollDirection.ALL;
            default -> throw new IllegalConfigException(key);
        };
    }

    @NotNull
    public ScrollBarSize parseScrollBarSize(@NotNull Key key) {
        if (!(getProperty(key) instanceof Map<?, ?> map) || !(map.get("x") instanceof Integer x) || !(map.get("y") instanceof Integer y)) {
            throw new IllegalConfigException(key);
        }
        return new ScrollBarSize((int) (x * HEIGHT_SCALE), (int) (y * WIDTH_SCALE));
    }

    @NotNull
    public LoadingSize parseLoadingSize(@NotNull Key key) {
        if (!(getProperty(key) instanceof Map<?, ?> map) || !(map.get("thickness") instanceof Integer thickness) || !(map.get("diameter") instanceof Integer diameter)) {
            throw new IllegalConfigException(key);
        }
        return new LoadingSize((int) (thickness * HEIGHT_SCALE), (int) (diameter * WIDTH_SCALE));
    }
}