package ru.mentee.power.loop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.assertj.core.api.Assertions.assertThat;


public class FibonacciCalculatorTest {

    private FibonacciCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new FibonacciCalculator();
    }

    @Test
    void testFibonacciRecursiveSmallNumbers() {
        assertThat(calculator.fibonacciRecursive(0)).isEqualTo(0);
        assertThat(calculator.fibonacciRecursive(1)).isEqualTo(1);
        assertThat(calculator.fibonacciRecursive(2)).isEqualTo(1);
        assertThat(calculator.fibonacciRecursive(3)).isEqualTo(2);
        assertThat(calculator.fibonacciRecursive(4)).isEqualTo(3);
        assertThat(calculator.fibonacciRecursive(5)).isEqualTo(5);
        assertThat(calculator.fibonacciRecursive(10)).isEqualTo(55);
    }

    @Test
    void testFibonacciIterative() {
        assertThat(calculator.fibonacciIterative(0)).isEqualTo(0);
        assertThat(calculator.fibonacciIterative(1)).isEqualTo(1);
        assertThat(calculator.fibonacciIterative(10)).isEqualTo(55);
        assertThat(calculator.fibonacciIterative(20)).isEqualTo(6765);
        assertThat(calculator.fibonacciIterative(30)).isEqualTo(832040);
        // Проверка большего числа - это должно быть эффективно
        assertThat(calculator.fibonacciIterative(40)).isEqualTo(102334155);
    }

    @Test
    void testFibonacciWithCache() {
        assertThat(calculator.fibonacciWithCache(0)).isEqualTo(0);
        assertThat(calculator.fibonacciWithCache(1)).isEqualTo(1);
        assertThat(calculator.fibonacciWithCache(10)).isEqualTo(55);
        assertThat(calculator.fibonacciWithCache(40)).isEqualTo(102334155);
        // Большое число - должно быстро вычисляться благодаря кэшированию
        assertThat(calculator.fibonacciWithCache(45)).isEqualTo(1134903170);
    }

    @Test
    @Timeout(value = 5) // Тест должен выполниться за 5 секунд
    void testFibonacciRecursiveWithTimeout() {
        // Это должно быть достаточно быстро даже для рекурсивного метода
        calculator.fibonacciRecursive(25);
    }

    @Test
    void testGenerateFibonacciSequence() {
        long[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21};
        assertThat(calculator.generateFibonacciSequence(9)).isEqualTo(expected);
    }

    @Test
    void testIsFibonacciNumber() {
        assertThat(calculator.isFibonacciNumber(0)).isTrue();
        assertThat(calculator.isFibonacciNumber(1)).isTrue();
        assertThat(calculator.isFibonacciNumber(2)).isTrue();
        assertThat(calculator.isFibonacciNumber(3)).isTrue();
        assertThat(calculator.isFibonacciNumber(4)).isFalse();
        assertThat(calculator.isFibonacciNumber(5)).isTrue();
        assertThat(calculator.isFibonacciNumber(6)).isFalse();
        assertThat(calculator.isFibonacciNumber(8)).isTrue();
        assertThat(calculator.isFibonacciNumber(10)).isFalse();
        assertThat(calculator.isFibonacciNumber(13)).isTrue();
        assertThat(calculator.isFibonacciNumber(21)).isTrue();
    }

    @Test
    void testNegativeInput() {
        long[] expected = new long[0];
        assertThat(calculator.generateFibonacciSequence(-1)).isEqualTo(expected);

    }

    @Test
    void testPerformanceComparison() {
        FibonacciCalculator calculator = new FibonacciCalculator();
        int n = 40; // Достаточно большое число для теста производительности

        // Измеряем время выполнения рекурсивного метода
        long startRecursive = System.nanoTime();
        long fibRecursive = calculator.fibonacciRecursive(n);
        long timeRecursive = System.nanoTime() - startRecursive;

        // Измеряем время выполнения итеративного метода
        long startIterative = System.nanoTime();
        long fibIterative = calculator.fibonacciIterative(n);
        long timeIterative = System.nanoTime() - startIterative;

        // Измеряем время выполнения метода с кэшированием
        long startCached = System.nanoTime();
        long fibCached = calculator.fibonacciWithCache(n);
        long timeCached = System.nanoTime() - startCached;

        // Проверяем, что все методы дают правильный результат
        assertThat(fibRecursive).isEqualTo(fibIterative).isEqualTo(fibCached);

        // Выводим результаты в консоль
        System.out.println("Время выполнения:");
        System.out.println("Рекурсивный: " + timeRecursive / 1_000_000.0 + " мс");
        System.out.println("Итеративный: " + timeIterative / 1_000_000.0 + " мс");
        System.out.println("Кэшированный: " + timeCached / 1_000_000.0 + " мс");

        // Проверяем, что рекурсивный метод самый медленный
        assertThat(timeRecursive).isGreaterThan(timeIterative);
        assertThat(timeRecursive).isGreaterThan(timeCached);
    }
}