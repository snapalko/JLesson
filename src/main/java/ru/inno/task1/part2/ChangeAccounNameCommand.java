package ru.inno.task1.part2;

public class ChangeAccounNameCommand implements Command {
    private final Account account;
    private final String newName;

    public ChangeAccounNameCommand(Account account, String newName) {
        this.account = account;
        this.newName = newName;
    }

    @Override
    public void execute() {
        account.setName(newName, true);
    }

    public void undo() {
        String oldName = account.getSaveStates().pollLast();
        account.setName(oldName);
    }
}
