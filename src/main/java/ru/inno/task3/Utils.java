package ru.inno.task3;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {
    public static boolean isValueFromCache; // Переменная введена и используется только для тестов

    // Введена и используется только для тестов (содержет записи, удаленные при очистке кэша)
    public static ConcurrentHashMap<PairedKey, Object> clearedData = new ConcurrentHashMap<>();

    public static String viewClearedData() {
        if (clearedData.isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder();


        clearedData.keySet().stream().sorted(PairedKey::compare).forEach(pk -> sb.append("{")
                .append(pk.getMethod())
                .append(" ")
                .append(pk.getTimeLive())
                .append(" ")
                .append(pk.getStrTime(pk.getTimeLive()))
                .append(" ")
                .append(clearedData.get(pk))
                .append("}\n"));

        return "Строки удалённые в процессе чистки кэша:\n" + sb.toString();
    }

    public static <T> T cache(T obj) {
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new MyWrapper<>(obj));
    }
}
