package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

class ArrayStatisticsTest {

    @Test
    void testFindMinMax() {
        // Подготовка
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        // Проверка
        assertThat(stats.findMin()).isEqualTo(1);
        assertThat(stats.findMax()).isEqualTo(9);
    }

    @Test
    void testCalculateSumAndAverage() {
        // Подготовка
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        // Проверка
        assertThat(stats.calculateSum()).isEqualTo(51);
        assertThat(stats.calculateAverage()).isEqualTo(5.1);
    }

    @Test
    void testCalculateMedian() {
        // Подготовка
        int[] testData1 = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6}; // Четное количество элементов
        int[] testData2 = {5, 7, 2, 9, 3, 5, 1, 8, 5}; // Нечетное количество элементов

        ArrayStatistics stats1 = new ArrayStatistics(testData1);
        ArrayStatistics stats2 = new ArrayStatistics(testData2);

        // Проверка
        assertThat(stats1.calculateMedian()).isEqualTo(5.0);
        assertThat(stats2.calculateMedian()).isEqualTo(5.0);
    }

    @Test
    void testFindMode() {
        // Подготовка
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        // Проверка
        assertThat(stats.findMode()).isEqualTo(5); // 5 встречается 3 раза
    }

    @Test
    void testCalculateStandardDeviation() {
        // Подготовка
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        // Проверка (с округлением до 2 знаков после запятой)
        assertThat(Math.round(stats.calculateStandardDeviation() * 100) / 100.0).isEqualTo(2.51);
    }

    @Test
    void testCountGreaterAndLessThan() {
        // Подготовка
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        // Проверка
        assertThat(stats.countGreaterThan(5)).isEqualTo(4); // 7, 9, 8, 6
        assertThat(stats.countLessThan(5)).isEqualTo(3); // 2, 3, 1
    }

    @Test
    void testContains() {
        // Подготовка
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        // Проверка
        assertThat(stats.contains(7)).isTrue();
        assertThat(stats.contains(10)).isFalse();
    }

    @Test
    void testEmptyArray() {
        int[] testData = {};

        ArrayStatistics stats = new ArrayStatistics(testData);

        // проверка
        assertThat(stats.calculateStandardDeviation()).isEqualTo(0);
        assertThat(stats.findMin()).isEqualTo(Integer.MAX_VALUE);
        assertThat(stats.findMax()).isEqualTo(Integer.MIN_VALUE);
        assertThat(stats.calculateAverage()).isEqualTo(0);
        assertThat(stats.findMode()).isEqualTo(0);



    }

    @Test
    void testSingleElementArray() {
        int[] testData = {7};

        ArrayStatistics stats = new ArrayStatistics(testData);

        assertThat(stats.calculateStandardDeviation()).isEqualTo(0.0);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] testData = {7, 7, 7, 7, 7};

        ArrayStatistics stats = new ArrayStatistics(testData);

        assertThat(stats.calculateMedian()).isEqualTo(7);
        assertThat(stats.calculateAverage()).isEqualTo(7);
        assertThat(stats.calculateStandardDeviation()).isEqualTo(0.0);

        assertThat(stats.findMode()).isEqualTo(7);
    }

    @Test
    void testArrayWithNegativeValues() {
      int[] testData = {-3, -5, -8, -4, -2};

      ArrayStatistics stats = new ArrayStatistics(testData);

      assertThat(stats.findMax()).isEqualTo(-2);
      assertThat(stats.findMin()).isEqualTo(-8);

    }
}