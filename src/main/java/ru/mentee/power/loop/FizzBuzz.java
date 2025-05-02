package ru.mentee.power.loop;

public class FizzBuzz {
    public String[] generateFizzBuzz(int n) {
        if (n <= 0) {
            return new String[0]; // Возвращаем пустой массив, чтобы избежать ошибки
        }
        String[] result = new String[n];
        for(int index = 1; index <= 30; index++){
            if(index % 3 == 0 && index % 5 == 0){
                result[index - 1] = "FizzBuzz";
            } else if (index % 3 == 0) {
                result[index - 1] = "Fizz";
            } else if (index % 5 == 0){
                result[index - 1] = "Buzz";
            }else{
                result[index - 1] = String.valueOf(index);
            }
        }



        return result;
    }

    public void printFizzBuzz(int n) {
        // Получаем массив результатов
        String[] results = generateFizzBuzz(n);

        // Выводим каждый элемент в консоль
        for (String result : results) {
            System.out.println(result);

        }
    }
    public static void main(String[] args) {
        // Создаем экземпляр класса
        FizzBuzz fizzBuzz = new FizzBuzz();

        // Выводим результаты для n = 15
        System.out.println("FizzBuzz для чисел от 1 до 30:");
        fizzBuzz.printFizzBuzz(30);
    }
}
