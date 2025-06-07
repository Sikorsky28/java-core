package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class NumberFilterTest {

    @Test
    void shouldReturnOnlyEvenNumbersFromMixedList() {
        // Arrange
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // Act
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);

        // Assert
        assertThat(result)
                .hasSize(3)
                .containsExactly(2, 4, 6);
    }

    @Test
    void shouldReturnEmptyListWhenSourceContainsOnlyOddNumbers() {
        List<Integer>  onlyOddNumbers = Arrays.asList(1, 3, 7, 9, 13);

        List<Integer> result = NumberFilter.filterEvenNumbers(onlyOddNumbers);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllNumbersWhenSourceContainsOnlyEvenNumbers() {
        List<Integer>  onlyEvenNumbers = Arrays.asList(2, 4, 8, 4, 12);

        List<Integer> result = NumberFilter.filterEvenNumbers(onlyEvenNumbers);

        assertThat(result).hasSize(onlyEvenNumbers.size())
                .containsAll(onlyEvenNumbers);

    }

    @Test
    void shouldReturnEmptyListWhenSourceIsEmpty() {
        List<Integer> nums = new ArrayList<>();

        List<Integer> result = NumberFilter.filterEvenNumbers(nums);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenSourceIsNull() {

        List<Integer> nums = null;

        List<Integer> result = NumberFilter.filterEvenNumbers(nums);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldIgnoreNullElementsWhenFilteringList() {
        // Arrange
        List<Integer> numbers = Arrays.asList(1, 2, 3, null, 4, 5, 6);

        // Act
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);

        // Assert
        assertThat(result)
                .hasSize(3)
                .containsExactly(2, 4, 6);
    }

    @Test
    void shouldHandleCustomScenarioForFilterEvenNumbers() {
        // Arrange
        List<Integer> numbers = Arrays.asList(null, null, null, 1, 8);

        // Act
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);

        // Assert
        assertThat(result)
                .hasSize(1)
                .containsExactly(8);
    }
}