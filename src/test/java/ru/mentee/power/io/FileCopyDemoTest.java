package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileInputStream; // Используем потоки напрямую для теста
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets; // Для проверки содержимого


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DisplayName("Тесты копирования файла (FileCopyDemo)")
class FileCopyDemoTest {

  @TempDir
  Path tempDir; // Временная директория

  Path sourcePath;
  Path destinationPath;
  String sourceContent = "Строка 1 для копирования.\\nLine 2 with English.\\nИ русские буквы.";

  @BeforeEach
  void setUp() throws IOException {
    // Готовим пути внутри временной директории
    sourcePath = tempDir.resolve("source_test.txt");
    destinationPath = tempDir.resolve("dest_test.txt");
    // Создаем исходный файл для большинства тестов
    Files.writeString(sourcePath, sourceContent, StandardCharsets.UTF_8);
    assertThat(sourcePath).exists(); // Проверка предусловия
  }

  // Вспомогательный метод для выполнения логики копирования
  private void performCopy(Path source, Path dest) throws IOException {
    try (FileInputStream fis = new FileInputStream(source.toFile());
         FileOutputStream fos = new FileOutputStream(dest.toFile())) {
      byte[] buffer = new byte[1024]; // Используем буфер как в задании
      int bytesRead;
      while ((bytesRead = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesRead);
      }
    }
  }

  @Test
  @DisplayName("Должен успешно копировать непустой файл")
  void shouldCopyNonEmptyFile() throws IOException {
    // Given: Файл создан в setUp
    assertThat(destinationPath).doesNotExist();

    // When: Выполняем копирование
    performCopy(sourcePath, destinationPath);

    // Then: Проверяем результат
    assertThat(destinationPath).exists();
    assertThat(Files.readString(destinationPath, StandardCharsets.UTF_8)).isEqualTo(sourceContent);
  }

  @Test
  @DisplayName("Должен успешно копировать пустой файл")
  void shouldCopyEmptyFile() throws IOException {
    // Given: Создаем пустой исходный файл (перезаписываем созданный в setUp)
    Path emptySource = tempDir.resolve("empty_source.txt");
    Files.deleteIfExists(emptySource); // На случай повторного запуска
    Files.createFile(emptySource);
    assertThat(emptySource).isEmptyFile();
    Path emptyDest = tempDir.resolve("empty_dest.txt");
    assertThat(emptyDest).doesNotExist();

    // When: Копируем пустой файл
    performCopy(emptySource, emptyDest);

    // Then: Проверяем результат
    assertThat(emptyDest).exists();
    assertThat(emptyDest).isEmptyFile();
  }

  @Test
  @DisplayName("Должен перезаписать существующий целевой файл")
  void shouldOverwriteExistingDestinationFile() throws IOException {
    // Given: Целевой файл уже существует с другим содержимым
    String initialDestContent = "Этот текст будет перезаписан.";
    Files.writeString(destinationPath, initialDestContent, StandardCharsets.UTF_8);
    assertThat(destinationPath).hasContent(initialDestContent);

    // When: Выполняем копирование
    performCopy(sourcePath, destinationPath);

    // Then: Проверяем, что целевой файл перезаписан
    assertThat(destinationPath).exists();
    assertThat(destinationPath).hasContent(sourceContent);

  }


  @Test
  @DisplayName("Должен выбросить IOException, если исходный файл не существует")
  void shouldThrowIOExceptionIfSourceDoesNotExist() {
    // Given: Исходный файл не существует
    Path nonExistentSource = tempDir.resolve("non_existent.txt");
    assertThat(nonExistentSource).doesNotExist();

    // When: Пытаемся выполнить копирование
    Throwable thrown = catchThrowable(() -> performCopy(nonExistentSource, destinationPath));

    // Then: Ожидаем исключение и проверяем, что целевой файл не создался
    assertThat(thrown).isInstanceOf(IOException.class);
    assertThat(destinationPath).doesNotExist();
  }
}