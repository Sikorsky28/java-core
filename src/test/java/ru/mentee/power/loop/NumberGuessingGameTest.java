package ru.mentee.power.loop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;

class NumberGuessingGameTest {

    // Поток для перехвата вывода программы
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // Сохраняем оригинальные потоки ввода-вывода
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        // Перед каждым тестом перенаправляем стандартный вывод в наш поток
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        // После каждого теста восстанавливаем оригинальные потоки
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testPlayRoundGuessCorrectly() {
        // Создаем тестовую версию игры с предопределенным случайным числом 42
        NumberGuessingGame game = new TestableNumberGuessingGame(42);

        // Имитируем последовательность действий пользователя:
        // 1. Вводим 50 (слишком большое) -> получаем подсказку "меньше"
        // 2. Вводим 30 (слишком маленькое) -> получаем подсказку "больше"
        // 3. Вводим 40 (слишком маленькое) -> получаем подсказку "больше"
        // 4. Вводим 42 (правильное число) -> получаем поздравление
        // 5. Отвечаем "нет" на вопрос о новой игре -> игра завершается
        String input = "50\n30\n40\n42\nнет\n";

        // Устанавливаем подготовленный ввод
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Запускаем игру
        game.startGame();

        // Получаем весь вывод программы
        String output = outputStream.toString();

        // Проверяем, что программа выдала ожидаемые сообщения
        assertThat(output).contains("Я загадал число");  // Начальное сообщение
        assertThat(output).contains("больше");          // Подсказка "больше"
        assertThat(output).contains("меньше");          // Подсказка "меньше"
        assertThat(output).contains("Поздравляю! Вы угадали число 42"); // Поздравление
        assertThat(output).contains("за 4 попыток");    // Информация о количестве попыток
    }

    @Test
    void testStatisticsUpdated() {
        // Создаем тестовую версию игры с предопределенным случайным числом 42
        NumberGuessingGame game = new TestableNumberGuessingGame(42);

        // Имитируем пользовательский ввод для двух последовательных раундов:
        // Раунд 1: 3 попытки (30, 40, 42), затем "да" для новой игры
        // Раунд 2: 5 попыток (60, 50, 40, 45, 42), затем "нет" для завершения
        String input = "30\n40\n42\nда\n60\n50\n40\n45\n42\nнет\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Запускаем игру
        game.startGame();

        // Получаем вывод и проверяем статистику
        String output = outputStream.toString();
        assertThat(output).contains("Минимальное количество попыток: 3"); // Минимум (из первого раунда)
        assertThat(output).contains("Максимальное количество попыток: 5"); // Максимум (из второго раунда)
        assertThat(output).contains("Среднее количество попыток: 4.0");   // Среднее: (3+5)/2 = 4.0
        assertThat(output).contains("Сыграно игр: 2");                    // Всего два раунда
    }



    // Вспомогательный тестовый класс, который всегда возвращает предопределенное случайное число
    static class TestableNumberGuessingGame extends NumberGuessingGame {
        private final int fixedNumber;

        TestableNumberGuessingGame(int fixedNumber) {
            this.fixedNumber = fixedNumber;
        }

        @Override
        protected Random createRandom() {
            // Возвращаем собственную реализацию Random, которая выдает предсказуемые значения
            return new TestableRandom(fixedNumber);
        }
    }

    // Тестовая версия класса Random с предсказуемым поведением
    static class TestableRandom extends Random {
        private final int fixedValue;

        TestableRandom(int fixedValue) {
            this.fixedValue = fixedValue;
        }

        @Override
        public int nextInt(int bound) {
            // Возвращаем fixedValue - 1, так как в методе playRound к результату nextInt добавляется 1
            return fixedValue - 1;
        }
    }
}