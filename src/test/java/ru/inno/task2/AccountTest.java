package ru.inno.task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class AccountTest {

    @Test
    @DisplayName("проверка работы кэшированого метода")
    void getCacheName() {
        Account account = new Account("Vasa");
        MyInterface mi = Utils.cache(account);

        mi.setName("Pete");

        mi.getName();
        Assertions.assertFalse(Utils.isValueFromCache); // Значение получено обычным способом
        mi.getName();
        Assertions.assertTrue(Utils.isValueFromCache); // Значение взято из кэша
        mi.getName();
        Assertions.assertTrue(Utils.isValueFromCache); // Значение взято из кэша

        mi.setName("John");
        mi.getName();
        Assertions.assertFalse(Utils.isValueFromCache); // Значение получено обычным способом
        mi.getName();
        Assertions.assertTrue(Utils.isValueFromCache); // Значение взято из кэша
    }

    @Test
    @DisplayName("проверка что у метода account.setName() есть аннотация Mutator")
    void setName() {
        Account account = new Account("Vasa");

        Method m;
        try {
            m = account.getClass().getDeclaredMethod("setName", String.class);

        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotNull(m.getAnnotation(Mutator.class));
    }
}