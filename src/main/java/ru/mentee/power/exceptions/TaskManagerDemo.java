package ru.mentee.power.exceptions;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Демонстрационное приложение для работы с TaskManager.
 */
public class TaskManagerDemo {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)){

            TaskManager account = null;
            while (account == null) {
                try{
                System.out.println("Введите id вашего счета: ");
                String id = scanner.nextLine();

                System.out.println("Введите начальный баланс: ");
                double initialBalance = scanner.nextDouble();
                scanner.nextLine();

                account = new TaskManager(id, initialBalance); // создание счета
            }catch (InputMismatchException e){
                    System.out.println("Ошибка ввода! Введите корректное число");
                    scanner.nextLine(); // Очистка ошибки
                }catch (IllegalArgumentException e){
                    System.out.println("Ошибка: " + e.getMessage());
                }
        }
            while (true) {
                printMenu();

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> {
                            System.out.print("Введите сумму для внесения: ");
                            double amount = scanner.nextDouble();
                            scanner.nextLine();
                            account.deposit(amount);
                        }
                        case 2 -> {
                            System.out.print("Введите сумму для снятия: ");
                            double amount = scanner.nextDouble();
                            scanner.nextLine();
                            try {
                                account.withdraw(amount);
                            } catch (TaskValidationException e) {
                                System.out.println("Ошибка: " + e.getMessage());
                                System.out.println("Недостаток средств: " + e.getDeficit());
                            }
                        }
                        case 3 -> System.out.println("Текущий баланс: " + account.getBalance());
                        case 4 -> {
                            System.out.println("Выход из программы.");
                            return;
                        }
                        default -> System.out.println("Некорректный выбор, попробуйте снова.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка ввода! Введите число.");
                    scanner.nextLine(); // Очистка ошибки
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Меню ===");
        System.out.println("1. Внести деньги");
        System.out.println("2. Снять деньги");
        System.out.println("3. Проверить баланс");
        System.out.println("4. Выход");
        System.out.println("==============");
        System.out.print("Выберите действие: ");
    }

}