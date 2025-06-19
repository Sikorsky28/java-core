package ru.mentee.power.collections.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArrayListAnalyzerTest {

    @Test
    @DisplayName("Метод filterByPrefix должен корректно фильтровать строки по префиксу")
    void shouldFilterByPrefixCorrectly() {
        List<String> input = new ArrayList<>(Arrays.asList("apple", "banana", "apricot", "orange", "app"));
        List<String> expected = Arrays.asList("apple", "apricot", "app");

        List<String> result = ArrayListAnalyzer.filterByPrefix(input, "ap");

        assertThat(result)
                .isNotNull()
                .hasSize(3)
                .containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод filterByPrefix должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFilterByPrefix() {
        assertThatThrownBy(() -> ArrayListAnalyzer.filterByPrefix(null, "test"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ArrayListAnalyzer.filterByPrefix(Arrays.asList("test"), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод filterByPrefix должен вернуть пустой список, если нет совпадений")
    void shouldReturnEmptyListWhenNoMatchesInFilterByPrefix() {
        List<String> input = new ArrayList<>(Arrays.asList("apple", "banana", "apricot", "orange", "app"));
        List<String> result = ArrayListAnalyzer.filterByPrefix(input, "lol");

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Метод findMax должен корректно находить максимальный элемент")
    void shouldFindMaxCorrectly() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 8, 1, 9, 3));

        Integer result = ArrayListAnalyzer.findMax(numbers);

        assertEquals(9, result);
    }

    @Test
    @DisplayName("Метод findMax должен выбросить исключение для пустого списка или null")
    void shouldThrowExceptionForEmptyOrNullListInFindMax() {

        assertThatThrownBy(() -> ArrayListAnalyzer.findMax(null));

    }

    @Test
    @DisplayName("Метод partition должен корректно разбивать список на части")
    void shouldPartitionListCorrectly() {
        List<Integer> input = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50, 60, 70));
        List<List<Integer>> result = ArrayListAnalyzer.partition(input, 3);
        assertThat(result)
                .isNotNull()
                .hasSize(3);

    }

    @Test
    @DisplayName("Метод partition должен выбросить исключение при некорректных аргументах")
    void shouldThrowExceptionForInvalidArgumentsInPartition() {
        assertThatThrownBy(() -> ArrayListAnalyzer.partition(null, 0));
    }

    @Test
    @DisplayName("Метод isPalindrome должен корректно определять палиндромы")
    void shouldIdentifyPalindromesCorrectly() {
        List<String> input = new ArrayList<>(Arrays.asList("Hello","Max", "Hello"));
        boolean result = ArrayListAnalyzer.isPalindrome(input);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Метод isPalindrome должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInIsPalindrome() {
        assertThatThrownBy(() -> ArrayListAnalyzer.isPalindrome(null));
    }
}