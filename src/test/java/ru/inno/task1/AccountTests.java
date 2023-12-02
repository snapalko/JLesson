package ru.inno.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class AccountTests {
    @Test
    @DisplayName("Создание счета с пустым наименованием кидает исключение")
    void newAccount_blankName_test() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Account(""));
    }

    @Test
    @DisplayName("Создание счета с непустым наименованием")
    void newAccount_notBlankName_test() {
        Account account = new Account("S");
        Assertions.assertEquals("S", account.getName());
    }

    @Test
    @DisplayName("Проверка метода, который заменяет количество валюты на указанное или добавляет эту валюту при отсутствии таковой")
    void putCurrencyAmount_test() {
        Account account = new Account("S");
        HashMap<Currency, Integer> map = new HashMap<>();
        map.put(Currency.RUB, 8);
        account.putCurrencyAmount(Currency.RUB, 8);
        Assertions.assertEquals(map, account.getCurrencyAndAmount());
        map.put(Currency.USD, 10);
        account.putCurrencyAmount(Currency.USD, 10);
        Assertions.assertEquals(map, account.getCurrencyAndAmount());
    }

    @Test
    @DisplayName("Создание счета с отрицательным количеством валюты кидает исключение")
    void putCurrencyAmount_negativeNumber_test() {
        Account account = new Account("S");
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.putCurrencyAmount(Currency.RUB, -1));
    }
}
