package ru.inno.task3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class MyWrapper<T> implements InvocationHandler {
    T obj;
    private final Object lock = new Object();
    //
    private final ConcurrentHashMap<PairedKey, Object> cacheData = new ConcurrentHashMap<>();

    private boolean cacheContainsKey = false; // в Кэше сохранет вызов данного метода? (не гарантируется, т.к. кэш чистится периодически)
    long cacheTimeLife;

    public MyWrapper(T obj) {
        this.obj = obj;
    }

    public String viewCacheData() {
        if (cacheData.isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder();

        cacheData.keySet().stream().sorted(PairedKey::compare).forEach(pk -> sb.append("{")
                .append(pk.getMethod())
                .append(" ")
                .append(pk.getTimeLive())
                .append(" ")
                .append(pk.getStrTime(pk.getTimeLive()))
                .append(" ")
                .append(cacheData.get(pk))
                .append("}\n"));

        return sb.toString();
    }

    public void clearCache() throws InterruptedException {

        long currentTimeMillis = System.currentTimeMillis();
        Object object;
        for (PairedKey pk : cacheData.keySet()) {

            if (!pk.isLive(currentTimeMillis)) {
                // Если истекло время жизни строки в кэше
                synchronized (lock) {
                    object = cacheData.remove(pk);
                    if (!(object == null)) {
                        Utils.clearedData.put(pk, object);
                        System.out.println(" - чистим кэш: " + pk + " " + object);
                    }
                }

            }
            Thread.sleep(1000L);
        }
    }

    public PairedKey getLastKeyFromCache(Method method) {
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
            try {
                this.clearCache();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        Object result;
        Object state;

        Method myMethod = obj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

        if (myMethod.isAnnotationPresent(Mutator.class)) {
            cacheContainsKey = false;
            Utils.isValueFromCache = false;
            result = myMethod.invoke(obj, args);
            return result;
        }

        if (myMethod.isAnnotationPresent(Cache.class)) {
            // Время жизни указанное в атрибуте аннотации Cache
            cacheTimeLife = myMethod.getAnnotation(Cache.class).value();

            // Если метод уже вызывался, результат должен быть записаться в кэш
            if (cacheContainsKey) {
                // Находим последнюю запись в кэше в парном ключе по этому методу.
                PairedKey pairedKey = getLastKeyFromCache(myMethod);

                if (pairedKey != null) {
                    // Берём значение из кэша
                    synchronized (lock) {
//                        state = cacheData.get(pairedKey);

                        System.out.println("Берём значение из кэша");
                        // Берём значение из кэша с удалением от туда записи, т.к. значение ключа поменяется
                        state = cacheData.remove(pairedKey);

                        // Меняем время жизни записи в кэше на текущее время (изменяем ключ)
                        long currentTimeLive = System.currentTimeMillis() + cacheTimeLife;
                        pairedKey.setTimeLife(currentTimeLive);

                        // и записывает обратно это в кэш
                        cacheData.put(pairedKey, state);
                    }

                    Utils.isValueFromCache = true;
                    result = state;
                } else {
                    // В кэше не нашлось записи с вызовом этого метода, вызываем сам метод
                    result = myMethod.invoke(obj, args);
                    synchronized (lock) {
                        cacheData.put(new PairedKey(myMethod, cacheTimeLife), result);
                    }
                    cacheContainsKey = true;
                    Utils.isValueFromCache = false;
                }
            } else {
                // Было изменение состояния объекта - вызываем сам метод
                result = myMethod.invoke(obj, args);
                synchronized (lock) {
                    cacheData.put(new PairedKey(myMethod, cacheTimeLife), result);
                }
                cacheContainsKey = true;
                Utils.isValueFromCache = false;
            }
            return result;
        }

        System.out.println("метод без аннотаций");
        cacheContainsKey = false;
        Utils.isValueFromCache = false;
        result = method.invoke(obj, args);
        return result;
    }
}

