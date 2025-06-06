package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidConfigValueExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением, ключом и значением")
    void shouldCreateExceptionWithMessageKeyAndValue() {
        String key = "max.connections";
        String value = "not_a_number";
        String message = "Некорректное значение для ключа: " + key;

        InvalidConfigValueException exception = new InvalidConfigValueException(message, key, value);

        assertThat(exception)
                .isInstanceOf(InvalidConfigValueException.class)
                .hasMessage(message);
        assertThat(exception.getInvalidKey()).isEqualTo(key);
        assertThat(exception.getInvalidValue()).isEqualTo(value);
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением, ключом, значением и причиной")
    void shouldCreateExceptionWithMessageKeyValueAndCause() {
        String key = "timeout";
        String value = "invalid_timeout";
        String message = "Некорректное значение для ключа: " + key;
        Throwable cause = new IllegalArgumentException("Некорректный формат");

        InvalidConfigValueException exception = new InvalidConfigValueException(message, key, value, cause);

        assertThat(exception)
                .isInstanceOf(InvalidConfigValueException.class)
                .hasMessage(message)
                .hasCause(cause);
        assertThat(exception.getInvalidKey()).isEqualTo(key);
        assertThat(exception.getInvalidValue()).isEqualTo(value);
    }

    @Test
    @DisplayName("Должен вернуть ключ, для которого значение некорректно")
    void shouldReturnKey() {
        String key = "server.port";
        InvalidConfigValueException exception = new InvalidConfigValueException("Ошибка", key, "invalid_port");

        assertThat(exception.getInvalidKey()).isEqualTo(key);
    }

    @Test
    @DisplayName("Должен вернуть некорректное значение")
    void shouldReturnInvalidValue() {
        String value = "wrong_format";
        InvalidConfigValueException exception = new InvalidConfigValueException("Ошибка", "database.url", value);

        assertThat(exception.getInvalidValue()).isEqualTo(value);
    }
}
