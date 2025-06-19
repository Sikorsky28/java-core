package ru.mentee.power.collections.list;

import java.util.*;

/**
 * Класс для анализа и обработки списков на основе ArrayList
 */
public class ArrayListAnalyzer {

    /**
     * Фильтрует список строк, оставляя только те, которые начинаются с указанного префикса
     *
     * @param strings список строк для фильтрации
     * @param prefix префикс для фильтрации
     * @return новый список, содержащий только строки с указанным префиксом
     * @throws IllegalArgumentException если strings или prefix равны null
     */
    public static List<String> filterByPrefix(List<String> strings, String prefix) {
        if (strings == null || prefix == null){
            throw new IllegalArgumentException("Список и prefix не могут быть null");
        }
        List<String> result = new ArrayList<>();
        for(String s : strings){
            if (s.startsWith(prefix)){
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Находит максимальный элемент в списке
     *
     * @param numbers список чисел
     * @return максимальное число из списка
     * @throws IllegalArgumentException если список пуст или равен null
     */
    public static Integer findMax(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()){
            throw new IllegalArgumentException("Список не может быть пустым или null");
        }
        Integer maxValue = Integer.MIN_VALUE;
        for(int i : numbers){
            if(i > maxValue){
                maxValue = i;
            }
        }
        return maxValue;
    }

    /**
     * Разбивает список на части указанного размера
     *
     * @param list исходный список
     * @param partSize размер каждой части
     * @return список списков, где каждый внутренний список имеет размер не более partSize
     * @throws IllegalArgumentException если list равен null или partSize <= 0
     */
    public static <T> List<List<T>> partition(List<T> list, int partSize) {
        if (list == null || partSize <= 0){
            throw new IllegalArgumentException("Список не может быть null, partSize не может быть <= 0");
        }
        List<List<T>> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i+= partSize) {
            int end = Math.min(i + partSize, list.size());
            result.add(list.subList(i, end));
        }
        return result;
    }

    /**
     * Проверяет, является ли список палиндромом
     * (читается одинаково в обоих направлениях)
     *
     * @param list список для проверки
     * @return true, если список является палиндромом, иначе false
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> boolean isPalindrome(List<T> list) {
        if (list == null){
            throw new IllegalArgumentException("Список не может быть null!");
        }
        int left = 0;
        int right = list.size() - 1;
        while (left < right){
            if (!list.get(left).equals(list.get(right))){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
