package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    // 🔹 Константы для ходов
    public static final String ROCK = "Камень";
    public static final String PAPER = "Бумага";
    public static final String SCISSORS = "Ножницы";

    // 🔹 Константы для результатов игры
    public static final String PLAYER_WINS = "Победа игрока";
    public static final String COMPUTER_WINS = "Победа компьютера";
    public static final String DRAW = "Ничья";
    public static final String ERROR = "Ошибка";

    // 🔹 Допустимые ходы
    private static final List<String> VALID_MOVES = Arrays.asList(ROCK, PAPER, SCISSORS);

    private final Random random = new Random(); // Генератор случайных чисел

    /**
     * Определяет исход игры на основе ходов игрока и компьютера.
     *
     * @param playerMove   Ход игрока
     * @param computerMove Ход компьютера
     * @return Результат игры (PLAYER_WINS, COMPUTER_WINS, DRAW или ERROR)
     */
    public String determineWinner(String playerMove, String computerMove) {
        if (!validateMove(playerMove) || !validateMove(computerMove)) {
            return ERROR; // 🔹 Проверка корректности хода
        }

        if (playerMove.equalsIgnoreCase(computerMove)) {
            return DRAW; // 🔹 Если выборы совпадают — ничья
        }

        switch (playerMove) {
            case ROCK:
                return (computerMove.equalsIgnoreCase(SCISSORS)) ? PLAYER_WINS : COMPUTER_WINS;
            case SCISSORS:
                return (computerMove.equalsIgnoreCase(PAPER)) ? PLAYER_WINS : COMPUTER_WINS;
            case PAPER:
                return (computerMove.equalsIgnoreCase(ROCK)) ? PLAYER_WINS : COMPUTER_WINS;
            default:
                return ERROR;
        }
    }

    /**
     * Проверяет, является ли ход допустимым.
     *
     * @param move Ход для проверки
     * @return true, если ход допустим, иначе false
     */
    private boolean validateMove(String move) {
        return VALID_MOVES.contains(move);
    }

    /**
     * Генерирует случайный ход компьютера.
     *
     * @return Случайный ход из списка VALID_MOVES.
     */
    public String generateComputerMove() {
        int randomIndex = random.nextInt(VALID_MOVES.size());
        return VALID_MOVES.get(randomIndex);
    }

    /**
     * Запускает одну игровую сессию.
     */
    public void playOneGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ваш ход (Камень, Ножницы, Бумага): ");
        String playerMove = scanner.nextLine().trim();

        if (!validateMove(playerMove)) {
            System.out.println(ERROR);
            return;
        }

        String computerMove = generateComputerMove();
        System.out.println("Компьютер выбрал: " + computerMove);

        String result = determineWinner(playerMove, computerMove);
        System.out.println(result); // Просто выводим результат в консоль
    }


    /**
     * Запускает игровой цикл.
     */
    public void startGameLoop() {
        Scanner scanner = new Scanner(System.in);
        int playerWins = 0;
        int computerWins = 0;
        int draws = 0;

        System.out.println("Добро пожаловать в игру \"Камень-Ножницы-Бумага\"!");
        System.out.println("Вы играете против компьютера.");
        System.out.println("Введите \"Выход\", чтобы завершить игру.");

        while (true) {
            System.out.println("\nВаш ход: ");
            String playerMove = scanner.nextLine().trim();

            if (playerMove.equalsIgnoreCase("Выход")) {
                System.out.println("\nСпасибо за игру! Итоговая статистика:");
                System.out.println("Победы игрока: " + playerWins);
                System.out.println("Победы компьютера: " + computerWins);
                System.out.println("Ничьи: " + draws);
                break;
            }

            if (!validateMove(playerMove)) {
                System.out.println(ERROR);
                continue;
            }

            String computerMove = generateComputerMove();
            System.out.println("Компьютер выбрал: " + computerMove);

            String result = determineWinner(playerMove, computerMove);

            if (result.equals(PLAYER_WINS)) {
                playerWins++;
            } else if (result.equals(COMPUTER_WINS)) {
                computerWins++;
            } else {
                draws++;
            }

            System.out.println(result);
        }

        scanner.close();
    }

    /**
     * Точка входа в игру.
     */
    public static void main(String[] args) {
        RockPaperScissors game = new RockPaperScissors();
        game.startGameLoop(); // Запуск игрового цикла
    }
}