package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzTest {

    @Test
    public void testFizzBuzzForFirst15Numbers() {
        // Подготовка
        FizzBuzz fizzBuzz = new FizzBuzz();

        // Действие
        String[] result = fizzBuzz.generateFizzBuzz(15);

        // Проверка
        assertThat(result).isNotNull();
        assertThat(result).hasSize(15);

        // Проверяем конкретные значения
        assertThat(result[0]).isEqualTo("1");    // 1
        assertThat(result[1]).isEqualTo("2");    // 2
        assertThat(result[2]).isEqualTo("Fizz"); // 3
        assertThat(result[4]).isEqualTo("Buzz"); // 5
        assertThat(result[14]).isEqualTo("FizzBuzz"); // 15
    }

    @Test
    public void testFizzBuzzWithZeroInput() {
        // Подготовка
        FizzBuzz fizzBuzz = new FizzBuzz();

        // Действие
        String[] result = fizzBuzz.generateFizzBuzz(0);

        // Проверка
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    public void testAllFizzValuesAreDivisibleBy3() {
        // соответствуют числам, делящимся на 3 (и не делящимся на 5)
        FizzBuzz fizzBuzz = new FizzBuzz();
        int n = 30;
        String[] result = fizzBuzz.generateFizzBuzz(n);

        for (int i = 0; i < n; i++) {
            if ("Fizz".equals(result[i])) {
                int number = i + 1;
                assertThat(number % 3).isEqualTo(0); // Число должно делиться на 3
                assertThat(number % 5).isNotEqualTo(0); // Но НЕ должно делиться на 5
            }
        }




    }

    @Test
    public void testAllBuzzValuesAreDivisibleBy5() {
        // соответствуют числам, делящимся на 5 (и не делящимся на 3)

        FizzBuzz fizzBuzz = new FizzBuzz();
        int n = 30;
        String[] result = fizzBuzz.generateFizzBuzz(n);

        for (int i = 0; i < n; i++) {
            if ("Buzz".equals(result[i])) {
                int number = i + 1; // Индекс + 1 (потому что массив начинается с 0)
                assertThat(number % 5).isEqualTo(0); // Число должно делиться на 3
                assertThat(number % 3).isNotEqualTo(0); // Но НЕ должно делиться на 5
            }
        }
    }

    @Test
    public void testAllFizzBuzzValuesAreDivisibleBy3And5() {
        // соответствуют числам, делящимся и на 3, и на 5
        FizzBuzz fizzBuzz = new FizzBuzz();
        int n = 30;
        String[] result = fizzBuzz.generateFizzBuzz(n);

        for (int i = 0; i < n; i++) {
            if ("FizzBuzz".equals(result[i])) {
                int number = i + 1; // Индекс + 1 (потому что массив начинается с 0)
                assertThat(number % 5).isEqualTo(0); // Число должно делиться на 3
                assertThat(number % 3).isEqualTo(0); // Число должно делиться на 5
            }
        }
    }
}