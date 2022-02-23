package com.huanshi.gui.controller.manager;

import cn.gjing.http.FallBackHelper;
import cn.gjing.http.HttpClient;
import cn.gjing.http.HttpMethod;
import cn.gjing.http.Listener;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class AbstractHttpManager extends AbstractManager {
    public void post(@NotNull String url, @NotNull HashMap<String, String> header, @NotNull Object body, @NotNull FallBackHelper<String> fallbackHelper, @NotNull Listener<String> listener, @NotNull ExceptionHandler exceptionHandler) {
        try {
            HttpClient.builder(url, HttpMethod.POST, String.class).header(header).body(JSON.toJSON(body)).readTimeout(15000).connectTimeout(10000).execute().fallback(fallbackHelper).listener(listener);
        } catch (Throwable throwable) {
            exceptionHandler.handle(throwable);
        }
    }
}