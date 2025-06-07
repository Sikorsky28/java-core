package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.mentee.power.collections.base.SimpleCollectionTasks.countStringsStartingWith;

class SimpleCollectionTasksTest {

    @Test
    void shouldReturnCorrectCountForStringsStartingWithGivenLetter() {
        // Arrange
        List<String> names = Arrays.asList("Alice", "Bob", "Anna", "Alex");
        char letter = 'A';

        // Act
        int result = countStringsStartingWith(names, letter);

        // Assert
        assertThat(result).isEqualTo(3);
    }

    @Test
    void shouldReturnZeroForEmptyList() {


        // Arrange
        List<String> names = new ArrayList<>();

        // Act
        int result = countStringsStartingWith(names, 'A');


        // Assert
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldReturnZeroForNullList() {

        // Arrange
        List<String> nullList = null;
        char letter = 'A';

        //Act
        int result = countStringsStartingWith(nullList,letter );

        // Assert
        assertThat(result).isEqualTo(0);



    }

    @Test
    void shouldIgnoreNullAndEmptyStringsInList() {
        char letter = 'И';
        List<String> names = Arrays.asList("Иван", "Илья", "", "Леонид", null );


        int res =  countStringsStartingWith(names, letter);

        assertThat(res).isEqualTo(2);

    }

    @Test
    void shouldBeCaseInsensitiveWhenComparingLetters() {


        List<String> names = Arrays.asList("Mike", "John", "maria", "Mark");

        int result = countStringsStartingWith(names, 'M');
        assertThat(result).isEqualTo(3);

    }

    @Test
    void shouldIgnoreSpecialCharactersAtStartOfString() {
        // Arrange (Подготовка данных)
        List<String> strings = Arrays.asList("@apple", "#banana", "Apple", "apricot", "Aardvark");

        // Act (Вызов тестируемого метода)
        int result = countStringsStartingWith(strings, 'A');

        // Assert (Проверка результата)
        assertEquals(3, result, "Метод должен учитывать только слова, начинающиеся с 'A', игнорируя спецсимволы.");
    }
}