package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением")
    void shouldCreateExceptionWithMessage() {
        String message = "Ошибка конфигурации";
        ConfigException exception = new ConfigException(message);

        assertThat(exception)
                .isInstanceOf(ConfigException.class)
                .hasMessage(message);
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением и причиной")
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "Ошибка конфигурации";
        Throwable cause = new IllegalArgumentException("Некорректное значение");
        ConfigException exception = new ConfigException(message, cause);

        assertThat(exception)
                .isInstanceOf(ConfigException.class)
                .hasMessage(message)
                .hasCause(cause);
    }
}
