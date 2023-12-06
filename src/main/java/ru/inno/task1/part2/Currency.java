package ru.inno.task1.part2;

public enum Currency {
    USD("доллар США"),
    EUR("евро"),
    JOY("Йена"),
    TRY("Лира"),
    AED("Дирхам"),
    RUB("Рубль");

    private final String name;

    Currency(String name) {
        this.name = name;
    }
}
