package ru.mentee.power.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.offset;

@DisplayName("Тесты для DiscountCalculator")
class DiscountCalculatorTest {

    private DiscountCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new DiscountCalculator();
    }

    @Test
    @DisplayName("Скидка 0% для суммы <= 1000")
    void shouldApplyZeroDiscountForAmountLessOrEqual1000() {
        // Arrange
        double amount = 800.0;
        double expectedPrice = 800.0;
        // Act
        double actualPrice = calculator.calculateDiscountedPrice(amount);
        // Assert
        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Скидка 10% для суммы > 1000 и <= 5000")
    void shouldApply10PercentDiscountForAmountBetween1000And5000() {
        // Arrange
        double amount = 1200.0;
        double expectedPrice = 1080.0; // 1200 * 0.9
        // Act
        double actualPrice = calculator.calculateDiscountedPrice(amount);
        // Assert
        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Скидка 20% для суммы > 5000")
    void shouldApply20PercentDiscountForAmountGreaterThan5000() {
        // Arrange
        double amount = 6000.0;
        double expectedPrice = 4800.0; // 6000 * 0.8
        // Act
        double actualPrice = calculator.calculateDiscountedPrice(amount);
        // Assert
        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Граничный случай: Скидка 0% для суммы ровно 1000")
    void shouldApplyZeroDiscountForAmountExactly1000() {
        // Arrange
        double amount = 1000.0;
        double expectedPrice = 1000.0;
        // Act
        double actualPrice = calculator.calculateDiscountedPrice(amount);
        // Assert
        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }
    @Test
    @DisplayName("Скидка 10% для суммы ровно 1000.01")
    void shouldApplyZeroDiscountForAmountGreateThan1000(){
        double amount = 1000.01;
        double expectedPrice = 900.01;

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Граничный случай: Скидка 10% для суммы ровно 5000")
    void shouldApplyZeroDiscountForAmountExactly5000() {
        double amount = 5000;
        double expectedPrice = 4500;

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));

    }
    @Test
    @DisplayName("Граничный случай: Скидка 20% для суммы ровно 5000,01")
    void shouldApplyZeroDiscountForAmountGraterThab5000() {
        double amount = 5000.01;
        double expectedPrice =  4000.01;

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));

    }

}