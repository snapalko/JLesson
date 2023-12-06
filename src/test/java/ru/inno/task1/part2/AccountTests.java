package ru.inno.task1.part2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTests {
    @Test
    @DisplayName("Создание счета с пустым наименованием кидает исключение")
    void newAccount_blankName_test() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Account(""));
    }

    @Test
    @DisplayName("Проверка невозможности восстановления, так как не было сохранения в предыдущее состояние счета")
    void Account_cannot_be_restored_test() {
        Account account = new Account("Иван Иванович");
        ActionManager actionManager = new ActionManager();
        Assertions.assertThrows(IllegalStateException.class, () -> actionManager.undo());
    }

    @Test
    @DisplayName("Проверка восстановления предыдущего состояния счета (3 отмены)")
    void Account_undo_test() {
        Account account = new Account("Иван Иванович");
        System.out.println(account.getName());

        ActionManager actionManager = new ActionManager();
        Command changeCurrency = new ChangeCurrencyCommand(account, Currency.RUB, 100);
        actionManager.execute(changeCurrency);
        System.out.println("Добавили 100 рублей: " + account.getCurrencyAndAmount());
        String state1 = "Иван Иванович {RUB=100}";
        Assertions.assertEquals(state1, account.getName() + " " + account.getCurrencyAndAmount());

        Command changeAccountName = new ChangeAccounNameCommand(account, "Василий Иванов");
        actionManager.execute(changeAccountName);
        System.out.println("Сменили имя на " + account.getName());
        String state2 = "Василий Иванов";
        Assertions.assertEquals(state2, account.getName());

        changeCurrency = new ChangeCurrencyCommand(account, Currency.RUB, 300);
        actionManager.execute(changeCurrency);
        System.out.println("Установили количество рублей на 300: " + account.getCurrencyAndAmount());
        String state3 = "Василий Иванов {RUB=300}";
        Assertions.assertEquals(state3, account.getName() + " " + account.getCurrencyAndAmount());

        System.out.println("---------first undo-------------");
        actionManager.undo();
        String state4 = "Василий Иванов {RUB=100}";
        Assertions.assertEquals(state4,account.getName() + " " + account.getCurrencyAndAmount());

        System.out.println("---------second undo-------------");
        actionManager.undo();
        String state5 = "Иван Иванович {RUB=100}";
        Assertions.assertEquals(state5, account.getName() + " " + account.getCurrencyAndAmount());

        System.out.println("---------third undo-------------");
        actionManager.undo();
        String state6 = "Иван Иванович {}";
        Assertions.assertEquals(state6, account.getName() + " " + account.getCurrencyAndAmount());
        System.out.println("==========================================");

        System.out.println("---------Illegal undo");
        Assertions.assertThrows(IllegalStateException.class, () -> actionManager.undo());
    }
}
