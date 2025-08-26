package ru.mentee.power.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты просмотрщика директорий (DirectoryViewer)")
class DirectoryViewerTest {

  @TempDir
  Path tempDir; // Временная директория для тестов

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  // Перехватываем стандартные потоки вывода и ошибок перед каждым тестом
  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  // Восстанавливаем стандартные потоки после каждого теста
  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  @DisplayName("Должен вывести содержимое непустой директории")
  void shouldListNonEmptyDirectoryContents() throws IOException {
    // Given: Создаем файлы и поддиректорию во временной папке
    Path textFile = tempDir.resolve("file1.txt");
    Files.writeString(textFile, "content1");
    Path imageFile = tempDir.resolve("image.jpg");
    Files.createFile(imageFile); // Пустой файл
    Path subDirectory = tempDir.resolve("subdir");
    Files.createDirectory(subDirectory);

    String[] args = {tempDir.toString()};

    // When: Запускаем main метод
    DirectoryViewer.main(args);

    // Then: Проверяем стандартный вывод
    String output = outContent.toString().replace("\r\n", "\n"); // Нормализуем переносы строк
    assertThat(output).contains("Содержимое директории: " + tempDir.toAbsolutePath());
    assertThat(output).contains("[FILE]  file1.txt (8 bytes)"); // Имя файла остается прежним
    assertThat(output).contains("[FILE]  image.jpg (0 bytes)");
    assertThat(output).contains("[DIR]  subdir");
    assertThat(errContent.toString()).isEmpty(); // Ошибок быть не должно
  }

  @Test
  @DisplayName("Должен вывести сообщение для пустой директории")
  void shouldHandleEmptyDirectory() {
    // Given: Временная директория пуста
    String[] args = {tempDir.toString()};

    // When: Запускаем main метод
    DirectoryViewer.main(args);

    // Then: Проверяем стандартный вывод
    String output = outContent.toString();
    assertThat(output).contains("Содержимое директории: " + tempDir.toAbsolutePath());
    assertThat(output).contains("Директория пуста.");
    assertThat(errContent.toString()).isEmpty();
  }

  @Test
  @DisplayName("Должен вывести ошибку, если путь не существует")
  void shouldPrintErrorForNonExistentPath() {
    // Given: Путь, который не существует
    Path nonExistentPath = tempDir.resolve("non_existent_dir");
    String[] args = {nonExistentPath.toString()};
    assertThat(nonExistentPath).doesNotExist();

    // When: Запускаем main метод
    DirectoryViewer.main(args);

    // Then: Проверяем вывод ошибок
    assertThat(errContent.toString()).contains("Ошибка: Путь не существует или не является директорией");
    assertThat(outContent.toString()).isEmpty(); // Стандартный вывод должен быть пуст
  }

  @Test
  @DisplayName("Должен вывести ошибку, если путь указывает на файл")
  void shouldPrintErrorForFilePath() throws IOException {
    // Given: Создаем файл и передаем путь к нему
    Path filePath = tempDir.resolve("my_file.txt");
    Files.createFile(filePath);
    String[] args = {filePath.toString()};
    assertThat(filePath).hasFileName("my_file.txt");

    // When: Запускаем main метод
    DirectoryViewer.main(args);

    // Then: Проверяем вывод ошибок
     assertThat(errContent.toString()).contains("Ошибка: Путь не существует или не является директорией");
    assertThat(outContent.toString()).isEmpty();
  }

  @Test
  @DisplayName("Должен вывести ошибку, если аргумент не предоставлен")
  void shouldPrintErrorWhenNoArgumentProvided() {
    // Given: Пустой массив аргументов
    String[] args = {};

    DirectoryViewer.main(args);

    // Then: Проверяем вывод ошибок
    assertThat(errContent.toString()).contains("Ошибка: Не указан путь к директории.");
    assertThat(outContent.toString()).isEmpty();
  }
}