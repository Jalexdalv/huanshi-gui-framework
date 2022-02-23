package com.huanshi.gui.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class SecurityUtils {
    @NotNull
    public static String md5Hash32(@NotNull String data) {
        return DigestUtils.md5Hex(data);
    }

    @NotNull
    public static String md5Hash32(@NotNull String data, @NotNull String salt) {
        return DigestUtils.md5Hex(data + salt);
    }

    @NotNull
    public static String md5Hash16(@NotNull String data) {
        return md5Hash32(data).substring(8, 24);
    }

    @NotNull
    public static String md5Hash16(@NotNull String data, @NotNull String salt) {
        return md5Hash32(data, salt).substring(8, 24);
    }
}