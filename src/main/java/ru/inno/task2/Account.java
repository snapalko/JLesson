package ru.inno.task2;

import java.util.HashMap;
import java.util.Map;

public class Account implements MyInterface {
    private String name;
    private final Map<Currency, Integer> currencies = new HashMap<>();

    public Account(String name) {
        setName(name);
    }

    @Override
    @Cache
    public String getName() {
        return name;
    }

    @Mutator
    @Override
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }

    @Override
    // Метод не помечен аннотацией @Cache
    public void setName1(String name) {
        setName(name);
    }

    @Override
    @Cache
    public Map<Currency, Integer> getCurrencies() {
        return new HashMap<>(currencies);
    }

    public void addCurrency(Currency currency, Integer value) {
        if (value < 0) throw new IllegalArgumentException("Колличество валюты не может быть отрицательным!");
        currencies.put(currency, value);
    }
}
