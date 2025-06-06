package ru.mentee.power.exceptions.config.exception;

/**
 * Исключение, выбрасываемое при отсутствии обязательного ключа конфигурации.
 */
public class MissingConfigKeyException extends ConfigException {
    private final String key;

    public MissingConfigKeyException(String message, String key) {
        super(message);
        this.key = key;
    }

    public MissingConfigKeyException(String message, String key, Throwable cause) {
        super(message, cause);
        this.key = key;
    }

    public String getMissingKey() {
        return key;
    }
}
