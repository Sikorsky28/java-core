package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class ListUtilsTest {

    @Test
    void shouldMergeTwoListsAndRemoveDuplicates() {
        // Arrange
        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = Arrays.asList("Banana", "Cherry", "Date");

        // Act
        List<String> result = ListUtils.mergeLists(list1, list2);

        // Assert
        assertThat(result)
                .hasSize(4)
                .containsExactlyInAnyOrder("Apple", "Banana", "Cherry", "Date");
    }

    @Test
    void shouldReturnFirstListElementsWhenSecondListIsEmpty() {

        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = new ArrayList<>();

        List<String> result = ListUtils.mergeLists(list1, list2);

        assertThat(result)
                .hasSize(list1.size())
                .containsAll(list1);


    }

    @Test
    void shouldReturnEmptyListWhenBothListsAreEmpty() {

        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        List<String> result = ListUtils.mergeLists(list1, list2);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnSecondListWhenFirstListIsNull() {

        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = null;

        List<String> result = ListUtils.mergeLists(list1, list2);

        assertThat(result)
                .hasSize(list1.size())
                .containsAll(list1);

    }

    @Test
    void shouldReturnEmptyListWhenBothListsAreNull() {
        List<String> list1 = null;
        List<String> list2 = null;

        List<String> result = ListUtils.mergeLists(list1, list2);

        assertThat(result).isEmpty();

    }

    @Test
    void shouldIgnoreNullElementsWhenMergingLists() {

        List<String> list1 = Arrays.asList("Apple", "Banana", null, "Cherry");
        List<String> list2 = Arrays.asList("Banana", null, "Cherry", "Date");

        // Act
        List<String> result = ListUtils.mergeLists(list1, list2);

        // Assert
        assertThat(result)
                .hasSize(4)
                .containsExactlyInAnyOrder("Apple", "Banana", "Cherry", "Date");

    }

    @Test
    void shouldIgnoreEmptyElementsWhenMergingLists() {
        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = Arrays.asList("Banana", "", "Cherry", "Date");

        // Act
        List<String> result = ListUtils.mergeLists(list1, list2);

        // Assert
        assertThat(result)
                .hasSize(4)
                .containsExactlyInAnyOrder("Apple", "Banana", "Cherry", "Date");
    }
}