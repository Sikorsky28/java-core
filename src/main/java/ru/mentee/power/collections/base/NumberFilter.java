package ru.mentee.power.collections.base;

import java.util.List;
import java.util.ArrayList;

public class NumberFilter {
    /**
     * Фильтрует список чисел, оставляя только четные.
     *
     * @param numbers Список целых чисел
     * @return Новый список, содержащий только четные числа из исходного списка
     */
    public static List<Integer> filterEvenNumbers(List<Integer> numbers) {
       List<Integer> onlyEvenNumbers = new ArrayList<>();


       if(numbers != null){
           for (Integer num : numbers){
               if( num != null && num % 2 == 0){
                   onlyEvenNumbers.add(num);

               }
           }
       }



        return onlyEvenNumbers;
    }
}