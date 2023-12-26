package ru.inno.task4.services;

import org.springframework.stereotype.Component;
import ru.inno.task4.model.Model;
import ru.inno.task4.repositories.DBUserRepository;

@Component
public class SomeService {
    private final DataReader dataReader;
    private final DBUserRepository dbUserRepository;

    public SomeService(DataReader dataReader, DBUserRepository dbUserRepository) {
        this.dataReader = dataReader;
        this.dbUserRepository = dbUserRepository;
    }

    public void doSomething() {
        Model model = dataReader.get();

        // Делаем ФИО заглавными буквами
        UpperCaseOperation upperCaseOperation = new UpperCaseOperation();
        model.setUsersList(upperCaseOperation.apply(model.getUsersList()));

        // Проверяем тип приложения
        CheckTypeAppOperation cta = new CheckTypeAppOperation();
        model.setUsersList(cta.apply(model.getUsersList()));

        // Проверяем дату входа
        CheckDateOperation checkDateOperation = new CheckDateOperation();
        model.setUsersList(checkDateOperation.apply(model.getUsersList()));

        dbUserRepository.storeUser(model);
    }
}
