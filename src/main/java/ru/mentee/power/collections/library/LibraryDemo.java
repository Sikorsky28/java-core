package ru.mentee.power.collections.library;


import java.util.*;

public class LibraryDemo {
    public static void main(String[] args) {
        // === Создание менеджера библиотеки ===
        Map<String, Book> library = new HashMap<>();
        Map<String, Reader> readers = new HashMap<>();
        List<Borrowing> borrowings = new ArrayList<>();
        Map<String, List<Book>> authorsAndBooks = new HashMap<>();
        LibraryManager manager = new LibraryManager(library, readers, borrowings, authorsAndBooks);

        // === Добавление книг ===
        Book book1 = new Book("111", "Java Basics", 2020, Book.Genre.SCIENCE);
        book1.addAuthor("John Doe");

        Book book2 = new Book("222", "History of Thailand", 2015, Book.Genre.HISTORY);
        book2.addAuthor("Anna Smith");

        Book book3 = new Book("333", "Advanced Java", 2022, Book.Genre.SCIENCE);
        book3.addAuthor("John Doe");

        manager.addBook(book1);
        manager.addBook(book2);
        manager.addBook(book3);

        // === Добавление читателей ===
        Reader reader1 = new Reader("r1", "Максим", "maksim@mail.com", Reader.ReaderCategory.STUDENT);
        Reader reader2 = new Reader("r2", "Милана", "banana@mail.com", Reader.ReaderCategory.VIP);

        manager.addReader(reader1);
        manager.addReader(reader2);

        // === Выдача книг ===
        manager.borrowBook("111", "r1", 14);
        manager.borrowBook("222", "r2", 7);

        // === Возврат книги ===
        manager.returnBook("111", "r1");

        // === Поиск по названию ===
        System.out.println("Поиск по названию 'java':");
        for (Book b : manager.searchBooksByTitle("java")) {
            System.out.println(b);
        }

        // === Сортировка по названию ===
        System.out.println("\nСортировка по названию:");
        List<Book> sortedByTitle = manager.sortBooksByTitle(manager.getAllBooks());
        for (Book b : sortedByTitle) {
            System.out.println(b);
        }

        // === Сортировка по доступности ===
        System.out.println("\nСортировка по доступности:");
        List<Book> sortedByAvailability = manager.sortBooksByAvailability(manager.getAllBooks());
        for (Book b : sortedByAvailability) {
            System.out.println(b + " | Available: " + b.isAvailable());
        }

        // === Итератор по жанру и году ===
        System.out.println("\nКниги жанра SCIENCE и года 2022:");
        Iterator<Book> sciBooks = manager.getBooksByGenreAndYearIterator(Book.Genre.SCIENCE, 2022);
        while (sciBooks.hasNext()) {
            System.out.println(sciBooks.next());
        }

        // === Получение статистики по жанрам ===
        System.out.println("\nСтатистика по жанрам:");
        Map<Book.Genre, Integer> stats = manager.getGenreStatistics();
        for (Map.Entry<Book.Genre, Integer> entry : stats.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // === Популярные книги ===
        System.out.println("\nПопулярные книги:");
        Map<Book, Integer> popular = manager.getMostPopularBooks(5);
        for (Map.Entry<Book, Integer> entry : popular.entrySet()) {
            System.out.println(entry.getKey().getTitle() + " — " + entry.getValue() + " выдач");
        }

        // === Активные читатели ===
        System.out.println("\nАктивные читатели:");
        Map<Reader, Integer> activeReaders = manager.getMostActiveReaders(5);
        for (Map.Entry<Reader, Integer> entry : activeReaders.entrySet()) {
            System.out.println(entry.getKey().getName() + " — " + entry.getValue() + " выдач");
        }
    }
}