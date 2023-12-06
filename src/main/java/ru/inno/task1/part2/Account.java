package ru.inno.task1.part2;

import com.google.gson.Gson;

import java.util.ArrayDeque;
import java.util.HashMap;

/** The Receiver class */
public class Account {
    private String name;
    private final HashMap<Currency, Integer> currencyAndAmount = new HashMap<>();

    private final ArrayDeque<String> saveStates = new ArrayDeque<>();

    public ArrayDeque<String> getSaveStates() {
        return saveStates;
    }

    private void save(String oldValue) {
        saveStates.addLast(oldValue);
    }

    public Account(String name) {
        checkName(name);
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

    public void setName(String name, boolean bSave) {
        if (bSave) {
            save(this.name);
        }
        checkName(name);
    }

    public HashMap<Currency, Integer> getCurrencyAndAmount() {
        return currencyAndAmount;
    }
    public void clearCurrencyAndAmount() {
        currencyAndAmount.clear();
    }

    public void putCurrencyAmount(Currency currency, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Количество валюты не может быть отрицательным!");
        }

        this.currencyAndAmount.put(currency, amount);
    }

    public void putCurrencyAmount(Currency currency, int amount, boolean bSave) {
        if (amount < 0) {
            throw new IllegalArgumentException("Количество валюты не может быть отрицательным!");
        }
        if (bSave) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(this.currencyAndAmount);

            save(jsonString);
        }
        this.currencyAndAmount.put(currency, amount);
    }
}
