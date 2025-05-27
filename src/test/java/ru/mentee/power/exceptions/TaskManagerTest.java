package ru.mentee.power.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskManagerTest {

    private TaskManager account;
    private static final double INITIAL_BALANCE = 1000.0;
    private static final String ACCOUNT_ID = "ACC-123";

    @BeforeEach
    void setUp() {

        account = new TaskManager(ACCOUNT_ID, INITIAL_BALANCE);
    }
    @Test
    @DisplayName("Конструктор должен правильно устанавливать начальный баланс и ID")
    void constructorShouldSetInitialBalanceAndId() {
        assertThat(account.getId()).isEqualTo(ACCOUNT_ID);
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Конструктор должен выбрасывать IllegalArgumentException при отрицательном балансе")
    void constructorShouldThrowIllegalArgumentExceptionForNegativeBalance() {

        // Проверяем, что при попытке создать TaskManager с отрицательным балансом выбрасывается исключение
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new TaskManager("123", -100));
        assertThat(exception.getMessage()).contains("Начальный баланс не может быть отрицательным");
    }

    // --- Тесты для deposit ---
    @Test
    @DisplayName("Метод deposit должен увеличивать баланс при положительной сумме")
    void depositShouldIncreaseBalanceForPositiveAmount() {
        account.deposit(500);
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE + 500);
    }

    @Test
    @DisplayName("Метод deposit должен допускать нулевую сумму")
    void depositShouldAllowZeroAmount() {
        account.deposit(0);
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE );

    }

    @Test
    @DisplayName("Метод deposit должен выбрасывать IllegalArgumentException при отрицательной сумме")
    void depositShouldThrowIllegalArgumentExceptionForNegativeAmount() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                account.deposit(-100)
        );

        assertThat(exception.getMessage()).contains("Сумма депозита не может быть отрицательной");
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE);
    }

    // --- Тесты для withdraw ---
    @Test
    @DisplayName("Метод withdraw должен уменьшать баланс при корректной сумме")
    void withdrawShouldDecreaseBalanceForValidAmount() throws TaskValidationException {
        account.withdraw(500);
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE - 500);
    }

    @Test
    @DisplayName("Метод withdraw должен позволять снять полный баланс")
    void withdrawShouldAllowWithdrawingFullBalance() throws TaskValidationException {
        account.withdraw(INITIAL_BALANCE);
        assertThat(account.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Метод withdraw должен допускать нулевую сумму")
    void withdrawShouldAllowZeroAmount() throws TaskValidationException {
        account.withdraw(0);
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать IllegalArgumentException при отрицательной сумме")
    void withdrawShouldThrowIllegalArgumentExceptionForNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                account.withdraw(-100));

        assertThat(exception.getMessage()).contains("Сумма снятия не может быть отрицательной");
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать TaskValidationException при превышении баланса")
    void withdrawShouldThrowTaskValidationExceptionWhenAmountExceedsBalance() {
        double excessiveAmount = INITIAL_BALANCE + 100.0;

        TaskValidationException exception = assertThrows(TaskValidationException.class, () ->
                account.withdraw(excessiveAmount));

        assertThat(exception.getMessage()).contains(String.format("Недостаточно средств на счете %s для снятия %.2f", ACCOUNT_ID, excessiveAmount));
        assertThat(exception.getBalance()).isEqualTo(INITIAL_BALANCE);
        assertThat(exception.getWithdrawAmount()).isEqualTo(excessiveAmount);
        assertThat(exception.getDeficit()).isEqualTo(excessiveAmount - INITIAL_BALANCE);

        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE);
    }
}