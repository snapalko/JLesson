package ru.inno.task3;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class MyWrapper<T> implements InvocationHandler {
    T obj;
    private final ConcurrentHashMap<PairedKey, Object> cacheData = new ConcurrentHashMap<>();
    //private Map<PairedKey, Object> cacheData = new HashMap<>();
    long cacheTimeLife;
//    private final Object lock = new Object();

    public MyWrapper(T obj) {
        this.obj = obj;
    }

    public String viewCacheData() {
        StringBuilder sb = new StringBuilder();
        if (cacheData.isEmpty()) return "{}";
        for (PairedKey pk : cacheData.keySet()) {
            sb.append("{")
                    .append(pk.getMethod())
                    .append(" ")
                    .append(pk.getTimeLive())
                    .append(" ")
                    .append(pk.getStrTime(pk.getTimeLive()))
                    .append(" ")
                    .append(cacheData.get(pk))
                    .append("}\n");
        }
        return sb.toString();
    }

//    public Map<PairedKey, Object> mapCopy(Map<PairedKey, Object> original) throws IOException, ClassNotFoundException {
//        Map<PairedKey, Object> result = new HashMap<>();
//
//        for (Map.Entry<PairedKey, Object> entry : original.entrySet()) {
//            PairedKey originalPairedKey = entry.getKey();
//            PairedKey resultPairedKey = new PairedKey(originalPairedKey.getMethod());
//            resultPairedKey.setTimeLife(originalPairedKey.getTimeLive());
//            Object originalObj = entry.getValue();
//            Object resultObj = Utils.getDeepCloning(originalObj);
//
//            result.put(resultPairedKey, resultObj);
//        }
//        return result;
//    }

    public void clearCache() /*throws IOException, ClassNotFoundException*/ {

        long currentTimeMillis = System.currentTimeMillis();
//        System.out.println("before currentTimeMillis="+getStrTime(currentTimeMillis)+"\n" + viewCacheData());
//        Map<PairedKey, Object> tmpData = mapCopy(cacheData); // Копируем cacheData во временную мапу

//        for (PairedKey pk : tmpData.keySet()) {
        for (PairedKey pk : cacheData.keySet()) {
            /*System.out.println("currentTimeMillis="
                    + getStrTime(currentTimeMillis)
                    + ", pk.timeLife=" + getStrTime(pk.timeLife));*/

            if (!pk.isLive(currentTimeMillis)) {
//                System.out.println(" - чистим кэш: " + cacheData.get(pk));
//                tmpData.remove(pk);
                cacheData.remove(pk);
//                System.out.println("after \n" + viewCacheData());
            }
        }
//        System.out.println("after  " + viewCacheData());
//        synchronized (lock) {
//            cacheData = mapCopy(tmpData);
//        }
    }

    PairedKey getLastKeyFromCache(Method method) {
        /*if (method == null) {
            System.out.println("method == null");
            return null;
        }*/
        try {
            return cacheData.keySet().stream()
                    .filter(s -> s.getMethod().equals(method))
                    .max(PairedKey::compare).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Thread thread = new Thread(() -> {
//            try {
                this.clearCache();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
        });
        thread.start();
        Object result;
        Method myMethod = obj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
//        clearCache();

        if (myMethod.isAnnotationPresent(Mutator.class)) {
            Utils.isValueFromCache = false; //            cacheData.clear();
            result = myMethod.invoke(obj, args);
            return result;
        }

        if (myMethod.isAnnotationPresent(Cache.class)) {
            if (Utils.isValueFromCache) {
                // Время жизни указанное в атрибуте аннотации Cache
                cacheTimeLife = myMethod.getAnnotation(Cache.class).value();
                // Находим последнюю запись в парном ключе по этому методу.
                PairedKey pairedKey = getLastKeyFromCache(myMethod);

                if (pairedKey != null) {
                    System.out.println("Берём значение из кэша: "+pairedKey.toString());
                    // Берём значение из кэша
                    Object object = cacheData.get(pairedKey);
                    object = cacheData.remove(pairedKey);
                    // Меняем время жизни записи в кэше на текущее время
                    pairedKey.setTimeLife(System.currentTimeMillis() + cacheTimeLife);
                    // и записывает это в кэш
                    cacheData.put(pairedKey, object);
                    Utils.isValueFromCache = true;
                    result = object;
                } else {
                    // Не нашлось в кэше записи с вызовом этого метода, вызываем сам метод
                    result = myMethod.invoke(obj, args);
                    cacheData.put(new PairedKey(myMethod, cacheTimeLife), result);
                    Utils.isValueFromCache = true;
                }
            } else {
                // Было изменение состояния объекта - вызываем сам метод
                result = myMethod.invoke(obj, args);
                cacheData.put(new PairedKey(myMethod, cacheTimeLife), result);
                Utils.isValueFromCache = true;
            }
            return result;
        }

        System.out.println("метод без аннотаций");
        Utils.isValueFromCache = false;
        result = method.invoke(obj, args);
        return result;
    }
}

