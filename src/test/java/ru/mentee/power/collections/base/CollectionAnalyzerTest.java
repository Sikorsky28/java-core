package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class CollectionAnalyzerTest {

    @Test
    void shouldReturnStringsLongerThanMinLength() {
        // Arrange
        Collection<String> strings = Arrays.asList("a", "abc", "abcde", "xy");
        int minLength = 2;

        // Act
        List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);

        // Assert
        assertThat(result)
                .hasSize(2)
                .containsExactly("abc", "abcde");
    }

    @Test
    void shouldReturnEmptyListWhenCollectionIsNull() {
       Collection<String> strings = null;
        int minLength = 2;

        List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);

        assertThat(result).isEmpty();

    }

    @Test
    void shouldReturnEmptyListWhenCollectionIsEmpty() {
        // Arrange
        Collection<String> strings = new ArrayList<>();
        int minLength = 2;

        // Act
        List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void shouldIgnoreNullAndEmptyStringsWhenFindingLongStrings() {
        // Arrange
        Collection<String> strings = Arrays.asList("a", null, "", "abc", "abcde", "xy");
        int minLength = 2;

        // Act
        List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);

        // Assert
        assertThat(result)
                .hasSize(2)
                .containsExactly("abc", "abcde");
    }

    @Test
    void shouldCalculateCorrectDigitSumForPositiveNumber() {
        // Act
        int result = CollectionAnalyzer.calculateDigitSum(123);

        // Assert
        assertThat(result).isEqualTo(6); // 1 + 2 + 3 = 6
    }

    @Test
    void shouldCalculateCorrectDigitSumForNegativeNumber() {
        // Act
        int result = CollectionAnalyzer.calculateDigitSum(-123);

        // Assert
        assertThat(result).isEqualTo(6); // 1 + 2 + 3 = 6
    }

    @Test
    void shouldReturnZeroAsDigitSumForZero() {
        // Act
        int result = CollectionAnalyzer.calculateDigitSum(0);

        // Assert
        assertThat(result).isEqualTo(0); // 1 + 2 + 3 = 6
    }

    @Test
    void shouldCountNumbersWithDigitSumGreaterThanThreshold() {
        Collection<Integer> numbers = Arrays.asList(123,2,45,65,5764,5432,324);
        int threshold = 5;


        // Act
        int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers,threshold);

        // Assert
        assertThat(result).isEqualTo(6);
    }

    @Test
    void shouldReturnZeroWhenCountingWithNullCollection() {
        Collection<Integer> numbers = null;
        int threshold = 5;


        // Act
        int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers,threshold);

        // Assert
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldReturnZeroWhenCountingWithEmptyCollection() {
        Collection<Integer> numbers = Arrays.asList();
        int threshold = 5;


        // Act
        int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers,threshold);

        // Assert
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldHandleCustomScenarioForDigitSumCount() {
        Collection<Integer> numbers = Arrays.asList(123,2,45,65,-5764,5432,324);
        int threshold = 5;


        // Act
        int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers,threshold);

        // Assert
        assertThat(result).isEqualTo(6);
    }
}