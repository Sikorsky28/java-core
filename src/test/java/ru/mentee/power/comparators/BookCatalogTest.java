package ru.mentee.power.comparators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BookCatalogTest {
    private BookCatalog catalog;
    private Book book1, book2, book3, book4;

    @BeforeEach
    void setUp() {
        catalog = new BookCatalog();

        book1 = new Book("Война и мир", "Толстой Л.Н.", 1869, 1225, "Роман");
        book2 = new Book("Преступление и наказание", "Достоевский Ф.М.", 1866, 672, "Роман");
        book3 = new Book("Мастер и Маргарита", "Булгаков М.А.", 1967, 480, "Фантастика");
        book4 = new Book("Анна Каренина", "Толстой Л.Н.", 1877, 864, "Роман");

        catalog.addBook(book1);
        catalog.addBook(book2);
        catalog.addBook(book3);
        catalog.addBook(book4);
    }

    @Test
    @DisplayName("getAllBooks должен возвращать все книги в каталоге")
    void getAllBooksShouldReturnAllBooks() {
        List<Book> allBooks = catalog.getAllBooks();

        assertThat(allBooks)
                .isNotNull()
                .hasSize(4)
                .contains(book1, book2, book3, book4);
    }
    // Пришлось переписать тест, что бы ожидать список в алфавитном порядке

    @Test
    @DisplayName("sortBooks с компаратором byTitle должен сортировать по названию")
    void sortBooksByTitleShouldSortByTitle() {
        List<Book> sorted = catalog.sortBooks(BookCatalog.byTitle());

        assertThat(sorted)
                .isNotNull()
                .hasSize(4)
                .containsExactly(book4, book1, book3, book2); // По алфавиту
    }

    @Test
    @DisplayName("sortBooks с компаратором byAuthor должен сортировать по автору")
    void sortBooksByAuthorShouldSortByAuthor() {
        List<Book> sorted = catalog.sortBooks(BookCatalog.byAuthor());
        assertThat(sorted)
                .isNotNull()
                .hasSize(4)
                .containsExactly(book3, book2, book1, book4); // По алфавиту
    }

    @Test
    @DisplayName("sortBooks с компаратором byYearPublished должен сортировать по году издания")
    void sortBooksByYearPublishedShouldSortByYear() {
        List<Book> sorted = catalog.sortBooks(BookCatalog.byYearPublished());
        assertThat(sorted)
                .isNotNull()
                .hasSize(4)
                .containsExactly(book2, book1, book4, book3); // По алфавиту
    }

    @Test
    @DisplayName("sortBooks с компаратором byPageCount должен сортировать по количеству страниц")
    void sortBooksByPageCountShouldSortByPageCount() {
        List<Book> sorted = catalog.sortBooks(BookCatalog.byPageCount());
        assertThat(sorted)
                .isNotNull()
                .hasSize(4)
                .containsExactly(book3, book2, book4, book1);
    }

    @Test
    @DisplayName("multipleComparators должен создавать композитный компаратор")
    void multipleComparatorsShouldCreateCompositeComparator() {
        List<Comparator<Book>> comparators = Arrays.asList(
                Comparator.comparing(Book::getAuthor),
                Comparator.comparing(Book::getYearPublished)
        );

        List<Book> sorted = catalog.sortBooks(BookCatalog.multipleComparators(comparators));

        // Проверяем, что книги сначала отсортированы по автору,
        // а затем книги одного автора - по году издания
        assertThat(sorted)
                .isNotNull()
                .hasSize(4)
                .containsExactly(book3, book2, book1, book4);
    }

    @Test
    @DisplayName("filterBooks должен правильно фильтровать книги")
    void filterBooksShouldFilterCorrectly() {
        // Фильтруем книги автора "Толстой Л.Н."
        List<Book> tolstoyBooks = catalog.filterBooks(book -> book.getAuthor().equals("Толстой Л.Н."));

        assertThat(tolstoyBooks)
                .isNotNull()
                .hasSize(2)
                .contains(book1, book4)
                .doesNotContain(book2, book3);
    }
}