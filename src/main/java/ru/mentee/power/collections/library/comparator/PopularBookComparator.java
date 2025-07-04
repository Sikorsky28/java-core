package ru.mentee.power.collections.library.comparator;

import java.util.Comparator;
import java.util.Map;

public class PopularBookComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
        return Integer.compare(e2.getValue(), e1.getValue()); // по убыванию
    }
}