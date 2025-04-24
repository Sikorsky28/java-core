package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SmartThermostat {

    private static final double ERROR_TEMP_CODE = -100.0;

    private static final List<String> WEEKDAYS = Arrays.asList("Понедельник", "Вторник","Среда","Четверг","Пятница");
    private static final List<String> WEEKENDS = Arrays.asList("Суббота", "Воскресенье");


    public double getTargetTemperature(int timeOfDay, String dayOfWeek,
                                       boolean isOccupied, double outsideTemp) {
        // Проверка корректности входных данных
        if (timeOfDay < 0 || timeOfDay > 23 ||
                (!WEEKDAYS.contains(dayOfWeek) && !WEEKENDS.contains(dayOfWeek))) {
            return ERROR_TEMP_CODE;
        }
        double baseTemp = calculateBaseTemperature(timeOfDay, dayOfWeek, isOccupied);
        // Применение погодных корректировок
        return applyWeatherCorrection(baseTemp, outsideTemp);
    }




    private double calculateBaseTemperature(int timeOfDay, String dayOfWeek, boolean isOccupied) {
        if (WEEKDAYS.contains(dayOfWeek)) {
            // Рабочие дни
            if (timeOfDay >= 6 && timeOfDay <= 8) { // Утро
                return isOccupied ? 22.0 : 18.0;
            } else if (timeOfDay >= 9 && timeOfDay <= 17) { // День
                return isOccupied ? 20.0 : 16.0;
            } else if (timeOfDay >= 18 && timeOfDay <= 22) { // Вечер
                return isOccupied ? 22.0 : 17.0;
            } else { // Ночь (23-5)
                return isOccupied ? 19.0 : 16.0;
            }
        } else {
            // Выходные дни
            if (timeOfDay >= 7 && timeOfDay <= 9) { // Утро
                return isOccupied ? 23.0 : 18.0;
            } else if (timeOfDay >= 10 && timeOfDay <= 17) { // День
                return isOccupied ? 22.0 : 17.0;
            } else if (timeOfDay >= 18 && timeOfDay <= 23) { // Вечер
                return isOccupied ? 22.0 : 17.0;
            } else { // Ночь (0-6)
                return isOccupied ? 20.0 : 16.0;
            }
        }
    }

    private double applyWeatherCorrection(double baseTemp, double outsideTemp) {
        if (outsideTemp > 25.0) {
            return baseTemp + 1.0;
        } else if (outsideTemp < 0.0) {
            return baseTemp - 1.0;
        }
        return baseTemp;
    }




    public static void main(String[] args) {
        SmartThermostat thermostat = new SmartThermostat();
        Scanner scanner = new Scanner(System.in);


         System.out.println("Введите время суток (0-23):");
         int time = scanner.nextInt();
         scanner.nextLine();
         System.out.println("Введите день недели: ");
         String day = scanner.nextLine();
         System.out.println("Дома кто-то есть? (true/false): ");
         Boolean occupied = scanner.nextBoolean();
         System.out.println("Введите температуру на улице: ");
         double outsideTemp = scanner.nextDouble();

         double targetTemp = thermostat.getTargetTemperature(time, day, occupied, outsideTemp);
         if (targetTemp == ERROR_TEMP_CODE) {
             System.out.println("Ошибка: Некорректные входные данные.");
         } else {
             System.out.println("Рекомендуемая температура: " + targetTemp + "°C");
         }

        scanner.close();
    }

}