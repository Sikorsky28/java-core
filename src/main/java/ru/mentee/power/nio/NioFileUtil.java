package ru.mentee.power.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// Утилитарный класс для файловых операций NIO.2
public class NioFileUtil {

  /**
   * Копирует файл с использованием Files.copy.
   * Перезаписывает целевой файл, если он существует.
   * @param source Исходный путь.
   * @param destination Целевой путь.
   * @throws IOException Если происходит ошибка ввода-вывода.
   */
  public static void copyFile(Path source, Path destination) throws IOException {
    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    System.out.println("(Внутри NioFileUtil) Копирование из " + source + " в " + destination);
  }
}
