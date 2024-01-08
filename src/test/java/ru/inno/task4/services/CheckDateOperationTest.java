package ru.inno.task4.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.task4.model.Model;
import ru.inno.task4.model.User;

import java.util.List;

class CheckDateOperationTest {

    @Test
    @DisplayName("Тестируем метод, который проверяет в правильном ли формате передана дата")
    void accept_correctDateFormat() {
        Model model = new Model();

        User user = new User("Петров Петр Петрович", "31/10/2023");
        model.setUsersList(List.of(user));

        // Производим проверку: дата указана в правильном формате
        CheckDateOperation ckDt = new CheckDateOperation();
        ckDt.accept(model);
        // в этом случае массив model.usersList будет содержать добавленного пользователя
        Assertions.assertEquals(1, model.getUsersList().size());
    }
    @Test
    @DisplayName("Тестируем метод, который проверяет в правильном ли формате передана дата, дату передаём в неправильном формате")
    void accept_notCorrectDateFormat() {
        Model model = new Model();

        User user = new User("Иванов Иван Иванович", "19/13/2023");
        model.setUsersList(List.of(user));

        // Производим проверку: дата указана в НЕправильном формате
        CheckDateOperation ckDt = new CheckDateOperation();
        ckDt.accept(model);
        // в этом случае массив model.usersList будет пустой
        Assertions.assertEquals(0, model.getUsersList().size());
    }
}