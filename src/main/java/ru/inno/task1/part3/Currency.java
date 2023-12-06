package ru.inno.task1.part3;

public enum Currency {
    USD("доллар США"),
    EUR("евро"),
    JOY("Йена"),
    TRY("Лира"),
    AED("Дирхам"),
    RUB("Рубль");

    private String name;

    Currency(String name) {
        this.name = name;
    }

}
