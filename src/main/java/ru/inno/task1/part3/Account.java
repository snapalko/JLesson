package ru.inno.task1.part3;

import java.util.HashMap;

/**
 * The Originator (создатель) class
 */
public class Account {
    private String name;

    private void checkName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Имя счета не может быть пустым!");
        }
        this.name = name;
    }

    public void setName(String name) {
        checkName(name);
    }

    public String getName() {
        return name;
    }

    private HashMap<Currency, Integer> currencyAndAmount = new HashMap<>();

    public void setCurrencyAndAmount(Currency currency, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Количество валюты не может быть отрицательным!");
        }

        this.currencyAndAmount.put(currency, amount);
    }

    public HashMap<Currency, Integer> getCurrencyAndAmount() {
        return currencyAndAmount;
    }

    public Account(String name) {
        checkName(name);
    }

    public AccountMemento saveState() {
        System.out.println("Сохранение счета.");
        return new AccountMemento(name, currencyAndAmount);
    }

    public void restoreState(Object obj) {
        System.out.println("Восстановление счета.");
        AccountMemento memento = (AccountMemento) obj;
        name = memento.name;
        currencyAndAmount = new HashMap<>(memento.currencyAndAmount);
    }

    public void print() {
        System.out.println("account.name = " + name + ", currencyAndAmount = " + currencyAndAmount);
    }

    private class AccountMemento {
        private final String name;
        private final HashMap<Currency, Integer> currencyAndAmount;

        public AccountMemento(String name, HashMap<Currency, Integer> currencyAndAmount) {
            this.name = name;
            this.currencyAndAmount = new HashMap<>(currencyAndAmount);
        }
    }
}
