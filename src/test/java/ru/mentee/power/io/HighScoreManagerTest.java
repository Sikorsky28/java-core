package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты менеджера данных HighScoreManager")
class HighScoreManagerTest {

  @TempDir
  Path tempDir;

  private Path testFilePath;

  @BeforeEach
  void setUp() {
    testFilePath = tempDir.resolve("test_scores.dat");
  }

  @Test
  @DisplayName("Должен сохранять и загружать пустой список")
  void shouldSaveAndLoadEmptyList() {
    // Создаем пустой список
    List<HighScoreEntryClass> emptyList = new ArrayList<>();

    // Сохраняем пустой список
    HighScoreManager.saveScores(emptyList, testFilePath.toString());

    // Загружаем список из файла
    List<HighScoreEntryClass> loadedList = HighScoreManager.loadScores(testFilePath.toString());

    // Проверяем результат
    assertThat(loadedList).isNotNull();
    assertThat(loadedList).isEmpty();
  }

  @Test
  @DisplayName("Должен сохранять и загружать список с несколькими записями")
  void shouldSaveAndLoadPopulatedList() {
    // Создаем список с несколькими записями
    List<HighScoreEntryClass> scores = new ArrayList<>();
    scores.add(new HighScoreEntryClass("Alice", 1200));
    scores.add(new HighScoreEntryClass("Bob", 1000));
    scores.add(new HighScoreEntryClass("Charlie", 1550));

    // Сохраняем список
    HighScoreManager.saveScores(scores, testFilePath.toString());

    // Загружаем список
    List<HighScoreEntryClass> loadedList = HighScoreManager.loadScores(testFilePath.toString());

    // Проверяем размер
    assertThat(loadedList).hasSize(scores.size());

    // Проверяем содержимое поэлементно
    for (int i = 0; i < scores.size(); i++) {
      assertThat(loadedList.get(i).getPlayerName())
          .isEqualTo(scores.get(i).getPlayerName());
      assertThat(loadedList.get(i).getScore())
          .isEqualTo(scores.get(i).getScore());
    }
  }

  @Test
  @DisplayName("Должен возвращать пустой список при загрузке несуществующего файла")
  void shouldReturnEmptyListForNonExistentFile() {
    // Загружаем данные из несуществующего файла
    List<HighScoreEntryClass> loadedList = HighScoreManager.loadScores(testFilePath.toString());

    // Проверяем, что список пустой
    assertThat(loadedList).isNotNull();
    assertThat(loadedList).isEmpty();
  }
}
