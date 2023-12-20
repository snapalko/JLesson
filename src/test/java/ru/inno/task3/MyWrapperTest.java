package ru.inno.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class MyWrapperTest {

    @Test
    @DisplayName("проверка метода, который возвращает ключ для Map-ы с максимальной timeLife")
    void getLastKeyFromCache() {
        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);
        MyWrapper mw = new MyWrapper<>(num);
        Method myMethod=null;
        Class[] parameterType = null;
        try {
            System.out.println(mw.getClass().getName());
            myMethod = mw.getClass().getDeclaredMethod("doubleValue", parameterType);
//            myMethod = mw.getClass().getDeclaredMethod("public double ru.inno.task3.MyWrapper.doubleValue()", parameterType);
//            myMethod = mw.getClass().getDeclaredMethod("public double ru.inno.task3.Fraction.doubleValue()", parameterType);
        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        try {
            new PairedKey(myMethod, 1000L);
            Thread.sleep(1000L);
            new PairedKey(myMethod, 1000L);
            Thread.sleep(2000L);
            new PairedKey(myMethod, 1000L);
            Thread.sleep(5000L);
            new PairedKey(myMethod, 1000L);
            Thread.sleep(1000L);
            new PairedKey(myMethod, 1000L);
            Thread.sleep(3000L);
            new PairedKey(myMethod, 1000L);
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PairedKey pk = mw.getLastKeyFromCache(myMethod);
        System.out.println(pk);
    }
}