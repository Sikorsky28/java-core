package ru.mentee.power.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class HighScoreManager {

  /**
   * Сохраняет поля объектов из списка в бинарный файл.
   * Формат: [количество объектов (int)][имя1 (UTF)][очки1 (int)]...
   */
  public static void saveScores(List<HighScoreEntryClass> scores, String filename) {
    try (DataOutputStream dos = new DataOutputStream(
        new BufferedOutputStream(new FileOutputStream(filename)))) {

      // Записываем количество записей
      dos.writeInt(scores.size());

      // Записываем все объекты
      for (HighScoreEntryClass entry : scores) {
        dos.writeUTF(entry.getPlayerName());
        dos.writeInt(entry.getScore());
      }

      System.out.println("Данные сохранены в " + filename);

    } catch (IOException e) {
      System.err.println("Ошибка при сохранении данных: " + e.getMessage());
    }
  }

  /**
   * Загружает данные полей из бинарного файла и создает список объектов HighScoreEntryClass.
   */
  public static List<HighScoreEntryClass> loadScores(String filename) {
    List<HighScoreEntryClass> loadedScores = new ArrayList<>();
    File file = new File(filename);
    if (!file.exists()) {
      System.out.println("Файл данных " + filename + " не найден, возвращаем пустой список.");
      return loadedScores;
    }

    try (DataInputStream dis = new DataInputStream(
        new BufferedInputStream(new FileInputStream(filename)))) {

      int numberOfScores = dis.readInt();
      System.out.println("Ожидается записей: " + numberOfScores);

      for (int i = 0; i < numberOfScores; i++) {
        String name = dis.readUTF();
        int score = dis.readInt();
        HighScoreEntryClass entry = new HighScoreEntryClass(name, score);
        loadedScores.add(entry);
      }

    } catch (EOFException e) {
      System.err.println("Ошибка: Неожиданный конец файла при чтении данных. Файл поврежден? " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Ошибка при загрузке данных: " + e.getMessage());
      loadedScores.clear();
    }

    return loadedScores;
  }

  public static void main(String[] args) {
    String filename = "highscores.dat";

    // 1. Создаем список
    List<HighScoreEntryClass> testScores = new ArrayList<>();
    testScores.add(new HighScoreEntryClass("Alice Class", 1200));
    testScores.add(new HighScoreEntryClass("Bob Class", 1000));
    testScores.add(new HighScoreEntryClass("Charlie Class", 1550));
    testScores.add(new HighScoreEntryClass("David Class", 1100));

    // 2. Сохраняем данные
    saveScores(testScores, filename);

    // 3. Загружаем данные
    System.out.println("\nЗагрузка рекордов...");
    List<HighScoreEntryClass> loadedScores = loadScores(filename);

    // 4. Выводим загруженные данные
    if (loadedScores.isEmpty()) {
      System.out.println("Данные не загружены.");
    } else {
      System.out.println("--- Загруженные данные ---");
      for (HighScoreEntryClass entry : loadedScores) {
        System.out.println(entry);
      }
    }
  }
}