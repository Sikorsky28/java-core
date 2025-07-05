package ru.mentee.power.collections.library.comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.collections.library.Book;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComparatorsTest {

    @Test
    @DisplayName("TitleComparator должен сортировать книги по названию в алфавитном порядке")
    void titleComparatorShouldSortBooksAlphabetically() {
        Book a = new Book("111", "Alpha", 2020, Book.Genre.HISTORY);
        Book c = new Book("112", "Charlie", 2020, Book.Genre.HISTORY);
        Book b = new Book("113", "Bravo", 2020, Book.Genre.HISTORY);
        Book b2 = new Book("114", "Bravo", 2020, Book.Genre.HISTORY);

        List<Book> books = Arrays.asList(c, b2, a, b);
        books.sort(new BookTitleComparator());

        assertThat(books)
                .extracting(Book::getTitle)
                .containsExactly("Alpha", "Bravo", "Bravo", "Charlie");
    }

    @Test
    @DisplayName("TitleComparator должен корректно обрабатывать null-значения")
    void titleComparatorShouldHandleNullValues() {
        Book withTitle = new Book("111", "Alpha",  2020, Book.Genre.HISTORY);
        Book nullTitle1 = new Book("123", null,2020, Book.Genre.HISTORY);
        Book nullTitle2 = new Book("133", null,  2020, Book.Genre.HISTORY);

        List<Book> books = Arrays.asList(nullTitle1, nullTitle2, withTitle);
        books.sort(new BookTitleComparator());

        assertThat(books)
                .extracting(Book::getTitle)
                .containsExactly(null, null, "Alpha");
    }

    @Test
    @DisplayName("PublicationYearComparator должен сортировать книги от новых к старым")
    void publicationYearComparatorShouldSortBooksFromNewToOld() {
        Book book2018 = new Book("111", "Old Book",  2018, Book.Genre.FANTASY);
        Book book2022 = new Book("112", "New Book",  2022, Book.Genre.FANTASY);
        Book book2020 = new Book("113", "Mid Book",  2020, Book.Genre.FANTASY);

        List<Book> books = Arrays.asList(book2018, book2022, book2020);
        books.sort(new BookPublicationYearComparator());

        assertThat(books)
                .extracting(Book::getPublicationYear)
                .containsExactly(2022, 2020, 2018);
    }

    @Test
    @DisplayName("AvailabilityComparator должен сортировать сначала доступные, потом недоступные книги")
    void availabilityComparatorShouldSortAvailableFirst() {
        Book available = new Book("111", "Available",  2020, Book.Genre.SCIENCE);
        Book unavailable = new Book("112", "Unavailable",  2020, Book.Genre.SCIENCE);
        unavailable.setAvailable(false);

        List<Book> books = Arrays.asList(unavailable, available);
        books.sort(new BookAvailabilityComparator());

        assertThat(books.get(0).isAvailable()).isTrue();
        assertThat(books.get(1).isAvailable()).isFalse();
    }

    @Test
    @DisplayName("GenreAndTitleComparator должен сортировать сначала по жанру, потом по названию")
    void genreAndTitleComparatorShouldSortByGenreThenByTitle() {
        Book fantasyB = new Book("111", "Bravo",  2020, Book.Genre.FANTASY);
        Book fantasyA = new Book("112", "Alpha",  2020, Book.Genre.FANTASY);
        Book scienceA = new Book("113", "Alpha",  2020, Book.Genre.SCIENCE);
        Book scienceC = new Book("114", "Charlie",  2020, Book.Genre.SCIENCE);
        Book history = new Book("115", "Delta",  2020, Book.Genre.HISTORY);

        List<Book> books = Arrays.asList(scienceC, history, fantasyB, scienceA, fantasyA);
        books.sort(new GenreAndTitleComparator());

        assertThat(books)
                .extracting(b -> b.getGenre() + " - " + b.getTitle())
                .containsExactly(
                        "FANTASY - Alpha",
                        "FANTASY - Bravo",
                        "HISTORY - Delta",
                        "SCIENCE - Alpha",
                        "SCIENCE - Charlie"
                );
    }
}
