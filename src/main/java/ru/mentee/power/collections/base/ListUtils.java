package ru.mentee.power.collections.base;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class ListUtils {
    /**
     * Объединяет два списка строк в один, удаляя дубликаты.
     *
     * @param list1 Первый список строк
     * @param list2 Второй список строк
     * @return Новый список, содержащий все уникальные элементы из обоих списков
     */
    public static List<String> mergeLists(List<String> list1, List<String> list2) {
        Set<String> uniqueStrings = new HashSet<>();

        // Добавляем элементы из list1
        if (list1 != null) {
            for (String str : list1) {
                if (str != null && !str.trim().isEmpty()) {
                    uniqueStrings.add(str);
                }
            }
        }

        // Добавляем элементы из list2
        if (list2 != null) {
            for (String str : list2) {
                if (str != null && !str.trim().isEmpty()) {
                    uniqueStrings.add(str);
                }
            }
        }

        return new ArrayList<>(uniqueStrings);
    }
}