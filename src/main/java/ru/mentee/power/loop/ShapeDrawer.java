package ru.mentee.power.loop;

public class ShapeDrawer {

    /**
     * Метод рисует заполненный квадрат заданного размера
     *
     * @param size размер стороны квадрата
     * @return строка с изображением квадрата
     */
    public String drawSquare(int size) {

        if(size <= 0){
            return "";
        }

        StringBuilder square = new StringBuilder();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                square.append("*");
            }
            if (i < size - 1){
                square.append("\n");
            }
        }

        return square.toString();

    }

    /**
     * Метод рисует пустой квадрат (только границы) заданного размера
     *
     * @param size размер стороны квадрата
     * @return строка с изображением пустого квадрата
     */
    public String drawEmptySquare(int size) {

        if (size <= 0){
            return "";
        }
        StringBuilder emptySquare = new StringBuilder();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i == 0 || i == size - 1 || j == 0 || j == size - 1){
                    emptySquare.append("*");
                }else {
                    emptySquare.append(" ");
                }
            }
            if (i < size - 1){
                emptySquare.append("\n");
            }
        }
        return emptySquare.toString();

    }

    /**
     * Метод рисует прямоугольный треугольник заданной высоты
     *
     * @param height высота треугольника
     * @return строка с изображением треугольника
     */
    public String drawTriangle(int height) {
        if(height <= 0){
            return "";
        }
        StringBuilder triangle = new StringBuilder();

        for (int i = 1; i <= height ; i++) {
            for (int j = 1; j <= i; j++) {
                triangle.append("*");

            }
            if (i < height){
                triangle.append("\n");
            }
        }
        return triangle.toString();
    }

    /**
     * Метод рисует ромб заданного размера
     *
     * @param size размер ромба (должен быть нечетным числом)
     * @return строка с изображением ромба
     */
    public String drawRhombus(int size) {

        if (size % 2 == 0) {
            size++;
        }

        StringBuilder rhombus = new StringBuilder();

        int mid = size / 2;

        for (int i = 0; i <= mid; i++) {
            for (int j = 0; j < size; j++) {
                if (j >= mid - i && j <= mid + i) {
                    rhombus.append("*");
                } else {
                    rhombus.append(" ");
                }
            }
            if (i < mid || size % 2 == 1) { // Добавляем перенос только между строками
                rhombus.append("\n");

            }
        }
        for (int i = mid - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (j >= mid - i && j <= mid + i) {
                    rhombus.append("*");
                } else {
                    rhombus.append(" ");
                }

            }
            if (i > 0) { // Убираем последний лишний перенос строки
                rhombus.append("\n");
            }
        }

        return rhombus.toString();

    }
        /**
         * Метод выводит фигуру в консоль
         *
         * @param shape строка с изображением фигуры
         */
        public void printShape (String shape){
            System.out.println(shape);
        }

        /**
         * Главный метод для демонстрации работы программы
         */
        public static void main (String[]args){
            ShapeDrawer drawer = new ShapeDrawer();

            System.out.println("Квадрат 5x5:");
            drawer.printShape(drawer.drawSquare(5));

            System.out.println("\nПустой квадрат 5x5:");
            drawer.printShape(drawer.drawEmptySquare(5));

            System.out.println("\nТреугольник высотой 5:");
            drawer.printShape(drawer.drawTriangle(5));

            System.out.println("\nРомб размером 5:");
            drawer.printShape(drawer.drawRhombus(5));
        }

}
