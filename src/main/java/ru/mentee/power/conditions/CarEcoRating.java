package ru.mentee.power.conditions;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CarEcoRating {

    // 🔹 Константы (общие для всех объектов)
    private static final int ERROR_CODE = -1;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 100;
    private static final int EURO_STANDARD_YEAR_THRESHOLD = 2020;

    private static final int BASE_RATING_ELECTRIC = 90;
    private static final int BASE_RATING_HYBRID = 70;
    private static final int BASE_RATING_DIESEL = 40;
    private static final int BASE_RATING_PETROL = 30;

    private static final List<String> VALID_FUEL_TYPES = Arrays.asList("Бензин", "Дизель", "Гибрид", "Электро");


    public int calculateEcoRating(String fuelType, double engineVolume,
                                  double fuelConsumption, int yearOfManufacture,
                                  boolean isEuroCompliant) {
        if (!validateInput(fuelType, engineVolume, fuelConsumption, yearOfManufacture)) {
            return ERROR_CODE;
        }

        int baseFuelRating = getBaseFuelTypeRating(fuelType);
        int modifiedRating = applyRatingModifiers(baseFuelRating, fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);
        return clampRating(modifiedRating);
    }


    private boolean validateInput(String fuelType, double engineVolume, double fuelConsumption, int yearOfManufacture) {
        int currentYear = Year.now().getValue();
        return VALID_FUEL_TYPES.contains(fuelType) &&
                engineVolume >= 0 &&
                fuelConsumption >= 0 &&
                yearOfManufacture >= 1900 && yearOfManufacture <= currentYear;
    }


    private int getBaseFuelTypeRating(String fuelType) {
        switch (fuelType) {
            case "Бензин": return BASE_RATING_PETROL;
            case "Дизель": return BASE_RATING_DIESEL;
            case "Гибрид": return BASE_RATING_HYBRID;
            case "Электро": return BASE_RATING_ELECTRIC;
            default: return ERROR_CODE;
        }
    }


    private int applyRatingModifiers(int baseRating, String fuelType, double engineVolume,
                                     double fuelConsumption, int yearOfManufacture, boolean isEuroCompliant) {
        int totalRating = baseRating;

        if (fuelType.equals("Бензин") || fuelType.equals("Дизель")) {
            totalRating -= (int) (engineVolume * 5);
        }

        if (fuelType.equals("Бензин") || fuelType.equals("Дизель")) {
            totalRating -= (int) (fuelConsumption * 2);
        }

        if (fuelType.equals("Электро")) {
            totalRating -= (int) (fuelConsumption * 0.5);
        }

        if (yearOfManufacture < EURO_STANDARD_YEAR_THRESHOLD) {
            totalRating -= (EURO_STANDARD_YEAR_THRESHOLD - yearOfManufacture);
        }

        if (isEuroCompliant && (fuelType.equals("Бензин") || fuelType.equals("Дизель") || fuelType.equals("Электро"))) {
            totalRating += 10;
        }

        if (fuelType.equals("Гибрид") && fuelConsumption < 5) {
            totalRating += 15;
        }

        System.out.println("Рейтинг после модификаций: " + totalRating);
        System.out.println("Перед округлением: " + Math.round(totalRating));

        return (int) Math.round(totalRating);
    }


    private int clampRating(int rating) {
        return Math.max(MIN_RATING, Math.min(MAX_RATING, rating));
    }

    public static void main(String[] args) {
        CarEcoRating ecoRating = new CarEcoRating();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите тип топлива (Бензин, Дизель, Гибрид, Электро):");
        String type = scanner.next();

        System.out.println("Введите объем двигателя (л):");
        double volume = scanner.nextDouble();

        System.out.println("Введите расход топлива (л/100км):");
        double consumption = scanner.nextDouble();

        System.out.println("Введите год выпуска:");
        int year = scanner.nextInt();

        System.out.println("Соответствует ли Евро-6? (true/false):");
        boolean isEuro = scanner.nextBoolean();

        int rating = ecoRating.calculateEcoRating(type, volume, consumption, year, isEuro);

        if (rating == ERROR_CODE) {
            System.out.println("Ошибка: некорректные данные!");
        } else {
            System.out.println("Экологический рейтинг автомобиля: " + rating);
        }

        scanner.close();
    }
}
