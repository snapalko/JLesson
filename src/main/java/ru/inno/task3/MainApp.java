package ru.inno.task3;

public class MainApp {
    public static void main(String[] args) throws InterruptedException {

        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);

        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(5);        System.out.println("-- вызов num.setNum(5)");
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(2);        System.out.println("-- вызов num.setNum(2)");
        num.doubleValue();// sout молчит
        num.doubleValue();// sout молчит

        System.out.println("засыпаем на 1.5 сек...");
        Thread.sleep(1500);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(8);        System.out.println("-- вызов num.setNum(8)");
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(12);        System.out.println("-- вызов num.setNum(12)");
        num.doubleValue();// sout молчит
        num.doubleValue();// sout молчит

        System.out.println("засыпаем на 1.5 сек...");
        Thread.sleep(1500);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        System.out.println(Utils.viewClearedData());
    }
}
