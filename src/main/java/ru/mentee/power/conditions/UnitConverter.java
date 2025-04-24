package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UnitConverter {

    private static final double ERROR_CODE = -1.0;

    private static final List<String> LENGTH_UNITS = Arrays.asList( "Метр", "Сантиметр", "Дюйм", "Фут");
    private static final List<String> WEIGHT_UNITS = Arrays.asList( "Килограмм", "Грамм", "Фунт", "Унция");
    private static final List<String> TEMP_UNITS = Arrays.asList( "Цельсий", "Фаренгейт", "Кельвин");

    /**
     * Конвертирует значение из одной единицы измерения в другую
     *
     * @param value значение для конвертации
     * @param fromUnit исходная единица измерения
     * @param toUnit целевая единица измерения
     * @return конвертированное значение или ERROR_CODE в случае ошибки
     */
    public double convert(double value, String fromUnit, String toUnit) {
        // 1. Проверка поддерживаемых единиц
        if (!LENGTH_UNITS.contains(fromUnit) && !WEIGHT_UNITS.contains(fromUnit) && !TEMP_UNITS.contains(fromUnit)) {
            return ERROR_CODE;
        }

        if (!LENGTH_UNITS.contains(toUnit) && !WEIGHT_UNITS.contains(toUnit) && !TEMP_UNITS.contains(toUnit)) {
            return ERROR_CODE;
        }

        // 2. Проверка категорий
        if (LENGTH_UNITS.contains(fromUnit) && LENGTH_UNITS.contains(toUnit)) {
            return convertLength(value, fromUnit, toUnit);
        } else if (WEIGHT_UNITS.contains(fromUnit) && WEIGHT_UNITS.contains(toUnit)) {
            return convertWeight(value, fromUnit, toUnit);
        } else if (TEMP_UNITS.contains(fromUnit) && TEMP_UNITS.contains(toUnit)) {
            return convertTemperature(value, fromUnit, toUnit);
        } else {
            return ERROR_CODE; // Несоответствие категорий
        }
    }




    private boolean areSameCategory(String unit1, String unit2) {
        String category1 = getCategory(unit1);
        String category2 = getCategory(unit2);

        return category1 != null && category1.equals(category2);
    }



    private String getCategory(String unit) {
        if (LENGTH_UNITS.contains(unit)) {
            return "Длина";
        } else if (WEIGHT_UNITS.contains(unit)) {
            return "Вес";
        } else if (TEMP_UNITS.contains(unit)) {
            return "Температура";
        } else {
            return null; // Единица измерения не поддерживается
        }
    }



    private double convertLength(double value, String fromUnit, String toUnit) {
        // Коэффициенты перевода в метры
        Map<String, Double> toMeters = new HashMap<>();
        toMeters.put("Метр", 1.0);
        toMeters.put("Сантиметр", 0.01);
        toMeters.put("Дюйм", 0.0254);
        toMeters.put("Фут", 0.3048);

        // Проверка наличия единиц
        if (!toMeters.containsKey(fromUnit) || !toMeters.containsKey(toUnit)) {
            return ERROR_CODE;
        }

        // Конвертация в метры
        double meters = value * toMeters.get(fromUnit);

        // Конвертация из метров в целевую единицу
        return meters / toMeters.get(toUnit);
    }


    private double convertWeight(double value, String fromUnit, String toUnit) {
        Map<String, Double> toKilograms = new HashMap<>();
        toKilograms.put("Килограмм", 1.0);
        toKilograms.put("Грамм", 0.001);
        toKilograms.put("Фунт", 0.453592);
        toKilograms.put("Унция", 0.0283495);

        if (!toKilograms.containsKey(fromUnit) || !toKilograms.containsKey(toUnit)) {
            return ERROR_CODE;
        }

        double kilograms = value * toKilograms.get(fromUnit);
        return kilograms / toKilograms.get(toUnit);
    }



    private double convertTemperature(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) {
            return value; // Если единицы одинаковы, возвращаем то же значение
        }

        switch (fromUnit) {
            case "Цельсий":
                if (toUnit.equals("Фаренгейт")) return (value * 9/5) + 32;
                if (toUnit.equals("Кельвин")) return value + 273.15;
                break;
            case "Фаренгейт":
                if (toUnit.equals("Цельсий")) return (value - 32) * 5/9;
                if (toUnit.equals("Кельвин")) return ((value - 32) * 5/9) + 273.15;
                break;
            case "Кельвин":
                if (toUnit.equals("Цельсий")) return value - 273.15;
                if (toUnit.equals("Фаренгейт")) return ((value - 273.15) * 9/5) + 32;
                break;
        }

        return ERROR_CODE; // Если единицы не поддерживаются
    }





        public static void main(String[] args) {
            UnitConverter converter = new UnitConverter();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите значение:");
            while (!scanner.hasNextDouble()) {  // Проверяем ввод на корректность
                System.out.println("Ошибка! Введите числовое значение:");
                scanner.next();
            }
            double val = scanner.nextDouble();

            System.out.println("Введите исходную единицу:");
            String from = scanner.next();

            System.out.println("Введите целевую единицу:");
            String to = scanner.next();

            double result = converter.convert(val, from, to);

            if (result == UnitConverter.ERROR_CODE) {
                System.out.println("Ошибка конвертации! Проверьте корректность единиц измерения.");
            } else {
                System.out.printf("Результат: %.2f %s\n", result, to);
            }

            scanner.close();
        }
    }

