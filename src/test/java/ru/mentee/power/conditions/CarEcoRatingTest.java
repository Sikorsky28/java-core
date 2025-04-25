package ru.mentee.power.conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

class CarEcoRatingTest {

    private CarEcoRating ratingCalculator;
    private static final int ERROR = -1;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 100;

    @BeforeEach
    void setUp() {
        ratingCalculator = new CarEcoRating();
    }

    @Test
    @DisplayName("Расчет рейтинга для современного электромобиля")
    void calculateRatingForModernElectricCar() {
        // Arrange
        String fuelType = "Электро";
        double engineVolume = 0.0;
        double fuelConsumption = 15.0; // кВтч/100км
        int yearOfManufacture = 2023;
        boolean isEuroCompliant = false; // Не применимо

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert

        int expectedRating = 83;
        assertThat(rating).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("Расчет рейтинга для эффективного гибрида Евро-6")
    void calculateRatingForEfficientEuro6Hybrid() {
        // Arrange
        String fuelType = "Гибрид";
        double engineVolume = 1.5;
        double fuelConsumption = 4.0;
        int yearOfManufacture = 2021;
        boolean isEuroCompliant = false;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        int expectedRating = 85; // Корректное ожидаемое значение
        assertThat(rating).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("Расчет рейтинга для старого дизельного автомобиля не Евро-6")
    void calculateRatingForOldDieselCarNotEuro6() {
        // Arrange
        String fuelType = "Дизель";
        double engineVolume = 2.5;
        double fuelConsumption = 8.0;
        int yearOfManufacture = 2015;
        boolean isEuroCompliant = false;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert

        assertThat(rating).isEqualTo(7);
    }

    @Test
    @DisplayName("Верхняя граница рейтинга (максимум 100)")
    void ensureMaximumRatingIs100() {
        // Arrange - создаем идеальный электромобиль будущего
        String fuelType = "Электро";
        double engineVolume = 0.0;
        double fuelConsumption = 0.1; // Почти нулевой расход
        int yearOfManufacture = Year.now().getValue(); // Текущий год
        boolean isEuroCompliant = true; // Предположим, что аргумент учитывается для электро

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert

        assertThat(rating).isEqualTo(MAX_RATING);
    }

    @Test
    @DisplayName("Нижняя граница рейтинга (минимум 1)")
    void ensureMinimumRatingIs1() {
        // Arrange - создаем крайне неэффективный старый автомобиль
        String fuelType = "Бензин";
        double engineVolume = 7.0; // Огромный объем
        double fuelConsumption = 25.0; // Огромный расход
        int yearOfManufacture = 1980; // Очень старый
        boolean isEuroCompliant = false;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert


        assertThat(rating).isEqualTo(1);
    }

    @Test
    @DisplayName("Обработка неизвестного типа топлива")
    void handleUnknownFuelType() {
        // Arrange
        String fuelType = "Водород"; // Неизвестный тип топлива
        double engineVolume = 0.0;
        double fuelConsumption = 10.0;
        int yearOfManufacture = 2020;
        boolean isEuroCompliant = false;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert

        assertThat(rating).isEqualTo(ERROR);
    }

    @Test
    @DisplayName("Обработка отрицательного объема двигателя")
    void handleNegativeEngineVolume() {
        // Arrange
        String fuelType = "Бензин";
        double engineVolume = -2.0;
        double fuelConsumption = 10.0;
        int yearOfManufacture = 2020;
        boolean isEuroCompliant = false;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        assertThat(rating).isEqualTo(ERROR);
    }

    @Test
    @DisplayName("Обработка отрицательного расхода топлива")
    void handleNegativeFuelConsumption() {
        // Arrange
        String fuelType = "Дизель";
        double engineVolume = 2.0;
        double fuelConsumption = -5.0; // Отрицательный расход
        int yearOfManufacture = 2018;
        boolean isEuroCompliant = true;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        assertThat(rating).isEqualTo(ERROR);
    }

    @Test
    @DisplayName("Обработка года выпуска в будущем")
    void handleFutureYearOfManufacture() {
        // Arrange
        String fuelType = "Гибрид";
        double engineVolume = 1.6;
        double fuelConsumption = 5.5;
        int yearOfManufacture = Year.now().getValue() + 5; // Год в будущем
        boolean isEuroCompliant = true;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        assertThat(rating).isEqualTo(ERROR);
    }
}