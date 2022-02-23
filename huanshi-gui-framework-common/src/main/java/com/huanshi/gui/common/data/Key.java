package com.huanshi.gui.common.data;

import com.huanshi.gui.common.exception.IllegalSubKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Key implements Iterable<String>, Cloneable {
    private LinkedList<Object> subKeyList = new LinkedList<>();
    @Getter
    private String value;

    public Key(@NotNull String subKey) {
        addLast(subKey);
    }

    public Key(@NotNull String[] subKey) {
        addLast(subKey);
    }

    @Override
    @NotNull
    public Iterator<String> iterator() {
        return new Iterator<>() {
            private final ArrayList<String> nameList = new ArrayList<>();
            private int current = 0;
            {
                for (Object subKey : subKeyList) {
                    if (subKey instanceof String string) {
                        nameList.add(string);
                    } else if (subKey instanceof String[] strings) {
                        nameList.addAll(Arrays.asList(strings));
                    }
                }
            }
            @Override
            public boolean hasNext() {
                return current < nameList.size();
            }
            @Override
            @NotNull
            public String next() {
                return nameList.get(current++);
            }
        };
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        return value.equals(((Key) object).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @SneakyThrows
    @Override
    @NotNull
    public Key clone() {
        Key key = (Key) super.clone();
        key.subKeyList = new LinkedList<>(subKeyList);
        key.value = value;
        return key;
    }

    private void updateValue() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : this) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(" -> ");
            }
            stringBuilder.append(name);
        }
        value = stringBuilder.toString();
    }

    public void addLast(@NotNull String subKey) {
        String formatSubKey = StringUtils.trimToNull(subKey);
        if (formatSubKey == null) {
            throw new IllegalSubKeyException(subKey);
        }
        subKeyList.addLast(formatSubKey);
        updateValue();
    }

    public void addLast(@NotNull String[] subKey) {
        String[] cloneSubKey = subKey.clone();
        for (int i = 0; i < cloneSubKey.length; i++) {
            cloneSubKey[i] = StringUtils.trimToNull(cloneSubKey[i]);
            if (cloneSubKey[i] == null) {
                throw new IllegalSubKeyException(subKey);
            }
        }
        subKeyList.addLast(cloneSubKey);
        updateValue();
    }

    public void removeLast() {
        if (!subKeyList.isEmpty()) {
            subKeyList.removeLast();
            updateValue();
        }
    }
}