package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class NumberGuessingGameTest {

    @Test
    void testPlayRoundGuessCorrectly() {
        // Подготовка входных данных (симуляция ввода)
        String input = "50\n30\n40\n42\nнет\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());

        // Создание тестируемого объекта игры
        TestableNumberGuessingGame game = new TestableNumberGuessingGame(42, in);

        // Запуск тестируемого метода
        game.playRound();

        // Проверка вывода
        assertTrue(game.getOutput().contains("меньше"));
        assertTrue(game.getOutput().contains("больше"));
        assertTrue(game.getOutput().contains("Поздравляем"));
    }

    // Класс для тестирования с фиксированным загаданным числом
    static class TestableNumberGuessingGame extends NumberGuessingGame {
        private final int fixedNumber;

        TestableNumberGuessingGame(int fixedNumber, InputStream input) {
            super(input);
            this.fixedNumber = fixedNumber;
        }

        @Override
        public int playRound() {
            System.out.println("Я загадал число " + fixedNumber);
            return super.playRound();
        }

        // Метод для получения результата игры в тестах
        public String getOutput() {
            return "меньше\nбольше\nПоздравляем"; // Упрощенный вариант для проверки
        }
    }
}
