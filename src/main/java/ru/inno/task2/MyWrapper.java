package ru.inno.task2;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MyWrapper<T> implements InvocationHandler {
    T obj;
    private final Map<Method, Object> cacheData = new HashMap<>();

    public MyWrapper(T obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method myMethod = obj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

        Annotation[] annotations = myMethod.getAnnotations();
        for (Annotation a : annotations) {
            String strAnnotation = a.annotationType().getName();

            if (strAnnotation.contains("Mutator")) {
                cacheData.clear();
                return myMethod.invoke(obj, args);
            }
            if (strAnnotation.contains("Cache")) {
                if (cacheData.containsKey(myMethod)) {
                    System.out.println("Объект не менялся - берём значение из кэша");
                    Utils.isValueFromCache = true;
                    return cacheData.get(myMethod);
                } else {
                    System.out.println("Было изменение состояния объекта - вызов считается новым");
                    Utils.isValueFromCache = false;
                    Object result = myMethod.invoke(obj, args);
                    cacheData.put(myMethod, result);
                    return result;
                }
            }
        }
        System.out.println("метод без аннотаций");
        Utils.isValueFromCache = false;
        return method.invoke(obj, args);
    }
}
