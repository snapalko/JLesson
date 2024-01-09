package ru.inno.task4.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Model {
    @Setter
    private List<String> stringList = new ArrayList<>();

    @Setter
    private List<User> usersList = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s, в которой List<User> usersList = %s", super.toString(), Collections.singletonList(usersList));
    }
}
