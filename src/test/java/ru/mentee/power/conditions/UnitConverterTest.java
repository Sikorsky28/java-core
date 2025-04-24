package ru.mentee.power.conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class UnitConverterTest {

    private UnitConverter converter;
    private static final double DELTA = 0.001; // Допустимая погрешность
    private static final double ERROR = -1.0;  // Код ошибки

    @BeforeEach
    void setUp() {
        converter = new UnitConverter();
    }

    @Test
    @DisplayName("Конвертация из метров в сантиметры")
    void convertMetresToCentimetres() {
        // Arrange
        double value = 1.0;
        String fromUnit = "Метр";
        String toUnit = "Сантиметр";

        // Act
        double convertedValue = converter.convert(value, fromUnit, toUnit);

        // Assert
        assertThat(convertedValue).isCloseTo(100.0, within(DELTA)); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Конвертация из сантиметров в метры")
    void convertCentimetresToMetres() {
        // Arrange
        double value = 150.0;
        String fromUnit = "Сантиметр";
        String toUnit = "Метр";

        // Act
        double convertedValue = converter.convert(value, fromUnit, toUnit);

        // Assert
        // 150 см = 1.5 м
        assertThat(convertedValue).isCloseTo(1.5, within(DELTA)); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Конвертация из метров в футы")
    void convertMetresToFeet() {
        // Arrange
        double value = 2.0;
        String fromUnit = "Метр";
        String toUnit = "Фут";

        // Act
        double convertedValue = converter.convert(value, fromUnit, toUnit);

        // Assert
        assertThat(convertedValue).isCloseTo(6.5616, within(DELTA)); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Конвертация из килограммов в граммы")
    void convertKilogramsToGrams() {
        // Arrange
        double value = 2.5;
        String fromUnit = "Килограмм";
        String toUnit = "Грамм";

        // Act
        double convertedValue = converter.convert(value, fromUnit, toUnit);

        // Assert
        assertThat(convertedValue).isCloseTo(2500.0, within(DELTA));
    }

    @Test
    @DisplayName("Конвертация из фунтов в унции")
    void convertPoundsToOunces() {
        // Arrange
        double value = 1.0; // 1 фунт
        String fromUnit = "Фунт";
        String toUnit = "Унция";

        // Act
        double convertedValue = converter.convert(value, fromUnit, toUnit);

        // Assert
        // 1 фунт = примерно 16 унций (через кг: 1/2.20462 * 35.274 ≈ 16)
        assertThat(convertedValue).isCloseTo(16, within(DELTA)); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Конвертация из Цельсия в Фаренгейт")
    void convertCelsiusToFahrenheit() {
        // Arrange
        double value = 25.0;
        String fromUnit = "Цельсий";
        String toUnit = "Фаренгейт";

        // Act
        double convertedValue = converter.convert(value, fromUnit, toUnit);

        // Assert
        double expectedValue = (25.0 * 9.0 / 5.0) + 32.0; // В этой строке ошибка в формуле!
        assertThat(convertedValue).isCloseTo(expectedValue, within(DELTA));
    }

    @Test
    @DisplayName("Конвертация из Фаренгейта в Кельвин")
    void convertFahrenheitToKelvin() {
        // Arrange
        double value = 32.0; // 32°F = 0°C = 273.15K
        String fromUnit = "Фаренгейт";
        String toUnit = "Кельвин";

        // Act
        double convertedValue = converter.convert(value, fromUnit, toUnit);

        // Assert
        assertThat(convertedValue).isCloseTo(273.15, within(DELTA)); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Обработка несовместимых единиц измерения")
    void handleIncompatibleUnits() {
        // Arrange
        double value = 10.0;
        String fromUnit = "Метр";
        String toUnit = "Килограмм";

        // Act
        double result = converter.convert(value, fromUnit, toUnit);

        // Assert
        assertThat(result).isEqualTo(ERROR); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Обработка неподдерживаемых единиц измерения (fromUnit)")
    void handleUnsupportedFromUnit() {
        // Arrange
        double value = 10.0;
        String fromUnit = "Миля"; // Неподдерживаемая единица
        String toUnit = "Метр";

        // Act
        double result = converter.convert(value, fromUnit, toUnit);

        // Assert
        assertThat(result).isEqualTo(ERROR); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Обработка неподдерживаемых единиц измерения (toUnit)")
    void handleUnsupportedToUnit() {
        // Arrange
        double value = 10.0;
        String fromUnit = "Метр";
        String toUnit = "Ярд"; // Неподдерживаемая единица

        // Act
        double result = converter.convert(value, fromUnit, toUnit);

        // Assert
        assertThat(result).isEqualTo(ERROR);
    }

    @ParameterizedTest
    @CsvSource({ // value, fromUnit, toUnit, expected
            "1.0, Метр, Сантиметр, 100.0",
            "100.0, Сантиметр, Метр, 1.0",
            "1.0, Килограмм, Фунт, 2.20462",
            "1.0, Фунт, Килограмм, 0.45359", // 1 / 2.20462
            "0.0, Цельсий, Фаренгейт, 32.0",
            "273.15, Кельвин, Цельсий, 0.0",
            "5.0, Метр, Дюйм, 196.85", // В этой строке ошибка! 5 метров = 5 * 39.37 = 196.85 дюймов
            "10.0, Килограмм, Унция, 352.74" // В этой строке ошибка! 10 кг = 10 * 35.274 = 352.74 унций
    })
    @DisplayName("Различные конвертации")
    void testVariousConversions(double value, String fromUnit, String toUnit, double expected) {
        double convertedValue = converter.convert(value, fromUnit, toUnit);
        assertThat(convertedValue).isCloseTo(expected, within(DELTA));
    }
}