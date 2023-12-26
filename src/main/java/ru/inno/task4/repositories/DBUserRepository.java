package ru.inno.task4.repositories;

import org.springframework.stereotype.Component;
import ru.inno.task4.model.Model;
import ru.inno.task4.model.User;

@Component
public class DBUserRepository implements UserRepository {
    @Override
    public void storeUser(Model model) {
        System.out.println("DBUserRepository.storeUser:");
        for (User user : model.getUsersList()) {
            System.out.println(user.getUserName() + " "
                    + user.getFio() + " "
                    + user.getAccessDate() + " "
                    + user.getApplication() + " "
            );
        }
    }
}

