package ru.mentee.power.methods.recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс с рекурсивными методами для решения различных задач
 */
public class RecursionExercises {

    private static final HashMap<Integer, Long> memo = new HashMap<>(); // создал статическое поле класса для хранения результатов

    /**
     * Вычисляет факториал числа n
     *
     * @param n Положительное целое число
     * @return Факториал числа n
     * @throws -1 если n < 0
     */
    public static int factorial(int n) {
        if (n < 0){
            return -1;
        }
        // Базовый случай
        if (n == 0 || n == 1) {
            return 1;
        }
        // Рекурсивный случай
        return n * factorial(n - 1);
    }

    /**
     * Вычисляет n-ое число в последовательности Фибоначчи
     *
     * @param n Позиция в последовательности Фибоначчи (0-based)
     * @return Число Фибоначчи на позиции n
     * @throws -1 если n < 0
     */
    public static long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Фибоначчи не вычисляется для отрицательного `n`: " + n);

        }
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);

    }

    /**
     * Оптимизированный метод для вычисления n-ого числа Фибоначчи
     *
     * @param n Позиция в последовательности Фибоначчи (0-based)
     * @return Число Фибоначчи на позиции n
     * @throws -1 если n < 0
     */
    public static long fibonacciOptimized(int n) {
        if (n < 0) {
            return -1; // Обрабатываем ошибочный ввод
        }
        if (n <= 1) {
            return n; // Базовый случай
        }

        if (memo.containsKey(n)) {
            return memo.get(n); // Используем сохранённое значение
        }

        long result = fibonacciOptimized(n - 1) + fibonacciOptimized(n - 2);
        memo.put(n, result); // Кэшируем результат
        return result;
    }

    /**
     * Проверяет, является ли строка палиндромом
     *
     * @param str Исходная строка
     * @return true, если строка является палиндромом, иначе false
     */
    /**
     * Проверяет, является ли строка палиндромом
     *
     * @param str Исходная строка
     * @return true, если строка является палиндромом, иначе false
     * @throws IllegalArgumentException если str == null
     */
    public static boolean isPalindrome(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        // Убираем все не-буквенно-цифровые символы (поддержка кириллицы)
        String clean = str.replaceAll("(?U)[^\\p{L}\\p{N}]", "").toLowerCase();

        // Проверяем пустую строку
        if (clean.isEmpty()) return true;

        return checkPalindrome(clean, 0, clean.length() - 1);
    }

    private static boolean checkPalindrome(String str, int left, int right) {
        if (left >= right) return true; // Базовый случай: если пересеклись индексы
        if (str.charAt(left) != str.charAt(right)) return false; // Если символы не совпадают
        return checkPalindrome(str, left + 1, right - 1); // Рекурсивное приближение
    }



    /**
     * Вычисляет сумму цифр числа
     *
     * @param n Целое число
     * @return Сумма цифр числа
     */
    public static int sumOfDigits(int n) {
        if (n == 0) return 0; // Базовый случай: если число стало 0, сумма завершена
        return (n % 10) + sumOfDigits(n / 10); // Берём последнюю цифру и вызываем метод для оставшихся
    }


    /**
     * Возводит число в степень
     *
     * @param base Основание
     * @param exponent Показатель степени
     * @return Результат возведения в степень
     */
    public static double power(double base, int exponent) {
        if (exponent == 0) {
            return 1; // Базовый случай: любое число в степени 0 равно 1
        }

        if (exponent < 0) {
            return 1 / power(base, -exponent); // Обрабатываем отрицательную степень
        }

        if (exponent % 2 == 0) {
            double halfPower = power(base, exponent / 2); // Вычисляем половину степени
            return halfPower * halfPower; // Умножаем половину на саму себя
        } else {
            return base * power(base, exponent - 1); // Умножаем на `base`, уменьшая степень
        }
    }

    /**
     * Находит наибольший общий делитель двух чисел
     *
     * @param a Первое число
     * @param b Второе число
     * @return Наибольший общий делитель
     */
    public static int gcd(int a, int b) {
        if (b == 0) {  // Базовый случай
            return a;
        }
        return gcd(b, a % b);
    }

    /**
     * Обращает порядок элементов в массиве
     *
     * @param array Исходный массив
     * @param start Начальный индекс для обработки
     * @param end Конечный индекс для обработки
     */
    public static void reverseArray(int[] array, int start, int end) {
        if (start >= end) {
            return; // Базовый случай: дошли до центра массива
        }

        // Меняем элементы местами
        int temp = array[start];
        array[start] = array[end];
        array[end] = temp;

        // Рекурсивный вызов с новыми индексами
        reverseArray(array, start + 1, end - 1);
    }
}