package ru.mentee.power.methods.taskmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskManagerDemo {
    public static void main(String[] args) throws ParseException {
        TaskManager taskManager = new TaskManager();

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Date dueDate1 = formatter.parse("25.05.2025 15:30");
        Date dueDate2 = formatter.parse("22.05.2025 18:00");
        Date dueDate3 = formatter.parse("30.05.2025 10:15");

        taskManager.addTask("Сделать дз", "поскорее", dueDate1, Task.Priority.HIGH);
        taskManager.addTask("Прочитать книгу", "Глава 5", dueDate2, Task.Priority.MEDIUM);
        taskManager.addTask("Сходить в магазин", "Купить продукты", dueDate3, Task.Priority.LOW);

        System.out.println("\n🔹 Поиск задачи 'Прочитать книгу':");
        System.out.println(taskManager.searchTasks("Прочитать книгу"));

        System.out.println("\n🔹 Фильтрация задач по приоритету HIGH:");
        taskManager.getTasksByPriority(Task.Priority.HIGH).forEach(System.out::println);

        System.out.println("\n🔹 Сортировка задач по приоритету:");
        taskManager.sortTasksByPriority().forEach(System.out::println);

        System.out.println("\n🔹 Сортировка задач по дате выполнения:");
        taskManager.sortTasksByDueDate().forEach(System.out::println);

        Task completedTask = taskManager.getTaskById(1); // Получаем первую задачу
        if (completedTask != null) {
            completedTask.markAsCompleted();
            System.out.println("\n✅ Задача 'Сделать дз' теперь выполнена!");
        }

        // Вывод всех задач после изменений
        System.out.println("\n🔹 Обновлённый список задач:");
        taskManager.printAllTasks();
        
        
    }

}
