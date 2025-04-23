package ru.mentee.power.conditions;


import java.util.Scanner;

public class CreditCalculator {
    final int mortgage = 9;
    final int consumer = 15;
    final int carCredit = 12;

    final int scoreMaxValue = 850;
    final int scoreMinValue = 300;

    final double loanAmountMaxValue = 10000000;
    final double loanAmountMinValue = 10000;

    final int loanMonthsMaxValue = 360;  // Исправлено: было 36
    final int loanMonthsMinValue = 1;

    public double calculateMonthlyPayment(double loanAmount, int loanTermMonths, String creditType, int creditScore) {

        if (loanAmount > loanAmountMaxValue || loanAmount < loanAmountMinValue) {
            return -1;
        }
        if (loanTermMonths > loanMonthsMaxValue || loanTermMonths < loanMonthsMinValue) {
            return -1;
        }
        if (creditScore > scoreMaxValue || creditScore < scoreMinValue) {
            return -1;
        }

        int fixedRate;
        switch (creditType.toLowerCase()) {
            case "ипотека":
                fixedRate = mortgage;
                break;
            case "потребительский":
                fixedRate = consumer;
                break;
            case "автокредит":
                fixedRate = carCredit;
                break;
            default:
                return -1;
        }

        // Корректировка ставки в зависимости от кредитного рейтинга
        if (creditScore >= 750) {
            fixedRate -= 2;
        } else if (creditScore >= 650) {
            fixedRate -= 1;
        } else if (creditScore <= 499) {
            fixedRate += 3;
        }

        double monthlyRate = fixedRate / 12.0 / 100.0;
        return loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);
    }
    public static void main(String[] args) {
        CreditCalculator calculator = new CreditCalculator();
        Scanner scanner = new Scanner(System.in);



        System.out.println("Выберите тип кредита (Ипотека/Потребительский/Автокредит):");
        String creditType = scanner.nextLine();

        System.out.println("Введите сумму кредита (от 10,000 руб. до 10,000,000 руб.): ");
        double loanAmount = scanner.nextDouble();

        System.out.println("Выберите колличество месяцев (от 1 до 36)");
        int loanTermMonths = scanner.nextInt();
        System.out.println("Введите кредитный рейтинг (от 300 до 850)");
        int creditScore = scanner.nextInt();



        double result =  calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType,creditScore);
        double result2 = Math.round(result * 100.0) / 100.0;
        System.out.println("Размер ежемесячного платежа составит: " + result2 + " руб. ");
        scanner.close();
    }
}







