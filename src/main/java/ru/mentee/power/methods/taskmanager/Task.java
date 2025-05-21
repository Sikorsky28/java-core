package ru.mentee.power.methods.taskmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Класс, представляющий задачу
 */
public class Task {
    private int id;             // Уникальный идентификатор
    private String title;       // Название задачи
    private String description; // Описание задачи
    private Date dueDate;       // Срок выполнения
    private Priority priority;  // Приоритет
    private boolean completed;  // Статус выполнения

    /**
     * Приоритет задачи
     */
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    /**
     * Конструктор с полным набором параметров
     */
    public Task(int id, String title, String description, Date dueDate, Priority priority) {
        this.id = id;
        this. title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    /**
     * Конструктор с минимальным набором параметров (перегрузка)
     */
    public Task(int id, String title) {
        this.id = id;
        this. title = title;
    }

    /**
     * Конструктор с частичным набором параметров (перегрузка)
     */
    public Task(int id, String title, String description) {
        this.id = id;
        this. title = title;
        this.description = description;

    }


    /**
     * Метод для получения ID задачи
     */
    public int getId() {
        return id;
    }

    /**
     * Метод для получения названия задачи
     */
    public String getTitle() {

        return title;
    }
    /**
     * Метод для получения описания задачи
     */
    public String getDescription() {
        return description;
    }
    /**
     * Метод для получения даты выполнения
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Метод для получения приоретета задачи
     */
    public Priority getPriority() {
        return priority;
    }
    /**
     * Метод для получения информации о выполнения задачи
     */
    public boolean isCompleted() {
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Метод для установки даты выполнения из строки
     */
    public void setDueDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        this.dueDate = formatter.parse(dateStr); // Конвертируем строку в `Date`
    }

    /**
     * Метод для установки даты выполнения из объекта `Date`
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Метод для получения отформатированной даты выполнения
     */
    public String getFormattedDueDate() {
        if (dueDate == null) return "Не назначено";
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(dueDate);
    }


    /**
     * Метод для маркировки задачи как выполненной
     */
    public void markAsCompleted() {
        this.completed = true;
    }

    /**
     * Метод для проверки, просрочена ли задача
     */
    public boolean isOverdue() {
        // Если дата выполнения раньше текущей, задача просрочена
        if (dueDate == null) return false;
        Date currentDate = new Date();

       return dueDate != null && dueDate.before(currentDate);

    }

    /**
     * Переопределение метода toString для удобного отображения задачи
     */
    @Override
    public String toString() {
        return String.format("Задача: %s | Описание: %s | Срок: %s | Приоритет: %s | Выполнена: %s",
                title,
                description != null ? description : "Нет описания",
                getFormattedDueDate(),
                priority,
                completed ? "Да" : "Нет");
    }

}