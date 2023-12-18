package ru.inno.task3;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class MainApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("task3");

        /*Object o1 = "Object1";
        System.out.println(o1);
        Object o2 = null;
        try {
            o2 = Utils.getDeepCloning(o1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(o2);
        o2 = "Object2";
        System.out.println("--------");
        System.out.println(o1);
        System.out.println(o2);*/


        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(5);
        System.out.println("вызов num.setNum(5)");
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(2);
        System.out.println("вызов num.setNum(2)");
        num.doubleValue();// sout молчит
        num.doubleValue();// sout молчит

        System.out.println("засыпаем на 1.5 сек.");
        Thread.sleep(2500);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит
    }
}
