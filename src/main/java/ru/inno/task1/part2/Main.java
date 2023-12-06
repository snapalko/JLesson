package ru.inno.task1.part2;

public class Main {
    public static void main(String[] args) {
        Account account = new Account("Иван Иванович");
        System.out.println(account.getName());

        ActionManager actionManager = new ActionManager();
//        actionManager.undo();

        Command changeCurrency = new ChangeCurrencyCommand(account, Currency.USD, 50);
//        actionManager.execute(changeCurrency);
//        System.out.println("Добавили 50 долларов: " + account.getCurrencyAndAmount());

        /*Command*/ changeCurrency = new ChangeCurrencyCommand(account, Currency.RUB, 100);
        actionManager.execute(changeCurrency);
        System.out.println("Добавили 100 рублей: " + account.getCurrencyAndAmount());

        Command changeAccountName = new ChangeAccounNameCommand(account, "Василий Иванов");
        actionManager.execute(changeAccountName);
        System.out.println("Сменили имя на " + account.getName());

        changeCurrency = new ChangeCurrencyCommand(account, Currency.RUB, 300);
        actionManager.execute(changeCurrency);
        System.out.println("Установили количество рублей на 300: " + account.getCurrencyAndAmount());
        System.out.println("------------------------------------------");

        actionManager.undo();
        System.out.println(account.getName() + " " + account.getCurrencyAndAmount());
//        System.out.println(account.getSaveStates());
        System.out.println("---------first undo-------------");

        actionManager.undo();
        System.out.println(account.getName() + " " + account.getCurrencyAndAmount());
//        System.out.println(account.getSaveStates());
        System.out.println("---------second undo-------------");

        actionManager.undo();
        System.out.println(account.getName() + " " + account.getCurrencyAndAmount());
        System.out.println("==========================================");

        System.out.println("---------Illegal undo");
//        actionManager.undo();
        System.out.println(account.getName() + " " + account.getCurrencyAndAmount());
        System.out.println("==========================================");
        System.out.println("---------Illegal undo 2");
//        actionManager.undo();
    }
}
