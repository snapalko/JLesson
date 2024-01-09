package ru.inno.task4.main;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.inno.task4.config.ProjectConfig;
import ru.inno.task4.model.Model;
import ru.inno.task4.services.CredentialForDb;
import ru.inno.task4.services.MainService;

@Log4j2
public class MainApp {
    //    public static String pathName = "C:\\AProjects\\task4\\src\\main\\resources";
    public static String pathName = "D:\\Курсы (обучение)\\BaseCode\\Projects2\\Homework\\src\\main\\resources";
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        CredentialForDb cr = context.getBean(CredentialForDb.class);
        log.debug("Получили bean CredentialForDb: {}", cr);

        MainService mainService = context.getBean(MainService.class);
        log.debug("Получили bean mainService: {}", mainService);

        log.info("Запрос логина и пароля для подключения к базе данных");
        mainService.setCredential(cr);

        log.info("Читаем данные из текстовых файлов user*.txt");
        Model model = mainService.readDataFromFiles();

        log.info("Делаем ФИО заглавными буквами");
        mainService.setUpperCaseOperation(model);

        log.info("Проверяем дату входа");
        mainService.checkDateOper(model);

        log.info("Проверяем значения поля 'тип приложения'");
        mainService.checkTypeAppOperation(model);

        log.info("Создаем пользователей в базе данных");
        mainService.addUsersAndLogins(model);
    }
}

