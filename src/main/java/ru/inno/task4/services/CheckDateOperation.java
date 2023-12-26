package ru.inno.task4.services;

import ru.inno.task4.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;

public class CheckDateOperation implements UnaryOperator<List<User>> {
    @Override
    public List<User> apply(List<User> list) {
        List<User> outList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);

        for (User user : list) {
            try {
                Date date = formatter.parse(user.getAccessDate());
                outList.add(user);
            } catch (ParseException e) {
                toLog(user, "Не правильная дата");
            }
        }
        return outList;
    }

    void toLog(User u, String message) {
        String s = message;
        System.out.println("-----Ошибка у user-a " + u.getFio() + " дата указана не правильно! (" + u.getAccessDate() + ")");
    }
}

