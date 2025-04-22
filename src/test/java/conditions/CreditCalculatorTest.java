package conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class CreditCalculatorTest {

    private CreditCalculator calculator;
    private static final double DELTA = 0.01; // Допустимая погрешность для сравнения чисел с плавающей точкой

    @BeforeEach
    void setUp() {
        calculator = new CreditCalculator();
    }

    @Test
    @DisplayName("Расчет платежа для ипотеки с отличным кредитным рейтингом")
    void calculateMortgageWithExcellentCreditScore() {
        // Arrange
        double loanAmount = 5_000_000;
        int loanTermMonths = 240; // 20 лет
        String creditType = "Ипотека";
        int creditScore = 800; // Отличный рейтинг

        // Act
        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // Для отличного рейтинга (750-850) ставка по ипотеке: 9% - 2% = 7%
        double monthlyRate = 7.0 / 12 / 100;
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertThat(monthlyPayment).isCloseTo(expectedPayment, within(DELTA));
    }

    @Test
    @DisplayName("Расчет платежа для потребительского кредита с хорошим кредитным рейтингом")
    void calculateConsumerLoanWithGoodCreditScore() {
        // Arrange
        double loanAmount = 500_000;
        int loanTermMonths = 36; // 3 года
        String creditType = "Потребительский";
        int creditScore = 700; // Хороший рейтинг

        // Act
        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert

        // Для хорошего рейтинга (650-749) ставка по потребительскому кредиту: 15% - 1% = 14%
        double monthlyRate = 13.0 / 12 / 100; // В этой строке ошибка!
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertThat(monthlyPayment).isCloseTo(expectedPayment, within(DELTA));
    }

    @Test
    @DisplayName("Расчет платежа для автокредита со средним кредитным рейтингом")
    void calculateAutoLoanWithAverageCreditScore() {
        // Arrange
        double loanAmount = 800_000;
        int loanTermMonths = 48; // 4 года
        String creditType = "Автокредит";
        int creditScore = 600; // Средний рейтинг

        // Act
        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // Для среднего рейтинга (500-649) базовая ставка по автокредиту не меняется (12%)
        double monthlyRate = 10.0 / 12 / 100; // В этой строке ошибка!
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertThat(monthlyPayment).isCloseTo(expectedPayment, within(DELTA));
    }

    @Test
    @DisplayName("Расчет платежа для автокредита с плохим кредитным рейтингом")
    void calculateAutoLoanWithPoorCreditScore() {
        // Arrange
        double loanAmount = 1_000_000;
        int loanTermMonths = 60; // 5 лет
        String creditType = "Автокредит";
        int creditScore = 400; // Плохой рейтинг

        // Act
        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // Для плохого рейтинга (300-499) ставка по автокредиту: 12% + 3% = 15%
        double monthlyRate = 15.0 / 12 / 100;
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertThat(monthlyPayment).isCloseTo(expectedPayment, within(DELTA));
    }

    @Test
    @DisplayName("Обработка слишком маленькой суммы кредита")
    void handleTooSmallLoanAmount() {
        // Arrange
        double loanAmount = 5_000; // Меньше минимальной суммы (10,000)
        int loanTermMonths = 60;
        String creditType = "Автокредит";
        int creditScore = 700;

        // Act
        double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        assertThat(result).isEqualTo(-1.0);
    }

    @Test
    @DisplayName("Обработка слишком большой суммы кредита")
    void handleTooLargeLoanAmount() {
        // Arrange
        double loanAmount = 11_000_000; // Больше максимальной (10,000,000)
        int loanTermMonths = 120;
        String creditType = "Ипотека";
        int creditScore = 750;

        // Act
        double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        assertThat(result).isEqualTo(-1.0);
    }

    @Test
    @DisplayName("Обработка слишком короткого срока кредита")
    void handleTooShortLoanTerm() {
        // Arrange
        double loanAmount = 1_000_000;
        int loanTermMonths = 0; // Меньше минимального (1)
        String creditType = "Потребительский";
        int creditScore = 680;

        // Act
        double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // При некорректных входных данных метод должен возвращать -1
        assertThat(result).isEqualTo(-1); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Обработка слишком длинного срока кредита")
    void handleTooLongLoanTerm() {
        // Arrange
        double loanAmount = 2_000_000;
        int loanTermMonths = 400; // Больше максимального (360)
        String creditType = "Автокредит";
        int creditScore = 720;

        // Act
        double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        assertThat(result).isEqualTo(-1.0);
    }

    @Test
    @DisplayName("Обработка неизвестного типа кредита")
    void handleUnknownCreditType() {
        // Arrange
        double loanAmount = 500_000;
        int loanTermMonths = 60;
        String creditType = "Образовательный"; // Неизвестный тип
        int creditScore = 800;

        // Act
        double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        assertThat(result).isEqualTo(-1.0);
    }

    @Test
    @DisplayName("Обработка слишком низкого кредитного рейтинга")
    void handleTooLowCreditScore() {
        // Arrange
        double loanAmount = 300_000;
        int loanTermMonths = 36;
        String creditType = "Потребительский";
        int creditScore = 250; // Ниже минимального (300)

        // Act
        double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        assertThat(result).isEqualTo(-1.0);
    }

    @Test
    @DisplayName("Обработка слишком высокого кредитного рейтинга")
    void handleTooHighCreditScore() {
        // Arrange
        double loanAmount = 4_000_000;
        int loanTermMonths = 180;
        String creditType = "Ипотека";
        int creditScore = 900; // Выше максимального (850)

        // Act
        double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // При кредитном рейтинге больше 850 метод должен возвращать -1
        assertThat(result).isEqualTo(-1);
    }
}