package ru.mentee.power.conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RockPaperScissorsTest {

    private RockPaperScissors game;

    private static final String ROCK = "Камень";
    private static final String PAPER = "Бумага";
    private static final String SCISSORS = "Ножницы";
    private static final String PLAYER_WINS = "Победа игрока";
    private static final String COMPUTER_WINS = "Победа компьютера";
    private static final String DRAW = "Ничья";
    private static final String ERROR = "Ошибка";

    @BeforeEach
    void setUp() {
        game = new RockPaperScissors();
    }

    @Test
    @DisplayName("Камень побеждает ножницы")
    void rockBeatsScissors() {
        // Arrange
        String playerChoice = ROCK;
        String computerChoice = SCISSORS;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        // TODO: Исправьте ошибку в ожидаемом результате!
        // Камень бьет ножницы, поэтому игрок должен победить
        assertThat(result).isEqualTo(PLAYER_WINS); // fixed
    }

    @Test
    @DisplayName("Ножницы побеждают бумагу")
    void scissorsBeatsPaper() {
        // Arrange
        String playerChoice = SCISSORS;
        String computerChoice = PAPER;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(PLAYER_WINS);
    }

    @Test
    @DisplayName("Бумага побеждает камень")
    void paperBeatsRock() {
        // Arrange
        // TODO: Исправьте ошибку в выборе компьютера!
        // Для проверки того, что бумага побеждает камень, компьютер должен выбрать камень
        String playerChoice = PAPER;
        String computerChoice = ROCK; // fixed

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(PLAYER_WINS);
    }

    @Test
    @DisplayName("Ничья при одинаковом выборе (Камень)")
    void drawWhenSameChoiceRock() {
        // Arrange
        String playerChoice = ROCK;
        String computerChoice = ROCK;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        // TODO: Исправьте ошибку в ожидаемом результате!
        // При одинаковом выборе должна быть ничья
        assertThat(result).isEqualTo(PLAYER_WINS); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Ничья при одинаковом выборе (Бумага)")
    void drawWhenSameChoicePaper() {
        // Arrange
        String playerChoice = PAPER;
        String computerChoice = PAPER;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    @DisplayName("Ничья при одинаковом выборе (Ножницы)")
    void drawWhenSameChoiceScissors() {
        // Arrange
        String playerChoice = SCISSORS;
        String computerChoice = SCISSORS;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    @DisplayName("Обработка некорректного выбора игрока")
    void handleInvalidPlayerChoice() {
        // Arrange
        String playerChoice = "Колодец"; // Некорректный выбор
        String computerChoice = ROCK;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        // TODO: Исправьте ошибку в ожидаемом результате!
        // При некорректном выборе должна возвращаться ошибка
        assertThat(result).isEqualTo(DRAW); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Обработка некорректного выбора компьютера")
    void handleInvalidComputerChoice() {
        // Arrange
        String playerChoice = ROCK;
        String computerChoice = "Огонь"; // Некорректный выбор

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        // TODO: Исправьте ошибку в ожидаемом результате!
        // При некорректном выборе должна возвращаться ошибка
        assertThat(result).isEqualTo(COMPUTER_WINS); // В этой строке ошибка!
    }

    @Test
    @DisplayName("Обработка некорректного выбора у обоих")
    void handleInvalidBothChoices() {
        // Arrange
        String playerChoice = "Вода";
        String computerChoice = "Воздух";

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(ERROR);
    }

    @RepeatedTest(10) // Повторим тест 10 раз для большей уверенности в случайности
    @DisplayName("Генерация случайного выбора компьютера")
    void generateComputerChoiceReturnsValidOption() {
        // Act
        String choice = game.generateComputerMove();

        // Assert
        // TODO: Исправьте ошибку в проверке допустимых вариантов!
        // Метод должен возвращать один из допустимых вариантов: Камень, Ножницы или Бумага
        assertThat(choice).isIn(ROCK, SCISSORS, PAPER); // В этой строке ошибка!
    }

    @ParameterizedTest
    @CsvSource({ // playerChoice, computerChoice, expectedResult
            "Камень, Ножницы, Победа игрока",
            "Ножницы, Камень, Победа компьютера",
            "Бумага, Бумага, Ничья",
            "Ножницы, Бумага, Победа игрока",
            // TODO: Исправьте ошибки в ожидаемых результатах!
            "Камень, Бумага, Победа игрока",  // В этой строке ошибка! Бумага бьет камень → Победа компьютера
            "Бумага, Ножницы, Победа игрока"   // В этой строке ошибка! Ножницы бьют бумагу → Победа компьютера
    })
    @DisplayName("Различные комбинации выборов для determineWinner")
    void testVariousChoiceCombinationsDetermineWinner(String playerChoice, String computerChoice, String expectedResult) {
        String result = game.determineWinner(playerChoice, computerChoice);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Тестирование метода playGame (без моков)")
    void testPlayGame_ValidChoice() {
        // Arrange
        String playerChoice = ROCK;

        // Act
        String result = game.playOneGame();

        // Assert
        // TODO: Исправьте ошибку в проверке результата!
        // Результат должен содержать информацию о выборе компьютера
        assertThat(result).contains("Компьютер: ");  // В этой строке ошибка!
        assertThat(result).containsAnyOf(PLAYER_WINS, COMPUTER_WINS, DRAW);

        // Проверяем, что выбор компьютера был корректным
        if (result.contains(ROCK)) assertThat(game.determineWinner(playerChoice, ROCK)).isEqualTo(DRAW);
        if (result.contains(PAPER)) assertThat(game.determineWinner(playerChoice, PAPER)).isEqualTo(COMPUTER_WINS);
        if (result.contains(SCISSORS)) assertThat(game.determineWinner(playerChoice, SCISSORS)).isEqualTo(PLAYER_WINS);
    }

    @Test
    @DisplayName("Тестирование метода playGame с неверным выбором")
    void testPlayGame_InvalidChoice() {
        // Arrange
        String playerChoice = "Ящерица";

        // Act
        String result = game.playOneGame();

        // Assert
        // TODO: Исправьте ошибку в ожидаемом сообщении!
        // При некорректном выборе игрока должно выводиться сообщение об ошибке
        assertThat(result).isEqualTo("Неверный выбор игрока."); // В этой строке ошибка!
    }

    // Бонус: Используйте моки для подмены generateComputerChoice
    // Для этого нужно добавить зависимость Mockito в build.gradle:
    // testImplementation 'org.mockito:mockito-core:5.+'
    // testImplementation 'org.mockito:mockito-junit-jupiter:5.+'
    // И использовать @ExtendWith(MockitoExtension.class) над классом теста
    /*
    @Test
    @DisplayName("Тестирование метода playGame с моком для generateComputerChoice")
    void testPlayGame_WithMock() {
        // Arrange
        RockPaperScissors mockedGame = Mockito.spy(new RockPaperScissors()); // Создаем spy
        String playerChoice = ROCK;
        String forcedComputerChoice = PAPER; // Заставим компьютер выбрать Бумагу
        Mockito.doReturn(forcedComputerChoice).when(mockedGame).generateComputerMove();

        // Act
        String result = mockedGame.playOneGame();

        // Assert
        assertThat(result).isEqualTo(String.format("Компьютер выбрал: %s. Результат: %s",
                                     forcedComputerChoice, COMPUTER_WINS));
        Mockito.verify(mockedGame, Mockito.times(1)).generateComputerMove(); // Убедимся, что метод был вызван
    }
    */
}