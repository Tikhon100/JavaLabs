package com.example.phonenumbersapi.cashe;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class RequestCash {
    private static Map<String, Object> cache = new HashMap<>();

    public static void put(final String key, final Object value) {
        cache.put(key, value);
    }

    public static Object get(final String key) {
        return cache.get(key);
    }

    public static void remove(final String key) {
        cache.remove(key);
    }

    public static boolean containsKey(final String key) {
        return cache.containsKey(key);
    }

    public static void clear() {
        cache.clear();
    }

    private RequestCash() {

    }
}


