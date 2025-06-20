package ru.mentee.power.comparators;

import java.util.*;
import java.util.function.Predicate;

/**
 * Класс для управления каталогом книг в библиотеке
 */
public class BookCatalog {
    private List<Book> books;

    /**
     * Создает пустой каталог книг
     */
    public BookCatalog() {
        this.books = new ArrayList<>();
    }

    /**
     * Добавляет книгу в каталог
     * @param book книга для добавления
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Возвращает неизменяемый список всех книг в каталоге
     * @return список книг
     */
    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        for (Book book : books){
            if (book != null){
                allBooks.add(book);
            }
        }
        return  List.copyOf(allBooks);
    }

    /**
     * Сортирует книги по заданному компаратору
     * @param comparator компаратор для сортировки
     * @return новый отсортированный список книг (исходный список не меняется)
     */
    public List<Book> sortBooks(Comparator<Book> comparator) {
        List<Book> sortBooks = new ArrayList<>(books);
        sortBooks.sort(comparator);



      return sortBooks;
    }

    /**
     * Фильтрует книги по заданному предикату
     * @param predicate условие фильтрации
     * @return новый список книг, удовлетворяющих условию
     */
    public List<Book> filterBooks(Predicate<Book>  predicate) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if ( predicate.test(book)) {
                result.add(book);
            }
        }
        return result;
    }

    // Статические компараторы для удобства использования

    /**
     * @return компаратор для сортировки по названию (по алфавиту)
     */
    public static Comparator<Book> byTitle() {
        return Comparator.comparing(
                Book::getTitle,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        );
    }

    /**
     * @return компаратор для сортировки по автору (по алфавиту)
     */
    public static Comparator<Book> byAuthor() {

        return Comparator.comparing(
                Book::getAuthor,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        );
    }

    /**
     * @return компаратор для сортировки по году издания (от старых к новым)
     */
    public static Comparator<Book> byYearPublished() {
        return Comparator.comparingInt(Book::getYearPublished);
    }

    /**
     * @return компаратор для сортировки по количеству страниц (от меньшего к большему)
     */
    public static Comparator<Book> byPageCount() {
        return Comparator.comparingInt(Book::getPageCount);
    }

    /**
     * Создает сложный компаратор для сортировки по нескольким критериям
     * @param comparators список компараторов в порядке приоритета
     * @return композитный компаратор
     */
    public static Comparator<Book> multipleComparators(List<Comparator<Book>> comparators) {
        if (comparators == null || comparators.isEmpty()) {
            throw new IllegalArgumentException("Список компараторов не должен быть пустым");
        }

        Comparator<Book> combined = comparators.get(0);

        for (int i = 1; i < comparators.size(); i++) {
            combined = combined.thenComparing(comparators.get(i));
        }

        return combined;
    }
}