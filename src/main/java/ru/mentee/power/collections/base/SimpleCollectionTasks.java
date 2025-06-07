package ru.mentee.power.collections.base;


import java.util.List;

/**
 * Класс с простыми задачами по работе с коллекциями.
 */
public class SimpleCollectionTasks {


    /**
     * Подсчитывает количество строк в списке, начинающихся с заданной буквы
     * (без учета регистра).
     *
     * @param strings Список строк.
     * @param startChar Начальная буква.
     * @return Количество строк, начинающихся с startChar.
     *         Возвращает 0, если список равен null или пуст.
     */




    public static int countStringsStartingWith(List<String> strings, char startChar) {
    int count = 0;
    if(strings == null || strings.isEmpty()){
        return 0;
    }
    for (String str : strings){
        if(str != null && !str.isEmpty() && Character.toLowerCase(str.charAt(0)) == Character.toLowerCase(startChar)){count++;}
    }
       return count;
   }

}