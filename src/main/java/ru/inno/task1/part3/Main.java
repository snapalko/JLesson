package ru.inno.task1.part3;

public class Main {
    public static void main(String[] args) {
        AccountHistory history = new AccountHistory();

        Account account = new Account("Иван Иванович");
        account.print();

        history.saveState(account);

        account.setCurrencyAndAmount(Currency.RUB, 100);
        account.print();

        history.saveState(account);

        account.setName("Account #1");
        account.setCurrencyAndAmount(Currency.USD, 200);
//        account.setCurrencyAndAmount(Currency.RUB, 200);
        account.print();

        history.restoreState(account);
        account.print();
    }
}
