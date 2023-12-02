package ru.inno.task1;

import java.util.HashMap;

public class Account {
    private String name;
    private HashMap<Currency, Integer> currencyAndAmount = new HashMap<>();

    public Account(String name) {
        checkName(name);
        this.currencyAndAmount.put(Currency.RUB, 1);
    }

    private void checkName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Имя счета не может быть пустым!");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkName(name);
    }

    public HashMap<Currency, Integer> getCurrencyAndAmount() {
        return currencyAndAmount;
    }

    public void putCurrencyAmount(Currency currency, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Количество валюты не может быть отрицательным!");
        }
        this.currencyAndAmount.put(currency, amount);
    }
}
