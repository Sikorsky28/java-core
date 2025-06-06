package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MissingConfigKeyExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением и ключом")
    void shouldCreateExceptionWithMessageAndKey() {
        String key = "database.url";
        String message = "Ключ конфигурации отсутствует: " + key;
        MissingConfigKeyException exception = new MissingConfigKeyException(message, key);

        assertThat(exception)
                .isInstanceOf(MissingConfigKeyException.class)
                .hasMessage(message);
        assertThat(exception.getMissingKey()).isEqualTo(key);
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением, ключом и причиной")
    void shouldCreateExceptionWithMessageKeyAndCause() {
        String key = "server.port";
        String message = "Ключ конфигурации отсутствует: " + key;
        Throwable cause = new IllegalArgumentException("Некорректный формат ключа");
        MissingConfigKeyException exception = new MissingConfigKeyException(message, key, cause);

        assertThat(exception)
                .isInstanceOf(MissingConfigKeyException.class)
                .hasMessage(message)
                .hasCause(cause);
        assertThat(exception.getMissingKey()).isEqualTo(key);
    }

    @Test
    @DisplayName("Должен вернуть ключ, который отсутствует в конфигурации")
    void shouldReturnMissingKey() {
        String key = "timeout";
        MissingConfigKeyException exception = new MissingConfigKeyException("Ключ отсутствует", key);

        assertThat(exception.getMissingKey()).isEqualTo(key);
    }
}