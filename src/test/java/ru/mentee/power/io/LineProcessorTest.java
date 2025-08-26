package ru.mentee.power.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты обработчика строк (LineProcessor)")
class LineProcessorTest {

  @TempDir
  Path tempDir;

  private Path inputFile;
  private Path outputFile;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  void setUp() {
    inputFile = tempDir.resolve("input_test.txt");
    outputFile = tempDir.resolve("output_test.txt");
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  // Вспомогательный метод: вызывает LineProcessor.main с конкретными файлами
  private void runLineProcessor(Path input, Path output) {
    LineProcessor.main(new String[]{input.toString(), output.toString()});
  }

  @Test
  @DisplayName("Должен корректно обработать файл с несколькими строками разного регистра")
  void shouldProcessFileWithMixedCaseLines() throws Exception {
    // Подготовка входных данных
    List<String> inputLines = List.of(
        "Hello World",
        "строка Смешанного РегиСТРа",
        "12345 !@#$$%",
        "MiXeD CaSe LiNe"
    );
    Files.write(inputFile, inputLines, StandardCharsets.UTF_8);

    // Запуск основной логики
    runLineProcessor(inputFile, outputFile);

    // Ожидаемый результат
    List<String> expectedOutput = List.of(
        "HELLO WORLD",
        "СТРОКА СМЕШАННОГО РЕГИСТРА",
        "12345 !@#$$%",
        "MIXED CASE LINE"
    );

    // Проверка результата
    List<String> actualOutput = Files.readAllLines(outputFile, StandardCharsets.UTF_8);
    assertThat(actualOutput).isEqualTo(expectedOutput);

    // Проверка, что ошибок не было
    assertThat(errContent.toString(StandardCharsets.UTF_8)).isBlank();
  }

  @Test
  @DisplayName("Должен корректно обработать пустой входной файл")
  void shouldProcessEmptyInputFile() throws Exception {
    // Создаем пустой файл
    Files.write(inputFile, List.of(), StandardCharsets.UTF_8);

    runLineProcessor(inputFile, outputFile);

    // Проверка: выходной файл тоже пуст
    List<String> actualOutput = Files.readAllLines(outputFile, StandardCharsets.UTF_8);
    assertThat(actualOutput).isEmpty();

    // Ошибок не должно быть
    assertThat(errContent.toString(StandardCharsets.UTF_8)).isBlank();
  }

  @Test
  @DisplayName("Должен перезаписать существующий выходной файл")
  void shouldOverwriteExistingOutputFile() throws Exception {
    // Входной файл
    Files.write(inputFile, List.of("abc"), StandardCharsets.UTF_8);

    // Выходной файл уже существует с другим содержимым
    Files.write(outputFile, List.of("OLD DATA"), StandardCharsets.UTF_8);

    runLineProcessor(inputFile, outputFile);

    // Проверка: старое содержимое перезаписано
    List<String> actualOutput = Files.readAllLines(outputFile, StandardCharsets.UTF_8);
    assertThat(actualOutput).containsExactly("ABC");

    // Ошибок не должно быть
    assertThat(errContent.toString(StandardCharsets.UTF_8)).isBlank();
  }

  @Test
  @DisplayName("Должен создать входной файл по умолчанию, если он не существует")
  void shouldCreateDefaultInputFileIfNotExists() throws Exception {
    Path defaultInput = tempDir.resolve("input_for_processor.txt");
    Path defaultOutput = tempDir.resolve("output_processed.txt");

    // Запуск без предварительного создания input-файла
    LineProcessor.main(new String[]{defaultInput.toString(), defaultOutput.toString()});

    // Проверяем, что input-файл создан
    assertThat(Files.exists(defaultInput)).isTrue();

    // Проверяем, что output-файл тоже создан
    assertThat(Files.exists(defaultOutput)).isTrue();

    // Проверяем, что в System.out есть сообщение
    assertThat(outContent.toString(StandardCharsets.UTF_8))
        .contains("не найден, создан файл по умолчанию");
  }
}
