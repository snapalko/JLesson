package ru.inno.task4.repositories;

import ru.inno.task4.model.User;

public interface UserService {
    boolean userExists(User user);
    void createUserAndLogin(User user);
}
