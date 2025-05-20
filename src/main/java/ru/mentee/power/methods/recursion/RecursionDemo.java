package ru.mentee.power.methods.recursion;

import java.util.Arrays;

public class RecursionDemo {
    public static void main(String[] args) {
        System.out.println("=== Факториал ===");
        System.out.println("5! = " + factorial(5));

        System.out.println("\n=== Числа Фибоначчи ===");
        System.out.println("Наивная реализация:");
        for (int i = 0; i <= 10; i++) {
            System.out.print(fibonacci(i) + " ");
        }

        System.out.println("\nОптимизированная реализация:");
        for (int i = 0; i <= 10; i++) {
            System.out.print(fibonacciOptimized(i) + " ");
        }

        System.out.println("\n\n=== Проверка палиндрома ===");
        String[] testStrings = {"арозаупаланалапуазора", "hello", "123321", "racecar", "Не палиндром"};
        for (String str : testStrings) {
            System.out.println("\"" + str + "\" → " + isPalindromeRecursive(str));
        }

        System.out.println("\n=== Сумма цифр ===");
        System.out.println("Сумма цифр 12345 = " + sumOfDigits(12345));

        System.out.println("\n=== Возведение в степень ===");
        System.out.println("2^10 = " + power(2, 10));
        System.out.println("2^-3 = " + power(2, -3));

        System.out.println("\n=== Наибольший общий делитель ===");
        System.out.println("НОД (48, 36) = " + gcd(48, 36));

        System.out.println("\n=== Обращение массива ===");
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Исходный массив: " + Arrays.toString(arr));
        reverseArray(arr, 0, arr.length - 1);
        System.out.println("Обращённый массив: " + Arrays.toString(arr));
    }

    public static int factorial(int n) {
        if (n < 0) return -1; // Ошибка
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    public static long fibonacci(int n) {
        if (n < 0) return -1;
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static long fibonacciOptimized(int n) {
        return fibonacciOptimizedHelper(n, new long[n + 1]);
    }

    private static long fibonacciOptimizedHelper(int n, long[] memo) {
        if (n < 0) return -1;
        if (n <= 1) return n;
        if (memo[n] != 0) return memo[n];

        memo[n] = fibonacciOptimizedHelper(n - 1, memo) + fibonacciOptimizedHelper(n - 2, memo);
        return memo[n];
    }

    public static boolean isPalindromeRecursive(String str) {
        String clean = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return checkPalindrome(clean, 0, clean.length() - 1);
    }

    private static boolean checkPalindrome(String str, int left, int right) {
        if (left >= right) return true;
        if (str.charAt(left) != str.charAt(right)) return false;
        return checkPalindrome(str, left + 1, right - 1);
    }

    public static int sumOfDigits(int n) {
        if (n == 0) return 0;
        return (n % 10) + sumOfDigits(n / 10);
    }

    public static double power(double base, int exponent) {
        if (exponent == 0) return 1;
        if (exponent < 0) return 1 / power(base, -exponent);
        if (exponent % 2 == 0) {
            double halfPower = power(base, exponent / 2);
            return halfPower * halfPower;
        } else {
            return base * power(base, exponent - 1);
        }
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static void reverseArray(int[] array, int start, int end) {
        if (start >= end) return;
        int temp = array[start];
        array[start] = array[end];
        array[end] = temp;
        reverseArray(array, start + 1, end - 1);
    }
}
