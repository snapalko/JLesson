package ru.inno.task1.part2;

import java.util.ArrayDeque;

public class ActionManager {
    private final ArrayDeque<Command> queueCommands = new ArrayDeque<>();

    public void execute(Command command) {
        command.execute();
        queueCommands.addLast(command);
    }

    public void undo() {
        if(queueCommands.isEmpty()) {
            throw new IllegalStateException("Невозможно отменить команду!");
        }

        Command command = queueCommands.pollLast();
        if (command == null) {
            throw new IllegalStateException("Невозможно отменить команду!");
        }
        command.undo();
    }
}
