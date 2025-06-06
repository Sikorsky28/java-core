package ru.mentee.power.exceptions.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigManagerTest {

    private ConfigManager configManager;
    private Map<String, String> testConfig;

    @BeforeEach
    void setUp() {
        testConfig = new HashMap<>();
        testConfig.put("server.port", "8080");
        testConfig.put("debug.mode", "true");
        testConfig.put("max.connections", "not_a_number");

        configManager = new ConfigManager(testConfig);
    }

    @Test
    @DisplayName("Должен успешно получить строковое значение по существующему ключу")
    void shouldGetStringValueWhenKeyExists() throws MissingConfigKeyException {
        String value = configManager.getRequiredValue("server.port");
        assertThat(value).isEqualTo("8080");
    }

    @Test
    @DisplayName("Должен выбросить MissingConfigKeyException при запросе несуществующего ключа")
    void shouldThrowMissingConfigKeyExceptionWhenKeyDoesNotExist() {
        assertThatThrownBy(() -> configManager.getRequiredValue("non.existent.key"))
                .isInstanceOf(MissingConfigKeyException.class);
    }

    @Test
    @DisplayName("Должен выбросить InvalidConfigValueException при запросе некорректного целочисленного значения")
    void shouldThrowInvalidConfigValueExceptionWhenIntValueIsInvalid() {
        assertThatThrownBy(() -> configManager.getRequiredIntValue("max.connections"))
                .isInstanceOf(InvalidConfigValueException.class);
    }
}
