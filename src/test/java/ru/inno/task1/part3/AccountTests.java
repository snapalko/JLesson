package ru.inno.task1.part3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountTests {
    @Test
    @DisplayName("Проверка сохранения и восстановления предыдущего состояния счета")
    void account_save_restore_test() {
        AccountHistory history = new AccountHistory();

        Account account = new Account("Иван Иванович");

        history.saveState(account);
        Assertions.assertEquals("Иван Иванович", account.getName());

        account.setCurrencyAndAmount(Currency.RUB, 100);
        account.print();
        history.saveState(account);
        String state = "Иван Иванович {RUB=100}";
        Assertions.assertEquals(state, account.getName() + " " + account.getCurrencyAndAmount());

        account.setName("Account #1");
        account.setCurrencyAndAmount(Currency.USD, 200);
        account.print();
//        state = "Account #1 {USD=200, RUB=100}";
        state = "Account #1 {RUB=100, USD=200}";
        Assertions.assertEquals(state, account.getName() + " " + account.getCurrencyAndAmount());

        history.restoreState(account);
        account.print();
        state = "Иван Иванович {RUB=100}";
        Assertions.assertEquals(state, account.getName() + " " + account.getCurrencyAndAmount());
    }
}
