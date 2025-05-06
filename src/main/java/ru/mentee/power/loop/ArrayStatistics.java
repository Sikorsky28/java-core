package ru.mentee.power.loop;

import ru.mentee.power.conditions.CarEcoRating;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayStatistics {

    // Массив с данными для анализа
    private final int[] data;

    /**
     * Конструктор класса
     *
     * @param data массив целых чисел для анализа
     */
    public ArrayStatistics(int[] data) {
        // Создаем копию массива, чтобы избежать изменений извне
        this.data = Arrays.copyOf(data, data.length);
    }

    /**
     * Возвращает минимальное значение в массиве
     *
     * @return минимальное значение или Integer.MAX_VALUE, если массив пуст
     */
    public int findMin() {
        if(data.length == 0){
            return Integer.MAX_VALUE;
        }
        int min = data[0];
        for (int num : data){
            if (num < min){
                min = num;
            }
        }

        return min;
    }

    /**
     * Возвращает максимальное значение в массиве
     *
     * @return максимальное значение или Integer.MIN_VALUE, если массив пуст
     */
    public int findMax() {
        if(data.length == 0){
            return Integer.MIN_VALUE;
        }
        int max = data[0];
        for(int num : data){
            if (num > max){
                max = num;
            }
        }
        return max;
    }

    /**
     * Вычисляет сумму всех элементов массива
     *
     * @return сумма элементов
     */
    public int calculateSum() {
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];

        }

        return sum;
    }

    /**
     * Вычисляет среднее арифметическое элементов массива
     *
     * @return среднее арифметическое или 0, если массив пуст
     */
    public double calculateAverage() {

        if(data.length == 0){
            return 0;
        }

        int sum = 0;
        double average = 0.0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        average = sum/data.length;


        return average;
    }

    /**
     * Вычисляет медиану массива (среднее значение после сортировки)
     *
     * @return медиана или 0, если массив пуст
     */
    public double calculateMedian() {

        Arrays.sort(data);
        int mid = data.length / 2;
        double median ;
        if (data.length % 2 == 0) {
            median = (data[mid -1 ] + data[mid]) / 2.0;
        } else {
            median = data[mid];

        }
        return median;

    }

    /**
     * Находит моду массива (наиболее часто встречающееся значение)
     * Если таких значений несколько, возвращает наименьшее из них
     *
     * @return мода или 0, если массив пуст
     */
    public int findMode() {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        if(data.length == 0){
            return 0;
        }

        // Подсчитываем частоту каждого числа
        for (int num : data) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int mode = data[0]; // Изначально предполагаем первое число как моду
        int maxCount = 0;

        // Ищем число с максимальной частотой
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mode = entry.getKey();
            }
        }

        return mode;
    }



    /**
     * Вычисляет стандартное отклонение элементов массива
     *
     * @return стандартное отклонение или 0, если массив пуст или содержит менее 2 элементов
     */
    public double calculateStandardDeviation() {
        int sum = 0;
        double average = 0.0;
        double xSum = 0.0;

        if (data.length == 0 || data.length <= 2){
            return 0;
        }

        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        average = (double)sum/data.length; // Важно добавить приведение типо

        for (int i = 0; i < data.length; i++) {
            xSum += Math.pow((data[i] - average), 2);


        }

        double std = Math.sqrt(xSum / data.length);

        return std;
    }

    /**
     * Подсчитывает количество элементов, больших заданного значения
     *
     * @param value значение для сравнения
     * @return количество элементов, больших value
     */
    public int countGreaterThan(int value) {
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            if (i > value){
                count++;

            }
        }
        return count;
    }

    /**
     * Подсчитывает количество элементов, меньших заданного значения
     *
     * @param value значение для сравнения
     * @return количество элементов, меньших value
     */
    public int countLessThan(int value) {
        int count = 0;
        for (int num : data){
            if (num < value){
                count++;
            }
        }
        return count;
    }

    /**
     * Проверяет, содержит ли массив заданное значение
     *
     * @param value искомое значение
     * @return true, если значение найдено, иначе false
     */
    public boolean contains(int value) {
        for (int num : data){
            if(num == value){
                return true;
            }

        }
        return false;
    }

    /**
     * Выводит статистический отчет по массиву
     */
    public void printStatisticsReport() {
        System.out.println("===== Статистический отчет =====");
        System.out.println("Размер массива: " + data.length);
        System.out.println("Минимальное значение: " + findMin());
        System.out.println("Максимальное значение: " + findMax());
        System.out.println("Сумма элементов: " + calculateSum());
        System.out.println("Среднее арифметическое: " + calculateAverage());
        System.out.println("Медиана: " + calculateMedian());
        System.out.println("Мода: " + findMode());
        System.out.println("Стандартное отклонение: " + calculateStandardDeviation());
        System.out.println("================================");
    }

    public static void main(String[] args) {
        // Пример использования
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        System.out.println("Исходный массив: " + Arrays.toString(testData));
        stats.printStatisticsReport();

        // Примеры использования отдельных методов
        System.out.println("Элементов больше 5: " + stats.countGreaterThan(5));
        System.out.println("Элементов меньше 5: " + stats.countLessThan(5));
        System.out.println("Массив содержит 7: " + stats.contains(7));
        System.out.println("Массив содержит 10: " + stats.contains(10));
    }
}