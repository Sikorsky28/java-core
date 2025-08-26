package ru.mentee.power.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

public class LineProcessor {

  public static void main(String[] args) {
    // Берём аргументы или дефолтные пути
    String inputFileName = args.length > 0 ? args[0] : "input_for_processor.txt";
    String outputFileName = args.length > 1 ? args[1] : "output_processed.txt";

    Path inputPath = Paths.get(inputFileName);
    Path outputPath = Paths.get(outputFileName);

    try {
      if (Files.notExists(inputPath)) {
        List<String> defaultLines = List.of(
            "Первая строка.",
            "Second Line with MixEd Case",
            "третья"
        );
        Files.write(inputPath, defaultLines, StandardCharsets.UTF_8);
        System.out.println("Файл не найден, создан файл по умолчанию: " + inputFileName);
      }
    } catch (IOException e) {
      System.err.println("Ошибка при создании файла по умолчанию: " + e.getMessage());
      return;
    }

    try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8);
         BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {

      String line;
      while ((line = reader.readLine()) != null) {
        writer.write(line.toUpperCase(Locale.ROOT)); // фиксируем локаль
        writer.newLine();
      }

      System.out.println("Файл успешно обработан. Результат в " + outputFileName);

    } catch (IOException e) {
      System.err.println("Ошибка при обработке файла: " + e.getMessage());
    }
  }
}
