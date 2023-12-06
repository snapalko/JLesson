package ru.inno.task1.part3;

/** The Caretaker (опекун) class */
public class AccountHistory {
    private Object obj;

    public void saveState(Account account) {
        this.obj = account.saveState();
    }

    public void restoreState(Account account) {
        account.restoreState(obj);
    }
}
