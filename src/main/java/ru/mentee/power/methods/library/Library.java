package ru.mentee.power.methods.library;

public class Library {
    // обратите внимание тут books - это не список а массив
    private Book[] books;
    private int bookCount;

    /**
     * Создает новую библиотеку с заданной вместимостью
     * @param capacity максимальное количество книг
     */
    public Library(int capacity) {
        books = new Book[capacity];
    }

    /**
     * Добавляет книгу в библиотеку
     * @param book книга для добавления
     * @return true, если книга добавлена успешно, false, если библиотека переполнена
     */
    public boolean addBook(Book book) {

        if (bookCount < books.length){
            books[bookCount] = book;
            bookCount++;
            book.setAvailable(true);
            return true;
        }

        return false;
    }

    /**
     * Ищет книгу по названию
     * @param title название книги
     * @return найденная книга или null, если книга не найдена
     */
    public Book findBookByTitle(String title) {
        for (int i = 0; i < bookCount; i++) {
            if(title != null && books[i].getTitle().equalsIgnoreCase(title)){
                return books[i];
            }
        }
        return null;
    }

    /**
     * Выдает книгу читателю
     * @param title название книги
     * @return true, если книга успешно выдана, false, если книга не найдена или уже выдана
     */
    public boolean checkoutBook(String title) {
        Book book = findBookByTitle(title); // Находим книгу
        if (book != null && book.isAvailable()) { // Проверяем доступность
            book.setAvailable(false); // Выдаём книгу
            return true;
        }
        return false;
    }

    /**
     * Возвращает книгу в библиотеку
     * @param title название книги
     * @return true, если книга успешно возвращена, false, если книга не найдена или уже доступна
     */
    public boolean returnBook(String title) {
        Book book = findBookByTitle(title);
        if(book != null && !book.isAvailable()){
            book.setAvailable(true);
            return true;
        }

        return false;
    }

    /**
     * Возвращает массив доступных книг
     * @return массив доступных книг
     */
    public Book[] listAvailableBooks() {
        int count = 0;

        for (int i = 0; i < bookCount; i++) {
            if(books[i] != null && books[i].isAvailable()){
                count++;

            }
        }
        Book[] availableBooks = new Book[count];

        int index = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i] != null && books[i].isAvailable()){

                availableBooks[index] = books[i];
                index++;
            }
        }


        return availableBooks;
    }

    /**
     * Возвращает массив выданных книг
     * @return массив выданных книг
     */
    public Book[] listCheckedOutBooks() {
        int count = 0;

        for (int i = 0; i < bookCount; i++) {
            if (books[i] != null && !books[i].isAvailable()){
                count ++;
            }
        }
        Book[] checkedOutBooks = new Book[count];

        int index = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i] != null && !books[i].isAvailable()){

                checkedOutBooks[index] = books[i];
                index++;
            }
        }
        return checkedOutBooks;
    }
}