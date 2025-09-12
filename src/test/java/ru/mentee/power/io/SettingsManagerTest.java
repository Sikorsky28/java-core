package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.mentee.power.io.SettingsManager.loadSettings;
import static ru.mentee.power.io.SettingsManager.saveSettings;

@DisplayName("Тесты менеджера настроек (SettingsManager)")
class SettingsManagerTest {

  @TempDir
  Path tempDir;

  private Path testFilePath;
  private List<Serializable> testSettingsList;
  private ServerConfiguration testServerConfig;
  private ServerConfiguration.WindowConfiguration testWindowConfig;

  @BeforeEach
  void setUp() {
    testFilePath = tempDir.resolve("test_settings.ser");

    testServerConfig = new ServerConfiguration("test.server", 1234, true);
    testServerConfig.setLastStatus("Initial"); // transient поле
    testWindowConfig = new ServerConfiguration.WindowConfiguration("Test Window", 800, 600);

    testSettingsList = new ArrayList<>();
    testSettingsList.add(testServerConfig);
    testSettingsList.add(testWindowConfig);
  }

  @Test
  @DisplayName("Должен сохранять и загружать список с разными типами Serializable")
  void shouldSaveAndLoadListOfMixedSerializable() throws IOException, ClassNotFoundException {
    // When
    saveSettings(testSettingsList, testFilePath.toString());
    List<Serializable> loadedList = loadSettings(testFilePath.toString());

    // Then
    assertThat(loadedList).isNotNull();
    assertThat(loadedList).hasSize(testSettingsList.size());

    // Первый элемент — ServerConfiguration
    assertThat(loadedList.get(0))
        .isInstanceOf(ServerConfiguration.class)
        .usingRecursiveComparison()
        .ignoringFields("lastStatus") // transient не сравниваем
        .isEqualTo(testServerConfig);

    // Проверка transient-поля
    ServerConfiguration loadedServerConfig = (ServerConfiguration) loadedList.get(0);
    assertThat(loadedServerConfig.getLastStatus()).isNull();

    // Второй элемент — WindowConfiguration
    assertThat(loadedList.get(1))
        .isInstanceOf(ServerConfiguration.WindowConfiguration.class)
        .usingRecursiveComparison()
        .isEqualTo(testWindowConfig);
  }

  @Test
  @DisplayName("Должен сохранять и загружать пустой список")
  void shouldSaveAndLoadEmptyList() throws IOException, ClassNotFoundException {
    // Given
    List<Serializable> emptyList = new ArrayList<>();

    // When
    saveSettings(emptyList, testFilePath.toString());
    List<Serializable> loadedList = loadSettings(testFilePath.toString());

    // Then
    assertThat(loadedList).isNotNull().isEmpty();
  }

  @Test
  @DisplayName("loadSettings должен возвращать пустой список, если файл не найден")
  void loadShouldReturnEmptyListWhenFileNotExists() {
    // Given: файла нет
    assertThat(testFilePath).doesNotExist();

    // When
    List<Serializable> loadedList = loadSettings(testFilePath.toString());

    // Then
    assertThat(loadedList).isNotNull().isEmpty();
  }

  @Test
  @DisplayName("loadSettings должен возвращать пустой список при ошибке десериализации")
  void loadShouldReturnEmptyListOnDeserializationError() throws IOException {
    // Given: создаём файл с "мусором"
    Files.writeString(testFilePath, "Это не сериализованный список");

    // When
    List<Serializable> loadedList = loadSettings(testFilePath.toString());

    // Then
    assertThat(loadedList).isNotNull().isEmpty();
  }
}
