package ru.mentee.power.methods.library;

public class Book {
    private String title;
    private String author;
    private int year;
    private boolean available;

    /**
     * Создает новую книгу
     * @param title название книги
     * @param author автор книги
     * @param year год издания
     */
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public boolean getAvailable(){
        return available;
    }

    /**
     * Устанавливает статус доступности книги
     * @param available true, если книга доступна, false, если выдана
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Получает
     * @return состояние available
     */
    public boolean isAvailable() {

        return available;
    }

    /**
     * @return Строковое представление информации о книге
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Название: ").append(title)
                .append(", Автор: ").append(author)
                .append(", Год: ").append(year)
                .append(", Доступность: ").append(available ? "Доступна" : "Выдана");

        return builder.toString();
    }
}

