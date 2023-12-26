package ru.inno.task4.services;

import ru.inno.task4.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class CheckTypeAppOperation implements UnaryOperator<List<User>> {
    @Override
    public List<User> apply(List<User> list) {
        List<User> outList = new ArrayList<>();

        for (User u : list) {
            if (!u.getApplication().equals("web") && !u.getApplication().equals("mobile"))
                u.setApplication("other:" + u.getApplication());
            outList.add(u);
        }
        return outList;
    }
}
