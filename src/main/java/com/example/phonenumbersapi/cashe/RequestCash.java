package com.example.phonenumbersapi.cashe;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestCash {
    private static Map<String, Object> cache = new HashMap<>();

    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static Object get(String key) {
        return cache.get(key);
    }

    public static void remove(String key) {
        cache.remove(key);
    }
    public static boolean containsKey(String key){
        return cache.containsKey(key);
    }

    public static void clear() {
        cache.clear();
    }

    private RequestCash() {
        throw new IllegalStateException("Utility class");
    }
}


