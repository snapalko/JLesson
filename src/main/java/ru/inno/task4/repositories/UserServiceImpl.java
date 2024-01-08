package ru.inno.task4.repositories;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.task4.dao.UserDAO;
import ru.inno.task4.model.User;

@Component
@Log4j2
public class UserServiceImpl implements UserService {
//    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }
    @Override
    public boolean userExists(User user) {
        return dao.userExists(user);
    }

    @Override
    @Transactional
    public void createUserAndLogin(User user) {
        Long user_id = dao.createUser(user);
        user.setId(user_id);
        if (user.getUserName().equals("login_5")) {
            method();
        }
        dao.createLogin(user);
    }

    void method() throws RuntimeException {
        log.error("мой Exception! Транзакция с добавлением login_5 должна откатиться");
        throw new RuntimeException("мой Exception!");
    }
}
