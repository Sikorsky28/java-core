package ru.mentee.power.collections.set;

import java.util.*;

/**
 * Класс для анализа текста с использованием множеств
 */
public class TextAnalyzer {

    /**
     * Находит все уникальные слова в тексте
     * Слова разделяются пробелами и знаками препинания
     *
     * @param text текст для анализа
     * @return множество уникальных слов в нижнем регистре
     * @throws IllegalArgumentException если text равен null
     *
     * Рекомендуемая реализация: HashSet - наиболее эффективная для хранения
     * неупорядоченного набора уникальных элементов
     */
    public static Set<String> findUniqueWords(String text) {
        if(text == null){
            throw new IllegalArgumentException("Текст не может быть null");
        } else if (text.isEmpty()) {
            return new HashSet<>();
        }
        Set<String> uniqueWords;
        uniqueWords = new HashSet<>(List.of(text.toLowerCase().split("[\\s\\p{Punct}]+")));
        return uniqueWords;
    }

    /**
     * Находит все слова, которые встречаются в обоих текстах
     * Это операция ПЕРЕСЕЧЕНИЯ множеств (Intersection)
     *
     * @param text1 первый текст
     * @param text2 второй текст
     * @return множество общих слов в нижнем регистре
     * @throws IllegalArgumentException если text1 или text2 равны null
     *
     * Рекомендуемая реализация: HashSet для создания множеств слов
     * и использование метода retainAll() для пересечения
     */
    public static Set<String> findCommonWords(String text1, String text2) {
        if(text1 == null || text2 == null){
            throw new IllegalArgumentException("Текст не может быть null");
        } else if (text1.isBlank() || text2.isBlank()) {
            return Collections.emptySet();
        }
        Set<String> words1 = new HashSet<>(List.of(text1.trim().toLowerCase().split("[\\s\\p{Punct}]+")));
        Set<String> words2 = new HashSet<>(List.of(text2.trim().toLowerCase().split("[\\s\\p{Punct}]+")));

        Set<String> commonWords = new HashSet<>(words1);
        commonWords.retainAll(words2);

        return commonWords ;
    }

    /**
     * Находит все слова, которые встречаются в первом тексте, но отсутствуют во втором
     * Это операция РАЗНОСТИ множеств (Difference)
     *
     * @param text1 первый текст
     * @param text2 второй текст
     * @return множество слов, уникальных для первого текста, в нижнем регистре
     * @throws IllegalArgumentException если text1 или text2 равны null
     *
     * Рекомендуемая реализация: HashSet для создания множеств слов
     * и использование метода removeAll() для вычитания множеств
     */
    public static Set<String> findUniqueWordsInFirstText(String text1, String text2) {
        if(text1 == null || text2 == null){
            throw new IllegalArgumentException("Текст не может быть null");
        } else if (text1.isBlank() || text2.isBlank()) {
            return Collections.emptySet();
        }

        Set<String> words1 = new HashSet<>(List.of(text1.trim().toLowerCase().split("[\\s\\p{Punct}]+")));
        Set<String> words2 = new HashSet<>(List.of(text2.trim().toLowerCase().split("[\\s\\p{Punct}]+")));

        Set<String> uniqueWordsInFirstText = new HashSet<>(words1);
        uniqueWordsInFirstText.removeAll(words2);

        return uniqueWordsInFirstText;
    }

    /**
     * Находит топ-N наиболее часто встречающихся слов в тексте
     *
     * @param text текст для анализа
     * @param n количество слов для возврата
     * @return множество из n наиболее часто встречающихся слов
     * @throws IllegalArgumentException если text равен null или n <= 0
     *
     * Рекомендуемая реализация: использование HashMap для подсчета частоты слов
     * и LinkedHashSet для хранения результата с сохранением порядка вставки
     */
    public static Set<String> findTopNWords(String text, int n){
        if (text == null || n <= 0) {
            throw new IllegalArgumentException("text не может быть null, n должен быть > 0");
        }
        String[] words = text.toLowerCase().split("[\\s\\p{Punct}]+");

        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            if (word.isBlank()) continue;
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(freq.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        Set<String> result = new LinkedHashSet<>();
        for (int i = 0; i < Math.min(n, sorted.size()); i++) {
            result.add(sorted.get(i).getKey());
        }
        return result;
    }

    /**
     * Находит все анаграммы в списке слов
     * Анаграммы - это слова, составленные из одних и тех же букв
     * Например: "пила", "липа", "пали" - анаграммы
     *
     * @param words список слов для проверки
     * @return множество множеств, где каждое внутреннее множество содержит группу анаграмм
     * @throws IllegalArgumentException если words равен null
     *
     * Рекомендуемая реализация: использование TreeSet для внутренних множеств
     * для хранения анаграмм в алфавитном порядке
     */
    public static Set<Set<String>> findAnagrams(List<String> words) {
        if(words == null){
            throw new IllegalArgumentException("Список не может быть пустым!");
        }
        Map<String, TreeSet<String>> map = new HashMap<>();

        for (String word : words){
            char[] chars = word.toLowerCase().toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);

            if (!map.containsKey(sorted)) {
                map.put(sorted, new TreeSet<>());
            }
            map.get(sorted).add(word);
        }
        return new HashSet<>(map.values());
    }

