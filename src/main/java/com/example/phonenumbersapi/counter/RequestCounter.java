package com.example.phonenumbersapi.counter;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


@Component
public class RequestCounter {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void increment() {
        counter.incrementAndGet();
    }

    public static int getCount() {
        return counter.get();
    }

    public static void reset() {
        counter.set(0);
    }
}

