package conditions;

import java.util.Scanner;

public class TrafficLight {

    /**
     * Возвращает рекомендацию для пешехода в зависимости от сигнала светофора.
     *
     * @param signal строковое представление сигнала ("Красный", "Желтый", "Зеленый")
     * @return Рекомендация для пешехода
     */
    public static String getRecommendation(String signal) {

        // "Красный" -> "Стой на месте!"
        // "Желтый" -> "Приготовься, но подожди!"
        // "Зеленый" -> "Можно переходить дорогу!"
        // Иначе -> "Некорректный сигнал!"
        // Используйте equalsIgnoreCase для сравнения строк без учета регистра
        if (signal == null) {
            return "Некорректный сигнал!";
        }
        if (signal.equalsIgnoreCase("Красный")) {
            return "Стой на месте!";
        } else if (signal.equalsIgnoreCase("Желтый")) {
            return "Приготовься, но подожди!";
        } else if (signal.equalsIgnoreCase("Зеленый")) {
            return "Можно переходить дорогу!";
        } else {
            return "Некорректный сигнал!";
        }
        /* Или через switch (начиная с Java 14 для строк)
        switch (signal.toLowerCase()) { // Приводим к нижнему регистру для надежности
            case "красный":
                return "Стой на месте!";
            case "желтый":
                return "Приготовься, но подожди!";
            case "зеленый":
                return "Можно переходить дорогу!";
            default:
                return "Некорректный сигнал!";
        }
        */
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Какой сейчас сигнал светофора (Красный, Желтый, Зеленый)?");
        System.out.print("Введите название сигнала: ");

        String signal = scanner.nextLine(); // Считываем строку
        String recommendation = getRecommendation(signal); // Вызываем наш метод
        System.out.println(recommendation);

        scanner.close();
    }
}