package ru.mentee.power.collections.list;

import java.util.*;

/**
 * Класс для анализа и обработки связных списков (LinkedList)
 */
public class LinkedListAnalyzer {

    /**
     * Объединяет два списка, удаляя дубликаты
     *
     * @param list1 первый список
     * @param list2 второй список
     * @return новый список, содержащий все уникальные элементы из обоих списков
     * @throws IllegalArgumentException если list1 или list2 равны null
     */
    public static <T> List<T> mergeLists(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Оба списка должны быть не null");
        }

        Set<T> uniqueElements = new HashSet<>();

        for (T item : list1) {
            uniqueElements.add(item);
        }

        for (T item : list2) {
            uniqueElements.add(item);
        }

        return new LinkedList<>(uniqueElements);
    }

    /**
     * Переворачивает список (изменяет порядок элементов на обратный)
     *
     * @param list список для обращения
     * @return тот же список с обратным порядком элементов
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> reverse(List<T> list) {
        if (list == null){
            throw new IllegalArgumentException("Список не может быть null!");
        }
        LinkedList<T> reversed = new LinkedList<>();

        for (T element : list){
            reversed.addFirst(element);
        }

        list.clear();
        list.addAll(reversed);

        return list;
    }

    /**
     * Удаляет из списка все элементы, которые встречаются более одного раза,
     * оставляя только их первое вхождение
     *
     * @param list список для обработки
     * @return список с удаленными дубликатами
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> removeDuplicates(List<T> list) {
        if (list == null){
            throw new IllegalArgumentException("Список не может быть null!");
        }

         Set<T> uniqueElements = new HashSet<>();

        Iterator<T> iterator = list.iterator();

        while (iterator.hasNext()){
            T current = iterator.next();
            if (!uniqueElements.add(current)){
                iterator.remove();
            }
        }

        return  list;
    }

    /**
     * Реализует циклический сдвиг элементов списка на указанное количество позиций
     *
     * @param list список для сдвига
     * @param positions количество позиций для сдвига (положительное - вправо, отрицательное - влево)
     * @return тот же список с циклически сдвинутыми элементами
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> rotate(List<T> list, int positions) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }

        LinkedList<T> linked = new LinkedList<>(list);
        int size = linked.size();
        if (size == 0 || positions % size == 0) {
            return linked;
        }

        // Преобразуем сдвиг к диапазону [0, size)
        int shift = ((positions % size) + size) % size;

        // Сдвиг вправо на shift шагов: по одному перемещаем в начало
        for (int i = 0; i < shift; i++) {
            T last = linked.removeLast();
            linked.addFirst(last);
        }
        return linked;
    }
}