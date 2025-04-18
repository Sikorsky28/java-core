package ru.mentee.power.variables;


public class StringOperations {
    public static void main(String[] args) {


        String s1 = "Hello";
        String s2 = "World!";

        char c1 = 'a';
        char c2 = 'A';

        int num = 5;



        // 1. Конкатенация строк

        String concatResult = s1 + s2;

        // 2. Преобразование символа в строку

        String str = String.valueOf(c2);


        // 3. Преобразование числа в строку и обратно

        String intToString = String.valueOf(num);


        int strToInt = Integer.parseInt(intToString);

        // 4. Извлечение символа из строки


        char c = s1.charAt(2);
        System.out.println(c);

        // Выведите результаты всех операций

        System.out.println("Конкатенация: \"Hello\" + \"World\" = \"" + concatResult + "\"");
        System.out.println("Символ в строку: '" + c2 + "' -> \"" + str + "\"");
        System.out.println("Число в строку: " + num + " -> \"" + intToString + "\"");
        System.out.println("Строку в число: \"" + intToString + "\" -> \"" + num );
        System.out.println("Символ из строки \"" + s1 + "\": индекс " + 2 + " -> '" + c + "'");

    }
}