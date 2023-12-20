package ru.inno.task3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class MyWrapper<T> implements InvocationHandler {
    T obj;
    private final Object lock = new Object();
    //
    private final ConcurrentHashMap<PairedKey, Object> cacheData = new ConcurrentHashMap<>();
    long cacheTimeLife;

    public MyWrapper(T obj) {
        this.obj = obj;
    }

    public String viewCacheData() {
        StringBuilder sb = new StringBuilder();
        if (cacheData.isEmpty()) return "{}";
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

    public String getStrTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss-SS");
        Date date = new Date(time);
        return formatter.format(date);
    }

    public void clearCache() throws InterruptedException /*throws IOException, ClassNotFoundException*/ {

        long currentTimeMillis = System.currentTimeMillis();
        Object object;
        for (PairedKey pk : cacheData.keySet()) {

            if (!pk.isLive(currentTimeMillis)) {
                // Если истекло время жизни строки в кэше
                synchronized (lock) {
                    object = cacheData.remove(pk);
                }
                System.out.println(" - чистим кэш: " + pk + " " + object);
            }
            Thread.sleep(1000L);
        }
//        System.out.println("after\n" + viewCacheData());
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
        Method myMethod = obj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

        if (myMethod.isAnnotationPresent(Mutator.class)) {
            Utils.isValueFromCache = false;
            result = myMethod.invoke(obj, args);
            return result;
        }

        if (myMethod.isAnnotationPresent(Cache.class)) {
            if (Utils.isValueFromCache) {
                // Время жизни указанное в атрибуте аннотации Cache
                cacheTimeLife = myMethod.getAnnotation(Cache.class).value();
                // Находим последнюю запись в парном ключе по этому методу.
                PairedKey pairedKey = getLastKeyFromCache(myMethod);
                Object state;
                if (pairedKey != null) {
//                    System.out.println(viewCacheData());
                    // Берём значение из кэша
                    synchronized (lock) {
                        state = cacheData.get(pairedKey);

                        System.out.println("Берём значение из кэша: " + pairedKey + " " + state);
                        state = cacheData.remove(pairedKey);

                        // Меняем время жизни записи в кэше на текущее время
                        long currentTimeLive = System.currentTimeMillis() + cacheTimeLife;
                        pairedKey.setTimeLife(currentTimeLive);

                        // и записывает это в кэш
                        cacheData.put(pairedKey, state);
                    }
                    Utils.isValueFromCache = true;
                    result = state;
                } else {
                    // Не нашлось в кэше записи с вызовом этого метода, вызываем сам метод
                    result = myMethod.invoke(obj, args);
                    synchronized (lock) {
                        cacheData.put(new PairedKey(myMethod, cacheTimeLife), result);
                    }
                    Utils.isValueFromCache = true;
                }
            } else {
                // Было изменение состояния объекта - вызываем сам метод
                result = myMethod.invoke(obj, args);
                synchronized (lock) {
                    cacheData.put(new PairedKey(myMethod, cacheTimeLife), result);
                }
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

