package ru.mentee.power.methods.taskmanager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для управления задачами
 */
public class TaskManager {
    private List<Task> tasks;
    private int nextId = 1;

    /**
     * Конструктор
     */
    public TaskManager() {
        tasks = new ArrayList<>();
    }

    /**
     * Добавление задачи с полным набором параметров
     */
    public Task addTask(String title, String description, Date dueDate, Task.Priority priority) {
        Task task = new Task(nextId, title, description, dueDate, priority);
        tasks.add(task);
        nextId++;
        return task;
    }

    /**
     * Добавление задачи только с названием (перегрузка)
     */
    public Task addTask(String title) {
        return addTask(title, "", null, Task.Priority.MEDIUM );

    }

    /**
     * Добавление задачи с названием и описанием (перегрузка)
     */
    public Task addTask(String title, String description) {
        return addTask(title, description, null, Task.Priority.MEDIUM );
    }

    /**
     * Получение задачи по ID
     */
    public Task getTaskById(int id) {
        for (Task task : tasks){
            if (task.getId() == id){
                return task;
            }
        }
        return null;
    }

    /**
     * Удаление задачи по ID
     */
    public boolean removeTask(int id) {

        for (Task task : tasks){
            if (task.getId() == id){
                tasks.remove(task);
                return true;
            }
        }
        return false;
    }

    /**
     * Маркировка задачи как выполненной
     */
    public boolean markTaskAsCompleted(int id) {
        Task task = getTaskById(id);
        if(task != null){
            task.markAsCompleted();
            return true;
        }


        return false;
    }

    /**
     * Получение всех задач
     */
    public List<Task> getAllTasks() {

        return  new ArrayList<>(tasks);
    }

    /**
     * Получение выполненных задач
     */
    public List<Task> getCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks){
            if (task.isCompleted() == true){
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    /**
     * Получение невыполненных задач
     */
    public List<Task> getIncompleteTasks() {
        List<Task> incompleteTasks = new ArrayList<>();
        for (Task task : tasks){
            if (!task.isCompleted()){
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }

    /**
     * Получение просроченных задач
     */
    public List<Task> getOverdueTasks() {
        List<Task> overdueTasks = new ArrayList<>();
        for (Task task : tasks){
            if (task.isOverdue() && !task.isCompleted()){
                overdueTasks.add(task);
            }
        }
        return overdueTasks;
    }

    /**
     * Получение задач с заданным приоритетом
     */
    public List<Task> getTasksByPriority(Task.Priority priority) {
        List<Task> tasksByPriority = new ArrayList<>();
        for (Task task : tasks){
            if (task.getPriority().equals(priority)){
                tasksByPriority.add(task);
            }
        }
        return tasksByPriority;
    }

    /**
     * Поиск задач по фрагменту названия или описания
     */
    public List<Task> searchTasks(String query) {
        List<Task> foundTask = new ArrayList<>();
        for (Task task : tasks){
            if(task.getTitle().toLowerCase().contains(query.toLowerCase()) ||
               task.getDescription().toLowerCase().contains(query.toLowerCase())){
                foundTask.add(task);
            }
        }
        return foundTask;
    }

    /**
     * Сортировка задач по сроку выполнения
     * Использует алгоритм сортировки пузырьком из блока циклов
     */
    public List<Task> sortTasksByDueDate() {
        List<Task> sortedTasks = new ArrayList<>(getAllTasks());
        boolean swapped;
        for (int i = 0; i < sortedTasks.size() - 1; i++) {
            swapped = false;
            for (int j = 0; j < sortedTasks.size() - i - 1; j++) {
               if (sortedTasks.get(j).getDueDate().after(sortedTasks.get(j + 1).getDueDate())){
                   Task temp = sortedTasks.get(j);
                   sortedTasks.set(j, sortedTasks.get(j + 1));
                   sortedTasks.set(j +1, temp);
                   swapped = true;
                }
            }
            if (!swapped) break;
        }

        return sortedTasks;
    }

    /**
     * Сортировка задач по приоритету
     * Использует алгоритм сортировки вставками из блока циклов
     */
    public List<Task> sortTasksByPriority() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getPriority)
                        .thenComparing(task -> task.getDueDate().getTime())) // Сравниваем по числовому значению времени
                .collect(Collectors.toList());
    }


    /**
     * Вывод всех задач в консоль
     */
    public void printAllTasks() {
        for (Task task : tasks){
            System.out.println(task);
        }
    }

    /**
     * Вывод задач с указанным заголовком
     */
    public void printTasks(List<Task> taskList, String header) {
        System.out.println("=== " + header + " ===");

        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }
}