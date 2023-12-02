package ru.inno.task1;

public class Main {
    public static void main(String[] args) {
        Account account = new Account("Какой-то счет");
        System.out.println(account.getName());
        account.putCurrencyAmount(Currency.RUB, 8);
        account.putCurrencyAmount(Currency.USD, 10);
        System.out.println(account.getCurrencyAndAmount());
    }
}
