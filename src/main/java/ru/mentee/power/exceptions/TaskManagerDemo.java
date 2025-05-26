// Каркас TaskManagerDemo.java (ЭТОТ ФАЙЛ НУЖНО МОДИФИЦИРОВАТЬ)
package ru.mentee.power.exceptions;

import ru.mentee.power.methods.taskmanager.Task; // Импорт из пакета методов
import ru.mentee.power.methods.taskmanager.TaskManager; // Импорт из пакета методов

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Демонстрационное приложение для работы с TaskManager (из MP-44),
 * которое нужно улучшить с помощью обработки исключений.
 */
public class TaskManagerDemo {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager taskManager = new TaskManager(); // Используем оригинальный TaskManager

    public static void main(String[] args) {
        // Предварительно добавим несколько задач для демонстрации
        initializeTasks();

        boolean running = true;
        System.out.println("Добро пожаловать в Менеджер Задач (с базовой обработкой ошибок)!");

        while (running) {
            printMenu();
            int choice = -1;

            try {
                System.out.print("Выберите действие (1-5): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Поглотить перевод строки
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода: Пожалуйста, введите номер действия (число).");
                scanner.nextLine(); // Очистить буфер сканера
                continue; // Перейти к следующей итерации
            }


            switch (choice) {
                case 1:
                    addTaskUI();
                    break;
                case 2:
                    markTaskAsCompletedUI();
                    break;
                case 3:
                    removeTaskUI();
                    break;
                case 4:
                    taskManager.printAllTasks();
                    break;
                case 5:
                    running = false;
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите от 1 до 5.");
            }
            System.out.println(); // Пустая строка для разделения
        }

        scanner.close(); // Закрываем сканер после завершения программы
    }

    // --- Вспомогательные методы для UI ---

    private static void addTaskUI() {
        try {
            System.out.print("Введите название задачи: ");
            String title = scanner.nextLine();
            // Для простоты добавим только название
            Task newTask = taskManager.addTask(title); // Используем простейший addTask
            if (newTask != null) { // addTask может вернуть null в некоторых реализациях MP-44, лучше проверить
                System.out.println("Задача '" + newTask.getTitle() + "' (ID: " + newTask.getId() + ") успешно добавлена.");
            } else {
                System.out.println("Не удалось добавить задачу."); // Маловероятно с addTask(title), но для полноты
            }
        } catch (Exception e) { // Общий перехват на случай других проблем
            System.out.println("Неожиданная ошибка при добавлении задачи: " + e.getMessage());
        }
    }

    private static void markTaskAsCompletedUI() {
        try {
            System.out.print("Введите ID задачи для отметки как выполненной: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Поглотить перевод строки


            if (id <= 0) {
                throw new IllegalArgumentException("ID задачи не может быть отрицательным или нулем.");
            }

            // Используем оригинальный метод markTaskAsCompleted из MP-44
            boolean success = taskManager.markTaskAsCompleted(id);

            if (success) {
                System.out.println("Задача с ID " + id + " отмечена как выполненная.");
            } else {
                System.out.println("Задача с ID " + id + " не найдена."); // Метод вернул false
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: Пожалуйста, введите ID задачи (целое число).");
            scanner.nextLine(); // Очистить буфер сканера
        } catch (IllegalArgumentException e) { // Ловим наше исключение
            System.out.println("Ошибка: " + e.getMessage());

            // Например, если бы мы сначала получили Task task = getTaskById(id), а потом вызвали task.markAsCompleted() без проверки на null
        } catch (NullPointerException e) {
                System.out.println("Критическая ошибка: Попытка работы с несуществующей задачей (NullPointerException).");
        } catch (Exception e) { // Общий перехват
             System.out.println("Неожиданная ошибка при отметке задачи: " + e.getMessage());
        }
    }

    private static void removeTaskUI() {
        try {
            System.out.print("Введите ID задачи для удаления: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Поглотить перевод строки

            if (id <= 0) {
                throw new IllegalArgumentException("ID задачи не может быть отрицательным или нулем.");
            }

            // Используем оригинальный метод removeTask из MP-44
            boolean success = taskManager.removeTask(id);

            if (success) {
                System.out.println("Задача с ID " + id + " удалена.");
            } else {
                System.out.println("Задача с ID " + id + " не найдена."); // Метод вернул false
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: Пожалуйста, введите ID задачи (целое число).");
            scanner.nextLine(); // Очистить буфер сканера
        } catch (IllegalArgumentException e) { // Ловим наше исключение
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) { // Общий перехват
            System.out.println("Неожиданная ошибка при удалении задачи: " + e.getMessage());
        }
    }


    private static void printMenu() {
        System.out.println("===== МЕНЮ =====");
        System.out.println("1. Добавить задачу (только название)");
        System.out.println("2. Отметить задачу как выполненную");
        System.out.println("3. Удалить задачу");
        System.out.println("4. Показать все задачи");
        System.out.println("5. Выход");
        System.out.println("==============");
    }

    private static void initializeTasks() {
        // Добавим пару задач для начала
        taskManager.addTask("Изучить исключения", "Понять try-catch-finally", null, Task.Priority.HIGH);
        taskManager.addTask("Попрактиковаться с TaskManager");
        System.out.println("Добавлены начальные задачи.");
        taskManager.printAllTasks();
        System.out.println();
    }
}
