package ru.mentee.power.datatypes;



public class SomeTypes {


    public static void main(String[] args) {

        String greetings = "Hello";
        String name = "Max";

        byte b = 10;
        short s = 20;
        int i = 30;
        long l = 40L;
        float f = 50.5f;
        double d = 60.6;




        String concatResult = greetings + name;                                                    // 1. Конкатенация строк
        System.out.println("Конкатенация: \"Hello\" + \"World\" = \"" + concatResult + "\"");



        String intToString = String.valueOf(i);                                                    // 3. Преобразование числа в строку
        System.out.println("Число в строку: " + i + " -> \"" + intToString + "\"");





        // Математические операции с разными типами

        System.out.println("1. byte + short → " + (b + s) + " (тип: " + getType(b + s) + ")");
        System.out.println("2. int + long → " + (i + l) + " (тип: " + getType(i - l) + ")");
        System.out.println("3. float + double → " + (f + d) + " (тип: " + getType(f * d) + ")");
        System.out.println("4. byte + int → " + (b + i) + " (тип: " + getType(b + i) + ")");
        System.out.println("5. long + float → " + (l + f) + " (тип: " + getType(l + f) + ")");
        System.out.println("6. int + double → " + (i + d) + " (тип: " + getType(i + d) + ")");
        System.out.println("7. float + int → " + (f + i) + " (тип: " + getType(f * i) + ")");
        System.out.println("8. double + long → " + (d + l) + " (тип: " + getType(d + l) + ")");
    }

    private static String getType(Object obj) {
        return obj.getClass().getSimpleName();
    }
}