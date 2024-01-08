package ru.inno.task4.services;

import ru.inno.task4.model.Model;
import ru.inno.task4.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CheckTypeAppOperation implements Consumer<Model> {
    @Override
    public void accept(Model model) {
        List<User> userList = new ArrayList<>();

        for (User u : model.getUsersList()) {
            if (!u.getApplication().equals("web") && !u.getApplication().equals("mobile"))
                u.setApplication("other:" + u.getApplication());
            userList.add(u);
        }
        model.setUsersList(userList);
    }
}
