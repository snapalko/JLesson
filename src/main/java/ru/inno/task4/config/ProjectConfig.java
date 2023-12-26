package ru.inno.task4.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ru.inno.task4.services", "ru.inno.task4.repositories"})
public class ProjectConfig {
}
