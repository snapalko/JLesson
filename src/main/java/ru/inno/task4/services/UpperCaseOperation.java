package ru.inno.task4.services;

import ru.inno.task4.model.Model;
import ru.inno.task4.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UpperCaseOperation implements Consumer<Model> {
    @Override
    public void accept(Model model) {
        List<User> userList = new ArrayList<>();

        for (User user : model.getUsersList()) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sbFio = new StringBuilder();

            if (!user.getLastName().isBlank()) {
                sb.append(user.getLastName().substring(0, 1).toUpperCase())
                        .append(user.getLastName().substring(1));
            }
            user.setLastName(sb.toString());
            sbFio.append(user.getLastName()).append(" ");

            sb = new StringBuilder();
            if (!user.getFirstName().isBlank()) {
                sb.append(user.getFirstName().substring(0, 1).toUpperCase())
                        .append(user.getFirstName().substring(1));
            }
            user.setFirstName(sb.toString());
            sbFio.append(user.getFirstName()).append(" ");

            sb = new StringBuilder();
            if (!user.getMidlName().isBlank()) {
                sb.append(user.getMidlName().substring(0, 1).toUpperCase())
                        .append(user.getMidlName().substring(1));
            } else sb.append(" ");
            user.setMidlName(sb.toString());
            sbFio.append(user.getMidlName());

            user.setFio(sbFio.toString());
            userList.add(user);
        }
        model.setUsersList(userList);
    }
}
