package ru.mentee.power.methods.overloading;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Класс для форматирования различных типов данных в строку
 */
public class DataFormatter {

    /**
     * Форматирует целое число, разделяя его по разрядам
     *
     * @param value Целое число
     * @return Отформатированное представление числа
     */
    public static String format(int value) {
        return NumberFormat.getInstance(Locale.US).format(value).replace(",", " ");

    }

    /**
     * Форматирует целое число с указанием префикса и суффикса
     *
     * @param value Целое число
     * @param prefix Префикс (например, символ валюты)
     * @param suffix Суффикс (например, код валюты)
     * @return Отформатированное представление числа с префиксом и суффиксом
     */
    public static String format(int value, String prefix, String suffix) {

        String formattedNumber = NumberFormat.getInstance(Locale.US).format(value).replaceAll(",", " ");
        return(prefix != null ? prefix  : "") + formattedNumber + (suffix != null ? " " +  suffix : "");
    }

    /**
     * Форматирует число с плавающей точкой, используя два десятичных знака
     *
     * @param value Число с плавающей точкой
     * @return Отформатированное представление числа
     */
    public static String format(double value) {

        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        String temp =  nf.format(value).replace(",", " ");
        return  temp.replace(".", ",");
    }

    /**
     * Форматирует число с плавающей точкой с указанным количеством десятичных знаков
     *
     * @param value Число с плавающей точкой
     * @param decimalPlaces Количество знаков после запятой
     * @return Отформатированное представление числа
     */
    public static String format(double value, int decimalPlaces) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(decimalPlaces);
        nf.setMaximumFractionDigits(decimalPlaces);
        String temp =  nf.format(value).replace(",", " ");
        return  temp.replace(".", ",");
    }

    /**
     * Форматирует дату в формате "дд.мм.гггг"
     *
     * @param date Дата
     * @return Отформатированное представление даты
     */
    public static String format(Date date) {
        // Создаем объект SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");


        //Форматируем объект SimpleDateFormat в строку

        return sdf.format(date);
    }

    /**
     * Форматирует дату согласно указанному шаблону
     *
     * @param date Дата
     * @param pattern Шаблон форматирования (как в SimpleDateFormat)
     * @return Отформатированное представление даты
     */
    public static String format(Date date, String pattern) {
        // Создаем объект SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);


        //Форматируем объект SimpleDateFormat в строку

        return sdf.format(date);
    }
    /**
     * Форматирует список строк, объединяя их через запятую
     *
     * @param items Список строковых элементов
     * @return Объединенная строка
     */
    public static String format(List<String> items) {


        return String.join(", ", items);
    }

    /**
     * Форматирует список строк, объединяя их через указанный разделитель
     *
     * @param items Список строковых элементов
     * @param delimiter Разделитель
     * @return Объединенная строка
     */
    public static String format(List<String> items, String delimiter) {

        return String.join(delimiter, items);
    }
}