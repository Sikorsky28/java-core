package ru.mentee.power.collections.base;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class CollectionAnalyzer {
    /**
     * Находит все строки в коллекции, длина которых больше заданной.
     *
     * @param strings Коллекция строк
     * @param minLength Минимальная длина
     * @return Список строк, длина которых больше minLength
     */
    public static List<String> findLongStrings(Collection<String> strings, int minLength) {
        List<String> longStrings = new ArrayList<>();

        if (strings != null) {

            for (String s : strings) {
                if (s != null && s.length() > minLength) {
                    longStrings.add(s);
                }
            }
        }

        return longStrings;
    }

    /**
     * Подсчитывает количество элементов в коллекции, удовлетворяющих условию:
     * сумма цифр числа больше заданного значения.
     *
     * @param numbers Коллекция целых чисел
     * @param threshold Пороговое значение для суммы цифр
     * @return Количество чисел, сумма цифр которых больше threshold
     */
    public static int countNumbersWithDigitSumGreaterThan(Collection<Integer> numbers, int threshold) {
        List<Integer> nums = new ArrayList<>();
        int count = 0;
        if(threshold == 0){
            return 0;
        }

        if (numbers != null){
            for (Integer n : numbers){
                if(n != null && calculateDigitSum(n) > threshold){
                   count++;
                }
            }
        }

        return count;
    }

    /**
     * Вспомогательный метод для подсчета суммы цифр числа.
     *
     * @param number Целое число
     * @return Сумма цифр числа
     */
    static int calculateDigitSum(int number) {
        int sum = 0;
        number = Math.abs(number); // обрабатываем отрицательные числа

        while (number != 0 ){
            sum = sum +  number % 10;
            number = number / 10;
        }


        return sum;
    }
}