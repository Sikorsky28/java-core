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
import java.util.stream.Collectors;

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

  private void runLineProcessor(Path input, Path output) {
    LineProcessor.main(new String[]{input.toString(), output.toString()});
  }

  private List<String> readNormalized(Path path) throws Exception {
    return Files.readAllLines(path, StandardCharsets.UTF_8)
        .stream()
        .map(s -> s.replace("\r", "")) // нормализуем переносы строк
        .collect(Collectors.toList());
  }

  @Test
  @DisplayName("Должен корректно обработать файл с несколькими строками разного регистра")
  void shouldProcessFileWithMixedCaseLines() throws Exception {
    List<String> inputLines = List.of(
        "Hello World",
        "строка Смешанного РегиСТРа",
        "12345 !@#$$%",
        "MiXeD CaSe LiNe"
    );
    Files.write(inputFile, inputLines, StandardCharsets.UTF_8);

    runLineProcessor(inputFile, outputFile);

    List<String> expectedOutput = List.of(
        "HELLO WORLD",
        "СТРОКА СМЕШАННОГО РЕГИСТРА",
        "12345 !@#$$%",
        "MIXED CASE LINE"
    );

    List<String> actualOutput = readNormalized(outputFile);

    assertThat(actualOutput).isEqualTo(expectedOutput);
    assertThat(errContent.toString(StandardCharsets.UTF_8)).isBlank();
  }

  @Test
  @DisplayName("Должен корректно обработать пустой входной файл")
  void shouldProcessEmptyInputFile() throws Exception {
    Files.write(inputFile, List.of(), StandardCharsets.UTF_8);

    runLineProcessor(inputFile, outputFile);

    List<String> actualOutput = readNormalized(outputFile);
    assertThat(actualOutput).isEmpty();
    assertThat(errContent.toString(StandardCharsets.UTF_8)).isBlank();
  }

  @Test
  @DisplayName("Должен перезаписать существующий выходной файл")
  void shouldOverwriteExistingOutputFile() throws Exception {
    Files.write(inputFile, List.of("abc"), StandardCharsets.UTF_8);
    Files.write(outputFile, List.of("OLD DATA"), StandardCharsets.UTF_8);

    runLineProcessor(inputFile, outputFile);

    List<String> actualOutput = readNormalized(outputFile);
    assertThat(actualOutput).containsExactly("ABC");
    assertThat(errContent.toString(StandardCharsets.UTF_8)).isBlank();
  }

  @Test
  @DisplayName("Должен создать входной файл по умолчанию, если он не существует")
  void shouldCreateDefaultInputFileIfNotExists() throws Exception {
    Path defaultInput = tempDir.resolve("input_for_processor.txt");
    Path defaultOutput = tempDir.resolve("output_processed.txt");

    LineProcessor.main(new String[]{defaultInput.toString(), defaultOutput.toString()});

    assertThat(Files.exists(defaultInput)).isTrue();
    assertThat(Files.exists(defaultOutput)).isTrue();

    String normalizedOut = outContent.toString(StandardCharsets.UTF_8).replace("\r", "");
    assertThat(normalizedOut).contains("не найден").contains("создан файл по умолчанию");
  }
}