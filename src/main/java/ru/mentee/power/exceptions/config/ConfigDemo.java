package ru.mentee.power.exceptions.config;

import ru.mentee.power.exceptions.config.exception.ConfigException;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.HashMap;
import java.util.Map;

/**
 * Демонстрация работы ConfigManager.
 */
public class ConfigDemo {
    public static void main(String[] args) {
        Map<String, String> config = new HashMap<>();
        config.put("database.url", "jdbc:mysql://localhost:3306/mydb");
        config.put("server.port", "8080");
        config.put("debug.mode", "true");
        config.put("max.connections", "not_a_number");

        ConfigManager manager = new ConfigManager(config);

        try {
            System.out.println("URL базы данных: " + manager.getRequiredValue("database.url"));
            System.out.println("Порт сервера: " + manager.getRequiredIntValue("server.port"));
            System.out.println("Режим отладки: " + manager.getRequiredBooleanValue("debug.mode"));

            System.out.println("Макс. соединений: " + manager.getRequiredIntValue("max.connections"));
        } catch (ConfigException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
