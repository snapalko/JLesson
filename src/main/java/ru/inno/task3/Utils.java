package ru.inno.task3;

import java.io.*;
import java.lang.reflect.Proxy;

public class Utils {
    public static boolean isValueFromCache; // Переменная введена и используется только для тестов

    public static <T> T cache(T obj) {
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new MyWrapper<>(obj));
    }

    public static Object getDeepCloning(Object obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

}
