package ru.inno.task2;

import java.lang.reflect.Proxy;

public class Utils {
    public static boolean isValueFromCache; // Переменная введена и используется только для тестов

    public static <T> T cache(T obj) {
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                 obj.getClass().getInterfaces(),
                new MyWrapper<>(obj));
    }
}

