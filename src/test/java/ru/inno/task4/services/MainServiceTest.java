package ru.inno.task4.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.inno.task4.model.Model;
import ru.inno.task4.model.User;
import ru.inno.task4.repositories.UserService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainServiceTest {
    @Mock
    UserService userService;

    @Test
    @DisplayName("Создание пользователя и логина в БД, при условии, что такого пользователя нет в БД")
    void addUsersAndLoginsHappyFlow() {
        MainService mainService = new MainService();
        mainService.setUserService(userService);

        Model model = new Model();
        User user = new User(1, "login_1", "Иванов И.И.");
        model.setUsersList(List.of(user));

        // Предпологаем, что такого пользователя нет в БД
        given(userService.userExists(user)).willReturn(false);
        //        when(userService.userExists(user)).thenReturn(true);
        Assertions.assertFalse(userService.userExists(user));
        // Пользователь должен добавиться в БД и написать об этом
        mainService.addUsersAndLogins(model);
        verify(userService).createUserAndLogin(user);
    }
    @Test
    @DisplayName("Создание пользователя и логина в БД, при условии, что такой пользователь уже есть в БД")
    void addUsersAndLoginsNotHappyFlow() {
        MainService mainService = new MainService();
        mainService.setUserService(userService);

        Model model = new Model();
        User user = new User(1, "login_3", "Иванов И.И.");
        model.setUsersList(List.of(user));

        // Предпологаем, что такой пользователь уже есть в БД
        given(userService.userExists(user)).willReturn(true);

        Assertions.assertTrue(userService.userExists(user));
        // Пользователь НЕ должен добавиться в БД, сообщив, что такой пользователь уже существует
        mainService.addUsersAndLogins(model);
        verify(userService, never()).createUserAndLogin(user);
    }
}