package ru.mentee.power.conditions;


import java.util.Scanner;

import static java.lang.System.*;

public class CreditCalculator {
  static final int MORTGAGE = 9;
  static final int CONSUMER = 15;
  static final int CAR_CREDIT = 12;

  static final int SCORE_MAX_VALUE = 850;
  static final int SCORE_MIN_VALUE = 300;

  static final double LOAN_AMOUNT_MIN_VALUE = 10_000;
  static final double LOAN_AMOUNT_MAX_VALUE = 10_000_000;

  static final int LOAN_MONTHS_MAX_VALUE = 360;  // Исправлено: было 36
  static final int LOAN_MONTHS_MIN_VALUE = 1;

  public static double calculateMonthlyPayment(double loanAmount, int loanTermMonths, String creditType, int creditScore) {

    if (loanAmount > LOAN_AMOUNT_MAX_VALUE || loanAmount < LOAN_AMOUNT_MIN_VALUE) {
      return -1;
    }
    if (loanTermMonths > LOAN_MONTHS_MAX_VALUE || loanTermMonths < LOAN_MONTHS_MIN_VALUE) {
      return -1;
    }
    if (creditScore > SCORE_MAX_VALUE || creditScore < SCORE_MIN_VALUE) {
      return -1;
    }

    int fixedRate;
    switch (creditType.toLowerCase()) {
      case "ипотека":
        fixedRate = MORTGAGE;
        break;
      case "потребительский":
        fixedRate = CONSUMER;
        break;
      case "автокредит":
        fixedRate = CAR_CREDIT;
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
    Scanner scanner = new Scanner(in);


    out.println("Выберите тип кредита (Ипотека/Потребительский/Автокредит):");
    String creditType = scanner.nextLine();

    out.println("Введите сумму кредита (от 10,000 руб. до 10,000,000 руб.): ");
    double loanAmount = scanner.nextDouble();

    out.println("Выберите колличество месяцев (от 1 до 360)");
    int loanTermMonths = scanner.nextInt();
    out.println("Введите кредитный рейтинг (от 300 до 850)");
    int creditScore = scanner.nextInt();


    double result = calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);
    double result2 = Math.round(result * 100.0) / 100.0;
    out.println("Размер ежемесячного платежа составит: " + result2 + " руб. ");
    scanner.close();
  }
}







