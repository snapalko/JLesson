package ru.inno.task4.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.inno.task4.config.ProjectConfig;
import ru.inno.task4.model.Model;
import ru.inno.task4.repositories.DBUserRepository;
import ru.inno.task4.services.SomeService;

public class MainApp {
//    public static String pathName = "C:\\AProjects\\task4\\src\\main\\resources";
       public static  String pathName = "D:\\Курсы (обучение)\\BaseCode\\Projects2\\Homework\\src\\main\\resources";

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var someService = context.getBean(SomeService.class);
        someService.doSomething();
    }
}
