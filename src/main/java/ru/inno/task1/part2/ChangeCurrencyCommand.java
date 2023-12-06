package ru.inno.task1.part2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ChangeCurrencyCommand implements Command {
    private final Account account;
    private final Currency newCurrency;
    private final int newAmount;

    public ChangeCurrencyCommand(Account account, Currency currency, int amount) {
        this.account = account;
        this.newCurrency = currency;
        this.newAmount = amount;
    }

    @Override
    public void execute() {
        account.putCurrencyAmount(newCurrency, newAmount, true);
    }

    @Override
    public void undo() {
        Currency currency;
        Integer amount;

        String jsonString = account.getSaveStates().pollLast();
        account.clearCurrencyAndAmount();

        if (jsonString.equals("{}")) {
            return;
        }

        Type typeOfHashMap = new TypeToken<HashMap<Currency, Integer>>() {}.getType();
        Gson gson = new Gson();
        HashMap<Currency, Integer> currencyAndAmount = gson.fromJson(jsonString, typeOfHashMap);
        if (!currencyAndAmount.isEmpty()) {
            for (Map.Entry<Currency, Integer> item : currencyAndAmount.entrySet()) {
                currency = item.getKey();
                amount = item.getValue();

                account.putCurrencyAmount(currency, amount);
            }
        }
    }
}