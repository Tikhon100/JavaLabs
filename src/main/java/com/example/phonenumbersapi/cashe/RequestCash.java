package com.example.phonenumbersapi.cashe;

import java.util.HashMap;
import java.util.Map;


public class RequestCash {
    private static final Map<String, Object> cacheMap = new HashMap<>();

    public Object get(String key) {
        return cacheMap.get(key);
    }

    public void put(String key,Object value) {
        cacheMap.put(key, value);
    }

    public void remove(String key) {
        cacheMap.remove(key);
    }

    public void clear() {
        cacheMap.clear();
    }

}