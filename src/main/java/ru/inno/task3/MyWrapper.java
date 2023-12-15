package ru.inno.task3;

import ru.inno.proba.Caсhe;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class MyWrapper<T> implements InvocationHandler {
    T obj;
    //    private final Map<Method, Object> cacheData = new HashMap<>();
//    private final Map<Method, PairedKey> cacheData = new HashMap<>();
    private volatile ConcurrentHashMap<PairedKey, Object> cacheData = new ConcurrentHashMap<>();
    long cacheTimeLife;

    public MyWrapper(T obj) {
        this.obj = obj;
    }

    public void clearCache() {
        /*for (Method m : cacheData.keySet()) {
            PairedKey pairedKey = cacheData.get(m);
            if (pairedKey.timeLife - System.currentTimeMillis() > cacheTimeLife) {
                cacheData.remove(m);
            }
        }*/
        // key list
        List<PairedKey> keyList = new ArrayList<>(cacheData.keySet());
        for (PairedKey pk : keyList) {
            if (pk.timeLife - System.currentTimeMillis() > cacheTimeLife) {
                cacheData.remove(pk);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result;
        Method myMethod = obj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        clearCache();

        if (myMethod.isAnnotationPresent(Mutator.class)) {
            cacheData.clear();
            result = myMethod.invoke(obj, args);
            return result;
        }

        if (myMethod.isAnnotationPresent(Cache.class)) {
            // Время жизни указанное в атрибуте аннотации Cache
            cacheTimeLife = myMethod.getAnnotation(Cache.class).value();

            if (cacheData.containsKey(myMethod)) {
                System.out.println("Объект не менялся - берём значение из кэша");
                Utils.isValueFromCache = true;
                // Берём значение "пары" из кэша
                PairedKey pairedKey = cacheData.get(myMethod);
                // Меняем время жизни на текущее время
                pairedKey.timeLife = System.currentTimeMillis() + cacheTimeLife;
                // и записывает это в кэш
                cacheData.put(myMethod, pairedKey);
                result = pairedKey.object;
            } else {
//                System.out.println("Было изменение состояния объекта - вызов считается новым");
                Utils.isValueFromCache = false;
                result = myMethod.invoke(obj, args);
                PairedKey p = new PairedKey(cacheTimeLife, result);
                cacheData.put(myMethod, p);
            }
            return result;
        }

        System.out.println("метод без аннотаций");
        Utils.isValueFromCache = false;
        result = method.invoke(obj, args);
        return result;
    }
}

class PairedKey {
    private Method method;

    long timeLife;

    public PairedKey(Method method, long timeout) {
        this.method = method;
        this.timeLife = System.currentTimeMillis() + timeout;
    }

    public Method getMethod() {
        return method;
    }
}