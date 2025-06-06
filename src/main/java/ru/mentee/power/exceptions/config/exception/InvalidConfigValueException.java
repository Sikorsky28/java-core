package ru.mentee.power.exceptions.config.exception;

/**
 * Исключение, выбрасываемое при неверном формате значения в конфигурации.
 */
public class InvalidConfigValueException extends ConfigException {
    private final String key;
    private final String value;

    public InvalidConfigValueException(String message, String key, String value) {
        super(message);
        this.key = key;
        this.value = value;
    }

    public InvalidConfigValueException(String message, String key, String value, Throwable cause) {
        super(message, cause);
        this.key = key;
        this.value = value;
    }

    public String getInvalidKey() {
        return key;
    }

    public String getInvalidValue() {
        return value;
    }
}
