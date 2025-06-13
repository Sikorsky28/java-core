package ru.mentee.power.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

public class Matrix<T> implements Iterable<T> {
    private final T[][] data;
    private final int rows;
    private final int columns;

    @SuppressWarnings("unchecked")
    public Matrix(T[][] data) {
        if (data == null) {
            throw new IllegalArgumentException("Массив данных не может быть null");
        }

        this.rows = data.length;
        if (rows == 0) {
            this.columns = 0;
        } else {
            this.columns = data[0].length;
            for (T[] row : data) {
                if (row.length != columns) {
                    throw new IllegalArgumentException("Все строки матрицы должны иметь одинаковую длину");
                }
            }
        }

        this.data = (T[][]) new Object[rows][];
        for (int i = 0; i < rows; i++) {
            this.data[i] = (T[]) new Object[columns];
            System.arraycopy(data[i], 0, this.data[i], 0, columns);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public T get(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Индексы за пределами матрицы");
        }
        return data[row][column];
    }

    public void set(int row, int column, T value) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Индексы за пределами матрицы");
        }
        data[row][column] = value;
    }

    @Override
    public Iterator<T> iterator() {
        return new RowMajorIterator();
    }

    public Iterator<T> rowMajorIterator() {
        return new RowMajorIterator();
    }

    public Iterator<T> columnMajorIterator() {
        return new ColumnMajorIterator();
    }

    public Iterator<T> spiralIterator() {
        return new SpiralIterator();
    }

    public Iterator<T> zigzagIterator() {
        return new ZigzagIterator();
    }

    /**
     * Построчный итератор (слева направо, сверху вниз)
     */
    private class RowMajorIterator implements Iterator<T> {
        private int row = 0;
        private int col = 0;

        @Override
        public boolean hasNext() {
            return row < rows;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T value = data[row][col++];
            if (col == columns) {
                col = 0;
                row++;
            }
            return value;
        }
    }

    /**
     * Итератор по столбцам (сверху вниз, слева направо)
     */
    private class ColumnMajorIterator implements Iterator<T> {
        private int row = 0;
        private int col = 0;

        @Override
        public boolean hasNext() {
            return col < columns && row < rows;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T value = data[row++][col];
            if (row == rows) {
                row = 0;
                col++;
            }

            return value;
        }
    }

    /**
     * Итератор по спирали от центра к краям
     */
    private class SpiralIterator implements Iterator<T> {
        private final List<T> spiral = new ArrayList<>();
        private int index = 0;

        public SpiralIterator() {
            if (rows == 0 || columns == 0) return;

            boolean[][] visited = new boolean[rows][columns];
            int total = rows * columns;
            int x = rows / 2;
            int y = columns / 2;
            if (rows % 2 == 0) x--;
            if (columns % 2 == 0) y--;

            int[] dx = {0, 1, 0, -1};
            int[] dy = {1, 0, -1, 0};
            int direction = 0;
            int steps = 1;

            spiral.add(data[x][y]);
            visited[x][y] = true;

            while (spiral.size() < total) {
                for (int side = 0; side < 2; side++) {
                    for (int i = 0; i < steps; i++) {
                        x += dx[direction];
                        y += dy[direction];

                        if (x >= 0 && x < rows && y >= 0 && y < columns && !visited[x][y]) {
                            spiral.add(data[x][y]);
                            visited[x][y] = true;
                        }
                    }
                    direction = (direction + 1) % 4;
                }
                steps++;
            }
        }

        @Override
        public boolean hasNext() {
            return index < spiral.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return spiral.get(index++);
        }
    }

    /**
     * Итератор змейкой (зигзаг: слева направо, затем справа налево)
     */
    private class ZigzagIterator implements Iterator<T> {
        private int row = 0;
        private int col = 0;
        private boolean leftToRight = true;

        @Override
        public boolean hasNext() {
            return row < rows;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T value = data[row][col];

            if (leftToRight) {
                col++;
                if (col == columns) {
                    col = columns - 1;
                    row++;
                    leftToRight = false;
                }
            } else {
                col--;
                if (col < 0) {
                    col = 0;
                    row++;
                    leftToRight = true;
                }
            }

            return value;
        }
    }
}
