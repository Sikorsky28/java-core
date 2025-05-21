package ru.mentee.power.variables;

/**
 * Утилитарный класс для работы со строками.
 */
public class StringUtils {

    /**
     * Подсчитывает количество вхождений символа в строке.
     *
     * @param str Исходная строка
     * @param target Искомый символ
     * @return Количество вхождений символа
     */
    public static int countChars(String str, char target) {

        if(str == null){
            return 0;
        }


        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == target){
                count++;
            }
        }



        return count;
    }

    /**
     * Обрезает строку до указанной максимальной длины.
     * Если строка длиннее maxLength, добавляет "..." в конце.
     *
     * @param str Исходная строка
     * @param maxLength Максимальная длина
     * @return Обрезанная строка
     */
    public static String truncate(String str, int maxLength) {

        if(str == null || maxLength <= 0){
            return "";
        } else if (str.length() <= maxLength){
            return str;
        }

    return str.substring(0, maxLength ) + "...";
    }

    /**
     * Проверяет, является ли строка палиндромом.
     * Игнорирует регистр и не-буквенные символы.
     *
     * @param str Исходная строка
     * @return true, если строка является палиндромом, иначе false
     */
    public static boolean isPalindrome(String str) {
        if (str == null){
            return false;
        }
        String cleanedStr = str.replaceAll("[^a-zA-Zа-яА-Я]", "").toLowerCase();

        int left = 0;
        int right = cleanedStr.length() - 1;

        while (left < right){
            if(cleanedStr.charAt(left) != cleanedStr.charAt(right)){
                return false;
            }
            left ++;
            right --;
        }
            return true;

    }

    /**
     * Заменяет все последовательности пробельных символов одним пробелом.
     * Удаляет пробелы в начале и конце строки.
     *
     * @param str Исходная строка
     * @return Нормализованная строка
     */
    public static String normalizeSpaces(String str) {

        if (str == null){
            return "";
        }




        return str.replaceAll("\\s+", " ").trim();
    }

    /**
     * Объединяет массив строк, используя указанный разделитель.
     *
     * @param strings Массив строк
     * @param delimiter Разделитель
     * @return Объединенная строка
     */
    public static String join(String[] strings, String delimiter) {
        if (strings == null || strings.length == 0) {
            return "";
        }

        if (delimiter == null) {
            delimiter = "";
        }

        StringBuilder result = new StringBuilder();
        for (String str : strings) {
            if (str != null) {
                if (result.length() > 0) {
                    result.append(delimiter);
                }
                result.append(str);
            }
        }

        return result.toString();
    }

}