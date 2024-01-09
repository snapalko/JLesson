package ru.inno.task4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.inno.task4.aop.LoggingAspect;
import ru.inno.task4.dao.UserDaoImpl;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ru.inno.task4.services", "ru.inno.task4.repositories", "ru.inno.task4.repositories"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ProjectConfig {
    @Bean
    public LoggingAspect aspect() {
        return new LoggingAspect();
    }

    @Bean
    public DataSource dataSource() {
        return new SingleConnectionDataSource();
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    UserDaoImpl userDaoImpl() {
        return new UserDaoImpl();
    }
}
