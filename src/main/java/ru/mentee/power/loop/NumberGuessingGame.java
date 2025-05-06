package ru.mentee.power.loop;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    private final Random random;
    private Scanner scanner;
    private int secretNumber;
    private int gamesPlayed = 0;
    private int minAttempts = Integer.MAX_VALUE;
    private int maxAttempts = 0;
    private int totalAttempts = 0;

    public NumberGuessingGame() {
        this(System.in);
    }

    public NumberGuessingGame(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
        this.random = createRandom();
    }

    protected Random createRandom() {
        return new Random();
    }

    public void startGame() {
        do {
            int attempts = playRound();
            if (attempts > 0) {
                updateStatistics(attempts);
            }
            showStatistics();
        } while (askPlayAgain());
    }

    public int playRound() {
        secretNumber = random.nextInt(100) + 1;
        int attempts = 0;
        boolean guessed = false;

        System.out.println("Я загадал число от 1 до 100. Попробуйте угадать!");

        do {
            System.out.print("Введите ваше предположение (или 'q' для выхода): ");

            if (!scanner.hasNextLine()) {
                System.out.println("Ошибка: нет доступных данных!");
                return -1;
            }

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                System.out.println("Игра прервана. Загаданное число было: " + secretNumber);
                return attempts;
            }

            int guess;
            try {
                guess = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите целое число!");
                continue;
            }

            if (guess < 1 || guess > 100) {
                System.out.println("Пожалуйста, введите число в диапазоне от 1 до 100!");
                continue;
            }

            attempts++;

            if (guess == secretNumber) {
                System.out.println("Поздравляю! Вы угадали число " + secretNumber +
                        " за " + attempts + " попыток.");
                guessed = true;
            } else if (guess < secretNumber) {
                System.out.println("Загаданное число больше!");
            } else {
                System.out.println("Загаданное число меньше!");
            }

        } while (!guessed);

        return attempts;
    }

    private void updateStatistics(int attempts) {
        if (attempts > 0) {
            gamesPlayed++;
            minAttempts = Math.min(minAttempts, attempts);
            maxAttempts = Math.max(maxAttempts, attempts);
            totalAttempts += attempts;
        }
    }

    public void showStatistics() {
        if (gamesPlayed > 0) {
            System.out.println("============== СТАТИСТИКА ==============");
            System.out.println("Сыграно игр: " + gamesPlayed);
            System.out.println("Минимальное количество попыток: " + minAttempts);
            System.out.println("Максимальное количество попыток: " + maxAttempts);
            System.out.printf("Среднее количество попыток: %.2f%n", (double) totalAttempts / gamesPlayed);
            System.out.println("========================================");
        }
    }

    private boolean askPlayAgain() {
        System.out.print("Хотите сыграть еще раз? (да/нет): ");

        if (!scanner.hasNextLine()) {
            System.out.println("Ошибка: нет доступных данных!");
            return false;
        }

        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("да") || answer.equals("yes") || answer.equals("y");
    }

    public void close() {
        scanner.close();
    }

    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        try {
            game.startGame();
        } finally {
            game.close();
        }
    }
}
