package ru.mentee.power.conditions;

import java.util.Scanner;

public class RentalChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем возраст пользователя
        System.out.print("Введите ваш возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Очищаем буфер после nextInt()

        // Запрашиваем наличие водительских прав
        System.out.print("Есть ли у вас водительские права? (да/нет): ");
        String hasLicense = scanner.nextLine().toLowerCase();

        // Проверяем условия аренды
        boolean canRent = age >= 18 && hasLicense.equals("да");

        // Выводим результат
        if (canRent) {
            System.out.println("Вы можете арендовать автомобиль!");
        } else {
            System.out.println("К сожалению, вы не можете арендовать автомобиль.");

            // Уточняем причину отказа
            if (age < 18) {
                System.out.println("Причина: вам меньше 18 лет.");
            }
            if (!hasLicense.equals("да")) {
                System.out.println("Причина: у вас нет водительских прав.");
            }
        }

        scanner.close();
    }
}