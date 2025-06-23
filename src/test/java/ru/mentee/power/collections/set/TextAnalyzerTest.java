package ru.mentee.power.collections.set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TextAnalyzerTest {

    @Test
    @DisplayName("Метод findUniqueWords должен находить все уникальные слова в тексте")
    void shouldFindUniqueWordsInText() {
        String text = "Привет, мир! Привет всем в этом мире!";
        Set<String> expected = new HashSet<>(Arrays.asList("привет", "мир", "всем", "в", "этом", "мире"));

        Set<String> result = TextAnalyzer.findUniqueWords(text);

        assertThat(result)
                .isNotNull()
                .hasSize(6)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findUniqueWords должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullTextInFindUniqueWords() {
        assertThatThrownBy(() -> TextAnalyzer.findUniqueWords(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findUniqueWords должен вернуть пустое множество для пустого текста")
    void shouldReturnEmptySetForEmptyTextInFindUniqueWords() {
        String text = "";
        Set<String> result = TextAnalyzer.findUniqueWords(text);

        assertThat(result).hasSize(0);
    }

    @Test
    @DisplayName("Метод findCommonWords должен находить общие слова в двух текстах (операция пересечения)")
    void shouldFindCommonWordsInTexts() {

        String text1 = "Метод findCommonWords должен находить общие слова в двух текстах (операция пересечения)";
        String text2 = "Я должен хорошо освоить коллекции, простой метод это изучать теорию в текстах";

        Set<String> result = TextAnalyzer.findCommonWords(text1, text2);

        Set<String> expected = new HashSet<>(Arrays.asList("должен", "метод", "текстах", "в"));

        assertThat(result)
                .hasSize(4)
                .containsExactlyInAnyOrderElementsOf(result)
                .isNotNull();
    }

    @Test
    @DisplayName("Метод findCommonWords должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFindCommonWords() {
        assertThatThrownBy(() -> TextAnalyzer.findCommonWords(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findUniqueWordsInFirstText должен находить слова, уникальные для первого текста (операция разности)")
    void shouldFindUniqueWordsInFirstText() {
        String text1 = "apple banana orange";
        String text2 = "banana cherry kiwi";
        Set<String> expected = new HashSet<>(Arrays.asList("apple", "orange"));

        Set<String> result = TextAnalyzer.findUniqueWordsInFirstText(text1, text2);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Метод findUniqueWordsInFirstText должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFindUniqueWordsInFirstText() {
        assertThatThrownBy(() -> TextAnalyzer.findUniqueWordsInFirstText(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findTopNWords должен находить наиболее часто встречающиеся слова")
    void shouldFindTopNWords() {
        String text = "Java is great, Java is powerful. Java Java Java!";
        Set<String> expected = new LinkedHashSet<>(Arrays.asList("java, is"));

        Set<String> result = TextAnalyzer.findTopNWords(text, 2);

        assertThat(result).hasSize(2)
                          .containsExactly("java", "is");


    }

    @Test
    @DisplayName("Метод findTopNWords должен выбросить исключение при некорректных аргументах")
    void shouldThrowExceptionForInvalidArgumentsInFindTopNWords() {
        assertThatThrownBy(() -> TextAnalyzer.findTopNWords(null, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findAnagrams должен находить группы анаграмм")
    void shouldFindAnagrams() {
        List<String> words = Arrays.asList("пила", "липа", "пали", "ток", "бар", "кот", "раб");
        Set<Set<String>> expected = new HashSet<>();

        expected.add(new TreeSet<>(Arrays.asList("пила", "липа", "пали")));
        expected.add(new TreeSet<>(Arrays.asList("бар", "раб")));
        expected.add(new TreeSet<>(Arrays.asList("ток", "кот")));

        Set<Set<String>>  res = TextAnalyzer.findAnagrams(words);

        assertThat(res).containsExactlyInAnyOrderElementsOf(expected);


    }

    @Test
    @DisplayName("Метод findAnagrams должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInFindAnagrams() {
        assertThatThrownBy(() -> TextAnalyzer.findAnagrams(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод isSubset должен определять, является ли одно множество подмножеством другого")
    void shouldCheckIfSetIsSubset() {
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        boolean result1 = TextAnalyzer.isSubset(set1, set2);
        boolean result2 = TextAnalyzer.isSubset(set2, set1);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    @DisplayName("Метод isSubset должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInIsSubset() {
        assertThatThrownBy(() -> TextAnalyzer.isSubset(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод removeStopWords должен удалять стоп-слова из текста")
    void shouldRemoveStopWordsFromText() {
        String text = "hello i am learning java";
        Set<String> stopWords = new HashSet<>(Arrays.asList("i", "am"));

        String expected = "hello learning java ";

        String res = TextAnalyzer.removeStopWords(text, stopWords);

        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("Метод removeStopWords должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInRemoveStopWords() {
        assertThatThrownBy(() -> TextAnalyzer.removeStopWords(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

   @Test
    @DisplayName("Метод compareSetPerformance должен сравнивать производительность разных типов множеств")
    void shouldCompareSetPerformance() {

       List<String> words = List.of("java", "spring", "hibernate", "spring", "docker", "java");

       Map<String, Long> result = TextAnalyzer.compareSetPerformance(words);

       assertNotNull(result, "Результат не должен быть null");
       assertEquals(9, result.size(), "Должно быть 9 записей в результате");


       List<String> expectedKeys = List.of(
               "HashSet.add()",
               "LinkedHashSet.add()",
               "TreeSet.add()",
               "HashSet.contains()",
               "LinkedHashSet.contains()",
               "TreeSet.contains()",
               "HashSet.remove()",
               "LinkedHashSet.remove()",
               "TreeSet.remove()"
       );

       for (String key : expectedKeys) {
           assertTrue(result.containsKey(key), "Ожидается ключ: " + key);
           assertTrue(result.get(key) >= 0, "Время выполнения должно быть >= 0 для: " + key);
       }
   }

}