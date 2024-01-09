package ru.inno.task4.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.inno.task4.model.User;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
public class UserDaoImpl implements UserDAO {

    private NamedParameterJdbcTemplate template;

    @Autowired
    public void init(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void checkConnection() {
        String sql = "SELECT version()";
        try {
            String res = template.queryForObject(sql, new MapSqlParameterSource(), String.class);
        } catch (DataAccessException e) {
            log.error("Ошибка подключения к базе данных! {}", e.getMessage());
            throw new RuntimeException("Ошибка подключения к базе данных! " + e.getMessage());
        }
    }

    @Override
    public Long createUser(User user) {
        String sql = "INSERT INTO Users (username, fio) VALUES (:username, :fio) RETURNING ID";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("username", user.getUserName())
                .addValue("fio", user.getFio());
        return template.queryForObject(sql, parameterSource, Long.class);
    }

    @Override
    public Long createLogin(User user) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);

        Date accessDate;
        try {
            accessDate = formatter.parse(user.getAccessDate());
        } catch (ParseException e) {
            log.error("Не правильный формат даты!");
            throw new RuntimeException("Не правильный формат даты!");
        }

        String sql = "INSERT INTO Logins (access_date, user_id, application) " +
                "VALUES (:access_date, :user_id, :application) RETURNING ID";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("access_date", accessDate)
                .addValue("user_id", user.getId())
                .addValue("application", user.getApplication());
        return template.queryForObject(sql, parameterSource, Long.class);
    }

    @Override
    public boolean userExists(User user) {
        int count = getCountOfUserByLogin(user.getUserName());
        return count > 0;
    }

    @Override
    public Integer getCountOfUserByLogin(String login) {
        String sql = "SELECT count(1) as total FROM Users WHERE username = :username";
        SqlParameterSource parameterSource = new MapSqlParameterSource("username", login);
        return template.queryForObject(sql, parameterSource, Integer.class);
    }
}


