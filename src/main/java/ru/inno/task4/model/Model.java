package ru.inno.task4.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final List<String> stringList = new ArrayList<>();

    public List<String> getStringList() {
        return stringList;
    }

    private List<User> usersList = new ArrayList<>();

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}
