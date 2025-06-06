package ru.mentee.power.exceptions.config;

import ru.mentee.power.exceptions.config.exception.ConfigException;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.Map;
import java.util.HashMap;

/**
 * Класс для работы с конфигурационными параметрами.
 */
public class ConfigManager {
    private final Map<String, String> config;

    public ConfigManager(Map<String, String> config) {
        this.config = new HashMap<>(config);
    }

    public ConfigManager() {
        this.config = new HashMap<>();
    }

    public String getRequiredValue(String key) throws MissingConfigKeyException {
        if (!config.containsKey(key)) {
            throw new MissingConfigKeyException("Ключ отсутствует: " + key, key);
        }
        return config.get(key);
    }

    public int getRequiredIntValue(String key) throws MissingConfigKeyException, InvalidConfigValueException {
        String value = getRequiredValue(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidConfigValueException("Некорректное числовое значение для ключа: " + key, key, value, e);
        }
    }

    public boolean getRequiredBooleanValue(String key) throws MissingConfigKeyException, InvalidConfigValueException {
        String value = getRequiredValue(key);
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        } else {
            throw new InvalidConfigValueException("Некорректное булево значение для ключа: " + key, key, value);
        }
    }

    public void setValue(String key, String value) {
        config.put(key, value);
    }
}
