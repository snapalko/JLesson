package ru.inno.task2;

public class MainApp {
    public static void main(String[] args) {
        Account account = new Account("Vasa");
        account.addCurrency(Currency.RUB, 100);
        MyInterface mi = Utils.cache(account);

        System.out.println(mi.getName());

        mi.setName1("John"); // метод не помечен аннотацией @Cache
        System.out.println(account.getName());
        System.out.println(mi.getName());

        System.out.println(mi.getCurrencies());
        account.addCurrency(Currency.RUB, 500);
        System.out.println(mi.getCurrencies());

        mi.setName("Pete");

        System.out.println(mi.getName());
        System.out.println(mi.getName());
        System.out.println(mi.getCurrencies());

        mi.setName("Vasya");
        System.out.println(mi.getName());
        System.out.println(mi.getName());
        account.addCurrency(Currency.RUB, 300);
        System.out.println(mi.getCurrencies());
    }
}