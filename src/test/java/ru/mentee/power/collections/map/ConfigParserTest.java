package ru.mentee.power.collections.map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class ConfigParserTest {

    @Test
    @DisplayName("Метод setValue должен добавлять и возвращать предыдущее значение")
    void shouldSetValueAndReturnPreviousValue() {
        ConfigParser parser = new ConfigParser();

        assertThat(parser.setValue("host", "localhost")).isNull();
        assertThat(parser.setValue("port", "8080")).isNull();

        assertThat(parser.setValue("host", "127.0.0.1")).isEqualTo("localhost");
        assertThat(parser.getValue("host")).isEqualTo("127.0.0.1");
    }

    @Test
    @DisplayName("Метод setValue должен выбрасывать исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInSetValue() {
        ConfigParser parser = new ConfigParser();

        assertThatThrownBy(() -> parser.setValue(null, "value"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> parser.setValue("key", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод getValue должен возвращать значение по ключу")
    void shouldGetValueByKey() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");
        parser.setValue("port", "8080");

        assertThat(parser.getValue("host")).isEqualTo("localhost");
        assertThat(parser.getValue("port")).isEqualTo("8080");
        assertThat(parser.getValue("missingKey")).isNull();
    }

    @Test
    @DisplayName("Метод getValue должен возвращать defaultValue, если ключ не найден")
    void shouldReturnDefaultValueIfKeyNotFound() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");

        assertThat(parser.getValue("missingKey", "default")).isEqualTo("default");
        assertThat(parser.getValue("host", "default")).isEqualTo("localhost");
    }

    @Test
    @DisplayName("Метод getValue с defaultValue должен выбрасывать исключение при null ключе")
    void shouldThrowExceptionForNullKeyInGetValueWithDefault() {
        ConfigParser parser = new ConfigParser();

        assertThatThrownBy(() -> parser.getValue(null, "default"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод removeKey должен удалять ключ и возвращать true, если ключ существовал")
    void shouldRemoveKeyAndReturnTrueIfKeyExists() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");
        parser.setValue("port", "8080");

        assertTrue(parser.removeKey("host"));
        assertFalse(parser.containsKey("host"));
        assertEquals(1, parser.size());
    }

    @Test
    @DisplayName("Метод removeKey должен возвращать false, если ключ не существовал")
    void shouldReturnFalseIfKeyDidNotExist() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");

        assertFalse(parser.removeKey("missingKey"));
        assertEquals(1, parser.size());
    }

    @Test
    @DisplayName("Метод containsKey должен корректно проверять наличие ключа")
    void shouldCheckIfKeyExists() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");

        assertTrue(parser.containsKey("host"));
        assertFalse(parser.containsKey("missingKey"));
    }

    @Test
    @DisplayName("Метод getKeys должен возвращать все ключи")
    void shouldReturnAllKeys() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");
        parser.setValue("port", "8080");

        Set<String> keys = parser.getKeys();
        assertThat(keys).containsExactlyInAnyOrder("host", "port");
    }

    @Test
    @DisplayName("Метод getAll должен возвращать копию всех пар ключ-значение")
    void shouldReturnCopyOfAllEntries() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");
        parser.setValue("port", "8080");

        Map<String, String> allEntries = parser.getAll();

        assertThat(allEntries).hasSize(2);
        assertThat(allEntries).containsEntry("host", "localhost");
        assertThat(allEntries).containsEntry("port", "8080");

        // Проверяем, что это копия, а не ссылка на внутренний map
        allEntries.put("newKey", "newValue");
        assertFalse(parser.containsKey("newKey"));
    }

    @Test
    @DisplayName("Метод getIntValue должен корректно парсить целые числа")
    void shouldParseIntegerValues() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("port", "8080");

        int port = parser.getIntValue("port");
        assertEquals(8080, port);
    }

    @Test
    @DisplayName("Метод getIntValue должен выбрасывать исключение, если значение не является числом")
    void shouldThrowExceptionIfValueIsNotANumber() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("port", "notANumber");

        assertThatThrownBy(() -> parser.getIntValue("port"))
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("Метод getBooleanValue должен корректно распознавать логические значения")
    void shouldParseBooleanValues() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("flagTrue1", "true");
        parser.setValue("flagTrue2", "YES");
        parser.setValue("flagTrue3", "1");
        parser.setValue("flagFalse1", "false");
        parser.setValue("flagFalse2", "no");
        parser.setValue("flagFalse3", "0");


        assertTrue(parser.getBooleanValue("flagTrue1"));
        assertTrue(parser.getBooleanValue("flagTrue2"));
        assertTrue(parser.getBooleanValue("flagTrue3"));
        assertFalse(parser.getBooleanValue("flagFalse1"));
        assertFalse(parser.getBooleanValue("flagFalse2"));
        assertFalse(parser.getBooleanValue("flagFalse3"));

    }

    @Test
    @DisplayName("Метод getListValue должен разбивать строку на список по запятым")
    void shouldSplitStringByCommas() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("colors", "red,green,blue");

        List<String> expected = Arrays.asList("red", "green", "blue");
        List<String> result = parser.getListValue("colors");

        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод getListValue должен возвращать пустой список для несуществующего ключа")
    void shouldReturnEmptyListForNonExistentKey() {
        ConfigParser parser = new ConfigParser();

        List<String> result = parser.getListValue("missingKey");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Метод parseConfig должен корректно парсить конфигурационную строку")
    void shouldParseConfigString() {
        String config = "# Это комментарий\nhost=localhost\nport=8080\n\ndebug=true";

        ConfigParser parser = new ConfigParser();
        parser.parseConfig(config);

        assertThat(parser.size()).isEqualTo(3);
        assertThat(parser.getValue("host")).isEqualTo("localhost");
        assertThat(parser.getValue("port")).isEqualTo("8080");
        assertThat(parser.getValue("debug")).isEqualTo("true");
    }

    @Test
    @DisplayName("Метод parseConfig должен выбрасывать исключение при null аргументе")
    void shouldThrowExceptionWhenConfigStringIsNull() {
        ConfigParser parser = new ConfigParser();

        assertThatThrownBy(() -> parser.parseConfig(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод toConfigString должен корректно преобразовывать конфигурацию в строку")
    void shouldConvertConfigToString() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");
        parser.setValue("port", "8080");

        String result = parser.toConfigString();

        assertThat(result).contains("host=localhost");
        assertThat(result).contains("port=8080");
        assertThat(result).contains("\n");
    }

    @Test
    @DisplayName("Метод clear должен удалять все пары ключ-значение")
    void shouldClearAllEntries() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");
        parser.setValue("port", "8080");

        parser.clear();

        assertThat(parser.size()).isEqualTo(0);
        assertFalse(parser.containsKey("host"));
        assertFalse(parser.containsKey("port"));
    }

    @Test
    @DisplayName("Метод size должен возвращать правильное количество пар")
    void shouldReturnCorrectSize() {
        ConfigParser parser = new ConfigParser();

        assertEquals(0, parser.size());

        parser.setValue("host", "localhost");
        assertEquals(1, parser.size());

        parser.setValue("port", "8080");
        assertEquals(2, parser.size());

        parser.removeKey("host");
        assertEquals(1, parser.size());
    }
}
