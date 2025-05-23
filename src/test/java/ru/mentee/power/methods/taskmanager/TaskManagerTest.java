package ru.mentee.power.methods.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Тесты для класса TaskManager
 */
class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();

        // Добавляем тестовые задачи
        taskManager.addTask("Срочная задача", "Выполнить скорее", createDate(2023, 5, 15), Task.Priority.HIGH);
        taskManager.addTask("Обычная задача", "В течение недели", createDate(2023, 6, 1), Task.Priority.MEDIUM);
        taskManager.addTask("Несрочная задача", "Когда будет время", createDate(2023, 7, 1), Task.Priority.LOW);
        taskManager.addTask("Задача без описания");
    }

    @Test
    void testGetTaskById() {
        TaskManager taskManager = new TaskManager();
        Date dueDate = createDate(2025, 5, 25);

        Task task = taskManager.addTask("Сделать дз", "Поскорее", dueDate, Task.Priority.HIGH);
        Task foundTask = taskManager.getTaskById(task.getId());

        assertThat(foundTask).isNotNull()
                .hasFieldOrPropertyWithValue("id", task.getId());

        // Проверка случая, когда задачи нет
        assertThat(taskManager.getTaskById(999)).isNull();
    }
    // Не существующее id
    @Test
    void testGetTaskByInvalidId() {
        TaskManager taskManager = new TaskManager();

        Task task = taskManager.getTaskById(999);

        assertThat(task).isNull();
    }


    // Удаление задачи по несуществующему ID
    @Test
    void testRemoveTaskByInvalidId() {
        TaskManager taskManager = new TaskManager();

        boolean result = taskManager.removeTask(999);

        assertThat(result).isFalse();
    }

    // Поиск задач по пустой строке
    @Test
    void testSearchTasksEmptyQuery() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Сделать дз", "Поскорее", createDate(2025, 5, 25), Task.Priority.HIGH);

        List<Task> searchResult = taskManager.searchTasks("");

        assertThat(searchResult).isNotEmpty();
    }
    // Проверка просроченной задачи
    @Test
    void testIsOverdueTask() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.addTask("Просроченная задача", "Истекший срок", createDate(2024, 5, 1), Task.Priority.HIGH);

        boolean isOverdue = task.isOverdue();

        assertThat(isOverdue).isTrue();
    }
    // Сортировка задач с одинаковым приоритетом
    @Test
    void testSortTasksWithSamePriority() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Задача 1", "Описание 1", createDate(2025, 5, 21), Task.Priority.HIGH);
        taskManager.addTask("Задача 2", "Описание 2", createDate(2025, 5, 20), Task.Priority.HIGH);

        List<Task> sortedTasks = taskManager.sortTasksByPriority();

        assertThat(sortedTasks.get(0).getDueDate()).isBefore(sortedTasks.get(1).getDueDate());
    }
    // Маркировка задачи как выполненной
    @Test
    void testMarkAsCompleted() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.addTask("Завершаем задачу", "Тест выполнения", createDate(2025, 6, 1), Task.Priority.MEDIUM);

        task.markAsCompleted();

        assertThat(task.isCompleted()).isTrue();
    }



    /**
     * Тест получения задач по приоритету
     */
    @Test
    void testGetTasksByPriority() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Сделать дз", "Поскорее", createDate(2025, 5, 25), Task.Priority.HIGH);
        taskManager.addTask("Купить продукты", "Список покупок", createDate(2025, 5, 22), Task.Priority.LOW);

        List<Task> highPriorityTasks = taskManager.getTasksByPriority(Task.Priority.HIGH);

        assertThat(highPriorityTasks).hasSize(1)
                .extracting(Task::getTitle)
                .containsExactly("Сделать дз");
    }

    /**
     * Тест поиска задач
     */
    @Test
    void testSearchTasks() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Сделать дз", "Поскорее", createDate(2025, 5, 25), Task.Priority.HIGH);
        taskManager.addTask("Купить продукты", "Список покупок", createDate(2025, 5, 22), Task.Priority.LOW);

        List<Task> searchResult = taskManager.searchTasks("дз");

        assertThat(searchResult).hasSize(1)
                .extracting(Task::getTitle)
                .containsExactly("Сделать дз");

        // Проверка случая, когда задачи нет
        assertThat(taskManager.searchTasks("несуществующая")).isEmpty();
    }

    /**
     * Тест сортировки задач по приоритету
     */
    @Test
    void testSortTasksByPriority() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Сделать дз", "Поскорее", createDate(2025, 5, 25), Task.Priority.HIGH);
        taskManager.addTask("Купить продукты", "Список покупок", createDate(2025, 5, 22), Task.Priority.LOW);

        List<Task> sortedTasks = taskManager.sortTasksByPriority();

        assertThat(sortedTasks).extracting(Task::getPriority)
                .containsExactly(Task.Priority.LOW, Task.Priority.HIGH);
    }







    /**
     * Вспомогательный метод для создания даты
     */
    private static Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }
}