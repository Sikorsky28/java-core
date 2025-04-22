package conditions;

import java.util.Scanner;

public class RentalChecker {

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        System.out.println("Введите ваш возраст: ");

        int age = scn.nextInt();
        scn.nextLine();

        System.out.println("У вас есть водительское удостоверение? да / нет ");
        String answer = scn.nextLine();
        if (answer.equalsIgnoreCase("да" ) && age >= 18){
            boolean isLicense = true;
            System.out.println("Вы можете арендовать автомобиль");

        }else{
            System.out.println("Вам нужны водительские права для аренды автомобиля ");
        }











    }


}
