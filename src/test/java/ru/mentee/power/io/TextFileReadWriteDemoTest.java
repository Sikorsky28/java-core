package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.charset.StandardCharsets; // Указываем кодировку для надежности
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты записи и чтения строк (TextFileReadWriteDemo)")
class TextFileReadWriteDemoTest {

  @TempDir
  Path tempDir;

  Path testFilePath;
  List<String> lines = List.of("Строка номер раз", "Line two", "И третья строка.");

  @BeforeEach
  void setUp() {
    testFilePath = tempDir.resolve("test_lines.txt");
  }

  // Вспомогательный метод для записи строк (как в задании)
  private void writeLines(Path path, List<String> linesToWrite) throws IOException {
    try (FileWriter writer = new FileWriter(path.toFile(), StandardCharsets.UTF_8)) { // Явно укажем UTF-8
      for (String line : linesToWrite) {
        writer.write(line);
        writer.write(System.lineSeparator());
      }
    }
  }

  // Вспомогательный метод для чтения посимвольно (как в задании)
  private String readChars(Path path) throws IOException {
    StringBuilder content = new StringBuilder();
    try (FileReader reader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
      int charCode;
      while ((charCode = reader.read()) != -1) {
        content.append((char) charCode);
      }
    }
    return content.toString();
  }

  @Test
  @DisplayName("Должен корректно записать и прочитать несколько строк")
  void shouldWriteAndReadLinesCorrectly() throws IOException {
    // Given
    List<String> linesToWrite = List.of("Строка 1", "Line 2", "Третья строка!");

    // When: Записываем строки
    writeLines(testFilePath, linesToWrite);

    // Then: Проверяем записанный файл
    assertThat(testFilePath).exists();
    assertThat(Files.readAllLines(testFilePath, StandardCharsets.UTF_8)).isEqualTo(linesToWrite);

    // When: Читаем файл посимвольно
    String readContent = readChars(testFilePath);

    // Then: Проверяем прочитанное содержимое
    String expectedContent = String.join(System.lineSeparator(), linesToWrite) + System.lineSeparator();
    assertThat(readContent).isEqualTo(expectedContent);

  }

  @Test
  @DisplayName("Должен корректно обработать запись пустого списка строк")
  void shouldHandleEmptyListWrite() throws IOException {
    // Given
    List<String> emptyList = new ArrayList<>();

    // When: Записываем пустой список
    writeLines(testFilePath, emptyList);

    // Then: Проверяем файл
    assertThat(testFilePath).exists();
    assertThat(testFilePath).isEmptyFile();

    // When: Читаем файл
    String readContent = readChars(testFilePath);

    // Then: Проверяем прочитанное
    assertThat(readContent).isEmpty();
  }

  @Test
  @DisplayName("Должен корректно записать и прочитать строки с различными символами")
  void shouldWriteAndReadSpecialChars() throws IOException {
    // Given
    List<String> specialLines = List.of(
        "Табуляция:\tсимволы",
        "Новая\\nСтрока", // Экранированный перенос
        "Символы:!@#$%^&*()_+=-`~"
    );

    // When: Записываем
    writeLines(testFilePath, specialLines);

    // Then: Проверяем записанный файл
    assertThat(testFilePath).exists();
    assertThat(Files.readAllLines(testFilePath, StandardCharsets.UTF_8)).isEqualTo(specialLines);

    // When: Читаем
    String readContent = readChars(testFilePath);

    // Then: Проверяем прочитанное
    String expectedContent = String.join(System.lineSeparator(), specialLines) + System.lineSeparator();
    assertThat(readContent).isEqualTo(expectedContent);
  }
}