package ru.inno.task4.dao;

import ru.inno.task4.model.User;

public interface UserDAO {
    void checkConnection();
    Long createUser(User user);
    Long createLogin(User user);
    Integer getCountOfUserByLogin(String login);
    boolean userExists(User user);
}
