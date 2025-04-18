package ru.mentee.power.variables;
import static java.lang.System.*;

public class PersonalCard {



    public static void main(String[] args) {

        String name = "Максим";
        String secondName = "Сикорский";
        String city = "Пхукет";
        int age = 35;
        int weight = 70;
        int tall = 170;
        char firstLetter = 'M';
        boolean isStudent = true;

        out.println("===== ЛИЧНАЯ КАРТОЧКА =====");
        out.println("Имя: " + name);
        out.println("Фамилия: " + secondName);
        out.println("Город: " + city);
        out.println("Возраст: " + age);
        out.println("Вес: " + weight);
        out.println("Рост: " + tall);
        out.println("Первая буква имени: " + firstLetter);
        out.println("Студент: " + isStudent);
        out.println("==========================");

    }
}
