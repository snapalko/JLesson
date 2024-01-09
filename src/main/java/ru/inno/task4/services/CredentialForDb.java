package ru.inno.task4.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import ru.inno.task4.dao.UserDAO;

import java.util.Objects;
import java.util.Scanner;

@Component
@Log4j2
public class CredentialForDb implements CredentialFor {

    @Autowired
    private UserDAO dao;

    @Autowired
    SingleConnectionDataSource dataSource;

    private final boolean isTest = true;

    public void setCredential() {
        String databaseURL = "jdbc:postgresql://46.29.236.5:5432/postgres";
        String userName = null;
        String password = null;

        if (!isTest) {
            Scanner in = new Scanner(System.in);
            System.out.println("Подключение к PostgreSQL...");
            System.out.print("Введите логин: ");
            if (in.hasNextLine()) {
                userName = in.nextLine();
            }

            System.out.print("Введите пароль: ");
            if (in.hasNextLine()) {
                password = in.nextLine();
            }
            if (!Objects.equals(password, "123"))
                log.error("Не правильно введен пароль для базы данных!");
            throw new RuntimeException("Не правильно введен пароль для базы данных!");
        } else {
            userName = "nsn";
            password = "123";
        }

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(databaseURL);
        dataSource.setSchema("proba");
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        try {
            dao.checkConnection();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            System.exit(1);
        }

        log.info("Подключение к PostgreSQL... - Ok");
    }
}
