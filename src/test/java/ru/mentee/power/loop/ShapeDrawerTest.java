package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShapeDrawerTest {

    private final ShapeDrawer drawer = new ShapeDrawer();

    @Test
    void testDrawSquare() {
        // Подготовка ожидаемого результата для квадрата 3x3
        String expected = "***\n***\n***";

        // Вызов тестируемого метода
        String result = drawer.drawSquare(3);

        // Проверка результата
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawEmptySquare() {
        // Подготовка ожидаемого результата для пустого квадрата 3x3
        String expected = "***\n* *\n***";

        // Вызов тестируемого метода
        String result = drawer.drawEmptySquare(3);

        // Проверка результата
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawTriangle() {
        // Подготовка ожидаемого результата для треугольника высотой 3
        String expected = "*\n**\n***";

        // Вызов тестируемого метода
        String result = drawer.drawTriangle(3);


        // Проверка результата
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawRhombus() {
        // Подготовка ожидаемого результата для ромба размером 3
        String expected = " * \n***\n * ";

        // Вызов тестируемого метода
        String result = drawer.drawRhombus(3);

        // Проверка результата
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testWithZeroOrNegativeSize() {

        String expected = "";

        String result = drawer.drawEmptySquare(0);


        assertThat(result).isEqualTo(expected);

    }

    @Test
    void testWithLargeSize() {

        String expected = "**********\n**********\n**********\n**********\n**********\n**********\n**********\n**********\n**********\n**********";

        String result = drawer.drawSquare(10);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testRhombusWithEvenSize() {



        ShapeDrawer drawer = new ShapeDrawer();

        // Ожидаемый результат при size = 4 (метод должен сделать size = 5)
        String expected =
                "  *  \n" +
                        " *** \n" +
                        "*****\n" +
                        " *** \n" +
                        "  *  ";

        String actual = drawer.drawRhombus(4); // Вызываем метод с четным числом

        assertEquals(expected, actual, "Ромб с чётным размером должен быть скорректирован до нечётного!");
    }
}