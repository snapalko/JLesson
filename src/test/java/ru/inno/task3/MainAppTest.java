package ru.inno.task3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainAppTest {

    @Test
    @DisplayName("Проверка работы кэшированого метода, а так же, была ли очистка устаревших записей из кэша")
    void main() throws InterruptedException {
        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);
        // Метод ещё не вызывался - срабатывает вызов самого метода
        num.doubleValue();
        Assertions.assertFalse(ru.inno.task3.Utils.isValueFromCache); // Значение получено обычным способом
        // теперь значение берется из кэша
        num.doubleValue();
        Assertions.assertTrue(ru.inno.task3.Utils.isValueFromCache); // Значение взято из кэша

        for (int i = 3; i < 7; i++) {
            // Меняем состояние объекта
            num.setNum(i);        System.out.println("-- вызов num.setNum("+i+")");
            // срабатывает вызов самого метода
            num.doubleValue();
            Assertions.assertFalse(ru.inno.task3.Utils.isValueFromCache); // Значение получено обычным способом
            // значение берется из кэша
            num.doubleValue();
            Assertions.assertTrue(ru.inno.task3.Utils.isValueFromCache); // Значение взято из кэша
            System.out.println("засыпаем на 1.5 сек...");
            Thread.sleep(1500);
        }

        // Проверяем, что за всё это время чистился кэш: если Utils.clearedData не пустая, значит кэш чистился
        // и сюда попадали, те записи, которые были удалены из кэша.
        Assertions.assertTrue(!Utils.clearedData.isEmpty());
        // Строки удалённые в процессе чистки кэша:
        System.out.println(Utils.viewClearedData());
    }
}