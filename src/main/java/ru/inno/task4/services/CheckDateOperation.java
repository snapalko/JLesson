package ru.inno.task4.services;

import lombok.extern.log4j.Log4j2;
import ru.inno.task4.model.Model;
import ru.inno.task4.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@Log4j2
public class CheckDateOperation implements Consumer<Model> {
    @Override
    public void accept(Model model) {
        List<User> userList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);

        for (User user : model.getUsersList()) {
            try {
                Date date = formatter.parse(user.getAccessDate());
                userList.add(user);
            } catch (ParseException e) {
                log.error("У user-a {} дата указана не правильно! ({})", user.getFio(), user.getAccessDate());
            }
        }
        model.setUsersList(userList);
    }
}

