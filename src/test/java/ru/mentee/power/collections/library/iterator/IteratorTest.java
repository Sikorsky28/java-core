package ru.mentee.power.collections.library.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.collections.library.Book;
import ru.mentee.power.collections.library.Borrowing;
import ru.mentee.power.collections.library.LibraryManager;
import ru.mentee.power.collections.library.Reader;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IteratorsTest {

    private LibraryManager libraryManager;

    private Book book1, book2, book3;
    private Reader reader1, reader2;

    @BeforeEach
    void setUp() {
        Map<String, Book> library = new HashMap<>();
        Map<String, Reader> readers = new HashMap<>();
        List<Borrowing> borrowings = new ArrayList<>();
        Map<String, List<Book>> authorsMap = new HashMap<>();

        libraryManager = new LibraryManager(library, readers, borrowings, authorsMap);

        book1 = new Book("001", "Book One", 2020,  Book.Genre.FANTASY);
        book2 = new Book("002", "Book Two", 2020,  Book.Genre.FANTASY);
        book3 = new Book("003", "Book Three", 2021,  Book.Genre.SCIENCE);

        book1.addAuthor("Author A");
        book2.addAuthor("Author A");
        book2.addAuthor("Author B");
        book3.addAuthor("Author C");

        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.addBook(book3);

        reader1 = new Reader("r1", "Reader One", "readerOne@mail", Reader.ReaderCategory.VIP);
        reader2 = new Reader("r2", "Reader Two", "readerTwo@mail", Reader.ReaderCategory.STUDENT);
        libraryManager.addReader(reader1);
        libraryManager.addReader(reader2);

        borrowings.add(new Borrowing("001", "r1", LocalDate.now().minusDays(10), LocalDate.now().minusDays(1))); // просроченная
        borrowings.add(new Borrowing("002", "r2", LocalDate.now().minusDays(5), LocalDate.now().plusDays(5))); // активная
        borrowings.get(1).returnBook(LocalDate.now()); // сделаем вторую возвращенной
    }

    @Test
    @DisplayName("Итератор по жанру и году должен возвращать только книги указанного жанра и года")
    void genreAndYearIteratorShouldReturnOnlyMatchingBooks() {
        Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FANTASY, 2020);
        List<Book> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Book b = iterator.next();
            assertThat(b.getGenre()).isEqualTo(Book.Genre.FANTASY);
            assertThat(b.getPublicationYear()).isEqualTo(2020);
            results.add(b);
        }
        assertThat(results).containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    @DisplayName("Итератор по жанру и году должен вернуть пустой результат, если нет подходящих книг")
    void genreAndYearIteratorShouldReturnEmptyIteratorWhenNoMatches() {
        Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.HISTORY, 1990);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    @DisplayName("Итератор для книг с несколькими авторами должен возвращать только книги с указанным минимальным количеством авторов")
    void multipleAuthorsIteratorShouldReturnOnlyBooksWithMultipleAuthors() {
        Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(2);
        List<Book> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Book b = iterator.next();
            assertThat(b.getAuthors().size()).isGreaterThanOrEqualTo(2);
            results.add(b);
        }
        assertThat(results).containsExactly(book2);
    }

    @Test
    @DisplayName("Итератор для книг с несколькими авторами должен вернуть пустой результат, если нет подходящих книг")
    void multipleAuthorsIteratorShouldReturnEmptyIteratorWhenNoMatches() {
        Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(3);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    @DisplayName("Итератор для просроченных выдач должен возвращать только просроченные и не возвращенные выдачи")
    void overdueBorrowingsIteratorShouldReturnOnlyOverdueBorrowings() {
        Iterator<Borrowing> iterator = libraryManager.getOverdueBorrowingsIterator();
        List<Borrowing> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Borrowing b = iterator.next();
            assertThat(b.isOverdue()).isTrue();
            assertThat(b.isReturned()).isFalse();
            result.add(b);
        }
        assertThat(result).hasSize(0);
    }

    @Test
    @DisplayName("Итератор для просроченных выдач должен вернуть пустой результат, если нет просроченных выдач")
    void overdueBorrowingsIteratorShouldReturnEmptyIteratorWhenNoOverdues() {
        LibraryManager cleanManager = new LibraryManager(new HashMap<>(), new HashMap<>(), new ArrayList<>(), new HashMap<>());
        Iterator<Borrowing> iterator = cleanManager.getOverdueBorrowingsIterator();
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    @DisplayName("Все итераторы должны выбрасывать NoSuchElementException при вызове next() на пустом итераторе")
    void allIteratorsShouldThrowNoSuchElementExceptionWhenEmpty() {
        Iterator<Book> genreIterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.HISTORY, 1900);
        Iterator<Book> multiAuthorIterator = libraryManager.getBooksWithMultipleAuthorsIterator(100);
        Iterator<Borrowing> overdueIterator = new LibraryManager(new HashMap<>(), new HashMap<>(), new ArrayList<>(), new HashMap<>())
                .getOverdueBorrowingsIterator();

        assertThatThrownBy(genreIterator::next).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(multiAuthorIterator::next).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(overdueIterator::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Метод remove() итератора должен выбрасывать UnsupportedOperationException")
    void removeMethodShouldThrowException() {
        Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FANTASY, 2020);
        iterator.next();
        assertThatThrownBy(iterator::remove)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("не поддерживается");
    }
}

