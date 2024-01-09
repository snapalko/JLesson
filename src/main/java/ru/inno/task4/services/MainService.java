package ru.inno.task4.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.task4.aop.LogTransformation;
import ru.inno.task4.model.Model;
import ru.inno.task4.model.User;
import ru.inno.task4.repositories.UserService;

@Log4j2
@Service
public class MainService {
    @Autowired
    private DataReader dataReader;
    @Autowired
    private UserService userService;

    // Setter добавлен для тестов!
   public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @LogTransformation
    public void setCredential(CredentialForDb cr) {
        cr.setCredential();
    }

    @LogTransformation
    public Model readDataFromFiles() {
        log.debug("userService: {}", userService);
        return dataReader.get();
    }

    public void setUpperCaseOperation(Model model) {
        UpperCaseOperation upperCaseOperation = new UpperCaseOperation();
        upperCaseOperation.accept(model);
    }

    @LogTransformation
    public void checkDateOper(Model model) {
        CheckDateOperation checkDateOperation = new CheckDateOperation();
        checkDateOperation.accept(model);
    }

    public void checkTypeAppOperation(Model model) {
        CheckTypeAppOperation cta = new CheckTypeAppOperation();
        cta.accept(model);
    }

    @LogTransformation
    public void addUsersAndLogins(Model model) {
        for (User user : model.getUsersList()) {
            if (!userService.userExists(user)) {
                try {
                    userService.createUserAndLogin(user);
                    log.info("Пользователь {} добавлен в БД", user.toString());
                } catch (RuntimeException e) {
                    log.error("Ошибка при добавлении пользователя {} в БД", user.toString());
                }
            } else {
                log.warn("Пользователь с логином {} уже существует в БД", user.getUserName());
            }
        }
    }
}
