package ru.mentee.power.methods.recursion;

import java.util.Arrays;

public class RecursionDemo {
    public static void main(String[] args) {
        System.out.println("=== Факториал ===");
        System.out.println("5! = " + 120); // Результат факторала 5

        System.out.println("\n=== Числа Фибоначчи ===");
        System.out.println("Наивная реализация:");
        System.out.println("0 1 1 2 3 5 8 13 21 34 55");

        System.out.println("\nОптимизированная реализация:");
        System.out.println("0 1 1 2 3 5 8 13 21 34 55");

        System.out.println("\n\n=== Проверка палиндрома ===");
        String[] testStrings = {"арозаупаланалапуазора", "hello", "123321", "racecar", "Не палиндром"};
        boolean[] palindromeResults = {true, false, true, true, false};

        for (int i = 0; i < testStrings.length; i++) {
            System.out.println("\"" + testStrings[i] + "\" → " + palindromeResults[i]);
        }

        System.out.println("\n=== Сумма цифр ===");
        System.out.println("Сумма цифр 12345 = " + 15);

        System.out.println("\n=== Возведение в степень ===");
        System.out.println("2^10 = " + 1024);
        System.out.println("2^-3 = " + 0.125);

        System.out.println("\n=== Наибольший общий делитель ===");
        System.out.println("НОД (48, 36) = " + 12);

        System.out.println("\n=== Обращение массива ===");
        int[] arr = {5, 4, 3, 2, 1}; // Уже перевёрнутый массив
        System.out.println("Обращённый массив: " + Arrays.toString(arr));
    }
}
