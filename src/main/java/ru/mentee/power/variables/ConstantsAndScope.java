package ru.mentee.power.variables;

public class ConstantsAndScope {


    static final double PI = 3.14;
    static final String const2 = "CONSTANT";
    protected static final String DEFAULT_NAME = "Unknown";


    public static void main(String[] args) {


         final int MAX_COUNT = 20;
         final String LOCAL_CONST = "LOCAL CONSTANT";
         int localVar = 30;





        {
            int blockVar = 100;
            System.out.println("Внутри блока:");
            System.out.println("blockVar = " + blockVar); // Работает
            System.out.println("localVar = " + localVar); // Работает
            System.out.println("PI = " + PI); // Работает (константа класса)


        }

        // System.out.println(blockVar); // Ошибка! blockVar не видна вне блока

        System.out.println("\nВне блока:");
        System.out.println("localVar = " + localVar); // Работает
        System.out.println("LOCAL_CONST = " + LOCAL_CONST); // Работает
        System.out.println("MAX_COUNT = " + MAX_COUNT); // Работает

        // Вызов метода
        someMethod();





    }


    public static void someMethod() {
        int someMethodVar = 1;
        final String METHOD_CONST = "World";

        System.out.println("\nВнутри someMethod:");
        System.out.println("someMethodVar = " + someMethodVar); // Работает
        System.out.println("METHOD_CONST = " + METHOD_CONST); // Работает
        System.out.println("DEFAULT_NAME = " + DEFAULT_NAME); // Работает (protected константа)
        // System.out.println(localVar); // Ошибка! localVar не видна в этом методе
    }
}