    /**
     * Проверяет, является ли первое множество подмножеством второго
     * Метод демонстрирует операцию проверки ПОДМНОЖЕСТВА
     *
     * @param <T> тип элементов множества
     * @param set1 первое множество
     * @param set2 второе множество
     * @return true, если все элементы set1 содержатся в set2
     * @throws IllegalArgumentException если set1 или set2 равны null
     *
     * Рекомендуемая реализация: использование метода containsAll()
     */
    public static <T> boolean isSubset(Set<T> set1, Set<T> set2) {
        if(set1 == null || set2 == null ){
            throw new IllegalArgumentException("Не корректные параметры ");
        }
        if(set2.containsAll(set1)){
            return true;
        }
        return false;
    }

    /**
     * Удаляет все стоп-слова из текста
     * Демонстрирует ПРАКТИЧЕСКОЕ ПРИМЕНЕНИЕ множеств для фильтрации
     *
     * @param text исходный текст
     * @param stopWords множество стоп-слов
     * @return текст без стоп-слов
     * @throws IllegalArgumentException если text или stopWords равны null
     *
     * Рекомендуемая реализация: использование HashSet для быстрой проверки
     * принадлежности слова к стоп-словам
     */
    public static String removeStopWords(String text, Set<String> stopWords) {
        if(text == null || stopWords == null){
            throw new IllegalArgumentException("Не корректные параметры!");
        }
        String[] words = text.toLowerCase().split("[\\s\\p{Punct}]+");
        StringBuilder res = new StringBuilder();

        for(String word : words){
            if(!stopWords.contains(word)){
                res.append(word).append(" ");
            }
        }
        return res.toString();
    }

    /**
     * Сравнивает производительность работы с разными типами множеств
     * Демонстрирует РАЗНИЦУ между HashSet, LinkedHashSet и TreeSet
     *
     * @param words список слов для тестирования
     * @return Map с результатами замеров времени для разных операций на разных типах Set
     * @throws IllegalArgumentException если words равен null
     */
    public static Map<String, Long> compareSetPerformance(List<String> words) {
        if (words == null){
            throw new IllegalArgumentException("Список не может быть пустым!");
        }
        Map<String, Long> result = new HashMap<>();

        Set<String> hashSet = new HashSet<>();
        Set<String> linkedHashSet = new LinkedHashSet<>();
        Set<String> treeSet = new TreeSet<>();

        // Замер add() для HashSet
        long start = System.nanoTime();
        for(String word : words){
            hashSet.add(word);
        }
        long duration = System.nanoTime() - start;
        result.put("HashSet.add()", duration);

        // Замер add() для LinkedHashSet
        start = System.nanoTime();
        for(String word : words){
            linkedHashSet.add(word);
        }
        duration = System.nanoTime() - start;
        result.put("LinkedHashSet.add()", duration);

        // Замер add() для TreeSet
        start = System.nanoTime();
        for(String word : words){
            treeSet.add(word);
        }
        duration = System.nanoTime() - start;
        result.put("TreeSet.add()", duration);

        // Замер contains() для HashSet
        start = System.nanoTime();
        for(String word : words){
            hashSet.contains(word);
        }
        duration = System.nanoTime() - start;
        result.put("HashSet.contains()", duration);

        // Замер contains() для LinkedHashSet
        start = System.nanoTime();
        for(String word : words){
           linkedHashSet.contains(word);
        }
        duration = System.nanoTime() - start;
        result.put("LinkedHashSet.contains()", duration);

        // Замер contains() для TreeSet
        start = System.nanoTime();
        for(String word : words){
            treeSet.contains(word);
        }
        duration = System.nanoTime() - start;
        result.put("TreeSet.contains()", duration);

        // Замер remove() для HashSet
        start = System.nanoTime();
        for(String word : words){
            hashSet.remove(word);
        }
        duration = System.nanoTime() - start;
        result.put("HashSet.remove()", duration);

        // Замер remove() для LinkedHashSet
        start = System.nanoTime();
        for(String word : words){
            linkedHashSet.remove(word);
        }
        duration = System.nanoTime() - start;
        result.put("LinkedHashSet.remove()", duration);

        // Замер remove() для TreeSet
        start = System.nanoTime();
        for(String word : words){
            treeSet.remove(word);
        }
        duration = System.nanoTime() - start;
        result.put("TreeSet.remove()", duration);

        return result;
    }
}