package ru.inno.task4.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.inno.task4.main.MainApp;
import ru.inno.task4.model.Model;
import ru.inno.task4.model.User;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Supplier;

@Component
@Log4j2
public class DataReader implements Supplier<Model> {
    private final String pathName = MainApp.pathName;

    @Override
    public Model get() {
        Model model = new Model();

        try {
            readFiles(model);
        } catch (NoSuchFileException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // Из считанных из файлов строк создаем массив объектов User
        model.setUsersList(fillUsersList(model.getStringList()));
        return model;
    }

    private void readFiles(Model model) throws NoSuchFileException {
        File dir = new File(this.pathName);
        if (!dir.exists()) {
            log.error("Не найден путь, где находятся файлы!");
            throw new NoSuchFileException("Не найден путь, где находятся файлы!");
        }

        if (dir.isDirectory()) {
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                if (!item.isDirectory()) {
                    log.debug(item.getName());
                    if (item.getName().contains("user")) {
                        model.getStringList().addAll(readFile(item));
                    }
                }
            }
            if (model.getStringList().isEmpty()) {
                log.error("В указанной папке нет файлов для чтения!");
                throw new NoSuchFileException("В указанной папке нет файлов для чтения!");
            }
        }
    }

    private List<String> readFile(File file) {
        List<String> list = new ArrayList<>();
        if (file.exists()) {
            try (FileReader fr = new FileReader(file); Scanner scan = new Scanner(fr)) {
                while (scan.hasNextLine()) {
                    list.add(scan.nextLine());
                }
            } catch (IOException e) {
                log.error("Ошибка чтения файла {}", file.getName());
                throw new RuntimeException("Ошибка чтения файла " + file.getName());
            }
        }
        return list;
    }

    private List<User> fillUsersList(List<String> stringList) {
        List<User> usersList = new ArrayList<>();

        for (String s : stringList) {
            User user = new User();
            String[] str = s.split(" ");

            user.setUserName(str[0]);
            user.setLastName(str[1]);
            user.setFirstName(str[2]);
            user.setMidlName(str[3]);
            user.setFio(user.getLastName() + " " + user.getFirstName() + " " + user.getMidlName());
            user.setAccessDate(str[4]);
            user.setApplication(str[5]);

            usersList.add(user);
        }

        return usersList;
    }
}
