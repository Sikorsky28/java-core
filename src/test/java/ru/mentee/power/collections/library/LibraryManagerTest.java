package ru.mentee.power.collections.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.*;

class LibraryManagerTest {

    private LibraryManager libraryManager;
    private Book book1, book2, book3;
    private Reader reader1, reader2;

    @BeforeEach
    void setUp() {
        Map<String, Book> library = new HashMap<>();
        Map<String, Reader> readers = new HashMap<>();
        List<Borrowing> borrowings = new ArrayList<>();
        Map<String, List<Book>> authorsAndTheirBooks = new HashMap<>();

        libraryManager = new LibraryManager(library, readers, borrowings, authorsAndTheirBooks);

        book1 = new Book("ISBN1", "Java Basics", 2020, Book.Genre.FANTASY);
        book2 = new Book("ISBN2", "Spring Boot Guide", 2022, Book.Genre.FICTION);
        book3 = new Book("ISBN3", "History of Rome", 2018, Book.Genre.HISTORY);

        book1.addAuthor("Михаил Луконин");
        book2.addAuthor("Эрнест Самонов");
        book3.addAuthor("Михаил Лук");


        reader1 = new Reader("R1", "Alice", "alice@example.com", Reader.ReaderCategory.STUDENT);
        reader2 = new Reader("R2", "Bob", "bob@example.com", Reader.ReaderCategory.TEACHER);

        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.addBook(book3);

        libraryManager.addReader(reader1);
        libraryManager.addReader(reader2);
    }


    @Nested
    @DisplayName("Тесты CRUD операций с книгами")
    class BookCrudTests {
        @Test
        @DisplayName("Должен корректно добавлять книгу в библиотеку")
        void shouldAddBookCorrectly() {
            Book newBook = new Book("ISBN5", "Java", 2020, Book.Genre.FANTASY);
            newBook.addAuthor("Milana");
            boolean added = libraryManager.addBook(newBook);
            Book retrived = libraryManager.getBookByIsbn("ISBN5");

            assertThat(added).isTrue();
            assertThat(retrived.getTitle()).isEqualTo("Java");

        }

        @Test
        @DisplayName("Не должен добавлять дубликат книги")
        void shouldNotAddDuplicateBook() {
            Book dublicate = new Book("ISBN1", "Java Basics", 2020, Book.Genre.FANTASY);
            boolean isAdded = libraryManager.addBook(dublicate);
            assertThat(isAdded).isFalse();
        }

        @Test
        @DisplayName("Должен возвращать книгу по ISBN")
        void shouldReturnBookByIsbn() {
            Book correct = libraryManager.getBookByIsbn("ISBN1");

            assertThat(correct.getTitle()).isEqualTo("Java Basics");
            assertThat(correct.getPublicationYear()).isEqualTo(2020);
        }

        @Test
        @DisplayName("Должен возвращать null при поиске книги по несуществующему ISBN")
        void shouldReturnNullForNonExistingIsbn() {
            Book result = libraryManager.getBookByIsbn("31");

            assertThat(result).isEqualTo(null);
        }

        @Test
        @DisplayName("Должен корректно удалять книгу из библиотеки")
        void shouldRemoveBookCorrectly() {
            boolean isRemoved = libraryManager.removeBook("ISBN1");
            assertThat(isRemoved).isTrue();

            Book res = libraryManager.getBookByIsbn("ISBN1");
            assertThat(res).isEqualTo(null);


        }

        @Test
        @DisplayName("Должен возвращать false при попытке удалить несуществующую книгу")
        void shouldReturnFalseWhenRemovingNonExistingBook() {
            boolean isRemoved = libraryManager.removeBook("ISBN69");
            assertThat(isRemoved).isFalse();
        }
    }

    @Nested
    @DisplayName("Тесты поиска и фильтрации книг")
    class BookSearchAndFilterTests {
        @Test
        @DisplayName("Должен возвращать список всех книг")
        void shouldReturnAllBooks() {
            List<Book> res =  libraryManager.getAllBooks();
            assertThat(res).hasSize(3);
            assertThat(res).containsExactlyInAnyOrder(book1, book2, book3);
        }

        @Test
        @DisplayName("Должен возвращать список книг определенного жанра")
        void shouldReturnBooksByGenre() {
            Book.Genre genre = Book.Genre.FANTASY;

            List<Book> res =  libraryManager.getBooksByGenre(genre);
            assertThat(res).containsExactlyInAnyOrder(book1);
            assertThat(res).allMatch(book -> book.getGenre() == genre);
        }

        @Test
        @DisplayName("Должен возвращать список книг определенного автора")
        void shouldReturnBooksByAuthor() {
            String author = "Михаил Луконин";
            List<Book> booksByAuthor = libraryManager.getBooksByAuthor(author);
            assertThat(booksByAuthor).allMatch(book -> book.getAuthors().contains(author));
            assertThat(booksByAuthor).noneMatch(book -> !book.getAuthors().contains(author));
        }

        @Test
        @DisplayName("Должен находить книги по части названия")
        void shouldFindBooksByTitlePart() {
            String predicate = "Spring";
            List<Book> booksByTitlePart = libraryManager.searchBooksByTitle(predicate);
            assertThat(booksByTitlePart).allMatch(book -> book.getTitle().toLowerCase().contains(predicate.toLowerCase()));
        }

        @Test
        @DisplayName("Должен возвращать только доступные книги")
        void shouldReturnOnlyAvailableBooks() {
            libraryManager.borrowBook("ISBN1", "R1", 7);
            List<Book> availableBooks = libraryManager.getAvailableBooks();
            assertThat(availableBooks).allMatch(book -> book.isAvailable() == true);
            assertThat(availableBooks).noneMatch(book -> !book.isAvailable() == true);
        }
    }

    @Nested
    @DisplayName("Тесты сортировки книг")
    class BookSortingTests {
        @Test
        @DisplayName("Должен корректно сортировать книги по названию")
        void shouldSortBooksByTitle() {
            List<Book> books = libraryManager.getAllBooks();
            libraryManager.sortBooksByTitle(books);
            assertThat(books).containsExactly(book1,book3,book2);
        }

        @Test
        @DisplayName("Должен корректно сортировать книги по году публикации")
        void shouldSortBooksByPublicationYear() {
            List<Book> books = libraryManager.getAllBooks();
            List<Book> sorted = libraryManager.sortBooksByPublicationYear(books);

            for (int i = 0; i < sorted.size() - 1; i++) {
                int current = sorted.get(i).getPublicationYear();
                int next = sorted.get(i + 1).getPublicationYear();
                assertThat(current).isGreaterThanOrEqualTo(next);
            }
        }

        @Test
        @DisplayName("Должен корректно сортировать книги по доступности")
        void shouldSortBooksByAvailability() {
            libraryManager.borrowBook("ISBN1", "R1", 7);
            List<Book> books = libraryManager.getAllBooks();
            List<Book> sorted = libraryManager.sortBooksByAvailability(books);
            boolean foundUnavailable = false;
            for (Book book : sorted) {
                if (!book.isAvailable()) {
                    foundUnavailable = true;
                } else {
                    // Доступные должны идти перед недоступными
                    assertThat(foundUnavailable).isFalse();
                }
            }

        }
    }

    @Nested
    @DisplayName("Тесты CRUD операций с читателями")
    class ReaderCrudTests {
        @Test
        @DisplayName("Должен корректно добавлять читателя")
        void shouldAddReaderCorrectly() {
            Reader newReader = new Reader("13", "Milana", "milana@com", Reader.ReaderCategory.STUDENT);
            boolean res = libraryManager.addReader(newReader);
            assertThat(res).isTrue();
            assertThat(libraryManager.getReaderById("13")).isEqualTo(newReader);
        }

        @Test
        @DisplayName("Не должен добавлять дубликат читателя")
        void shouldNotAddDuplicateReader() {
            Reader reader = new Reader("R2", "Bobby", "bob@example.com", Reader.ReaderCategory.TEACHER);
            boolean res = libraryManager.addReader(reader);
            assertThat(res).isFalse();
        }

        @Test
        @DisplayName("Должен возвращать читателя по ID")
        void shouldReturnReaderById() {
            Reader res = libraryManager.getReaderById("R1");
            assertThat(res).isEqualTo(reader1);
        }

        @Test
        @DisplayName("Должен возвращать null при поиске читателя по несуществующему ID")
        void shouldReturnNullForNonExistingReaderId() {
            Reader res = libraryManager.getReaderById("R5");
            assertThat(res).isEqualTo(null);
        }

        @Test
        @DisplayName("Должен корректно удалять читателя")
        void shouldRemoveReaderCorrectly() {
            boolean res = libraryManager.removeReader("R1");
            assertThat(res).isTrue();

            Reader removed = libraryManager.getReaderById("R1");
            assertThat(removed).isEqualTo(null);
        }
    }

    @Nested
    @DisplayName("Тесты операций с выдачей книг")
    class BorrowingOperationsTests {
        @Test
        @DisplayName("Должен корректно оформлять выдачу книги")
        void shouldBorrowBookCorrectly() {
            List<Book> books = libraryManager.getAvailableBooks();
            Book oneBook = books.getFirst();
            Reader reader = libraryManager.getReaderById("R1");

            boolean borrowing = libraryManager.borrowBook(oneBook.getIsbn(), reader.getId(), 5);

            assertThat(borrowing).isTrue();
            assertThat(oneBook.isAvailable()).isFalse();
            assertThat(libraryManager.getBorrowingsByBook(oneBook.getIsbn())).isNotEmpty();
        }

        @Test
        @DisplayName("Не должен выдавать недоступную книгу")
        void shouldNotBorrowUnavailableBook() {
            // Выдаем book1 одному читателю
            libraryManager.borrowBook(book1.getIsbn(), reader1.getId(), 5);

            // Пытаемся выдать ту же книгу другому читателю
            boolean borrowedAgain = libraryManager.borrowBook(book1.getIsbn(), reader2.getId(),5);

            assertThat(borrowedAgain).isFalse();
        }

        @Test
        @DisplayName("Должен корректно оформлять возврат книги")
        void shouldReturnBookCorrectly() {
            // Выдаем book1 одному читателю
            libraryManager.borrowBook(book1.getIsbn(), reader1.getId(), 5);
            boolean returned = libraryManager.returnBook(book1.getIsbn(), reader1.getId());

            assertThat(returned).isTrue();
            assertThat(libraryManager.getBookByIsbn(book1.getIsbn()).isAvailable()).isTrue();
            assertThat(libraryManager.getBorrowingsByBook(book1.getIsbn())).anyMatch(borrowing -> borrowing.getReturnDate() != null);
        }

        @Test
        @DisplayName("Должен возвращать список просроченных выдач")
        void shouldReturnOverdueBorrowings() {
            List<Borrowing> overdue = libraryManager.getOverdueBorrowings();

            assertThat(overdue).allMatch(b -> b.isOverdue());
            assertThat(overdue).noneMatch(b -> !b.isOverdue());
        }

        @Test
        @DisplayName("Должен корректно продлевать срок выдачи")
        void shouldExtendBorrowingPeriodCorrectly() {
            libraryManager.borrowBook(book1.getIsbn(), reader1.getId(), 5);
            List<Borrowing> borrowings = libraryManager.getBorrowingsByReader(reader1.getId());

            Borrowing borrowing = borrowings.getFirst();
            LocalDate oldDueDate = borrowing.getDueDate();

            boolean res = libraryManager.extendBorrowingPeriod(book1.getIsbn(), reader1.getId(), 3);

            assertThat(res).isTrue();
            assertThat(borrowing.getDueDate()).isEqualTo(oldDueDate.plusDays(3));
        }
    }

    @Nested
    @DisplayName("Тесты статистики и отчетов")
    class StatisticsAndReportsTests {
        @Test
        @DisplayName("Должен возвращать корректную статистику по жанрам")
        void shouldReturnCorrectGenreStatistics() {
            Map<Book.Genre, Integer> statistics =  libraryManager.getGenreStatistics();
            Set<Book.Genre> genres = libraryManager.getGenreStatistics().keySet();

            assertThat(statistics.keySet().containsAll(genres));

            Map<Book.Genre, Integer> expected = Map.of(
                    Book.Genre.FICTION, 1,
                    Book.Genre.FANTASY, 1,
                    Book.Genre.HISTORY, 1
            );
            assertThat(statistics).isEqualTo(expected);
        }

        @Test
        @DisplayName("Должен возвращать список самых популярных книг")
        void shouldReturnMostPopularBooks() {
            // 1. Создать выдачи с разным количеством повторений
            libraryManager.borrowBook(book1.getIsbn(), reader1.getId(), 5);
            libraryManager.borrowBook(book1.getIsbn(), reader2.getId(), 5); // book1 — 2 выдачи
            libraryManager.borrowBook(book2.getIsbn(), reader1.getId(), 5); // book2 — 1 выдача
            libraryManager.borrowBook(book3.getIsbn(), reader2.getId(), 5);
            libraryManager.borrowBook(book3.getIsbn(), reader1.getId(), 5); // book3 — 2 выдачи

            int limit = 2;
            Map<Book, Integer> popularBooks = libraryManager.getMostPopularBooks(limit);
            assertThat(popularBooks).hasSizeLessThanOrEqualTo(limit);

            List<Integer> borrowCounts = new ArrayList<>(popularBooks.values());
            assertThat(borrowCounts)
                    .as("Список выдач должен быть отсортирован по убыванию")
                    .isSortedAccordingTo(Comparator.reverseOrder());
        }

        @Test
        @DisplayName("Должен возвращать список самых активных читателей")
        void shouldReturnMostActiveReaders() {
            // 1. Создаём несколько выдач для разных читателей
            libraryManager.borrowBook(book1.getIsbn(), reader1.getId(), 5);
            libraryManager.borrowBook(book2.getIsbn(), reader1.getId(), 5); // 2 выдачи
            libraryManager.borrowBook(book3.getIsbn(), reader2.getId(), 5); // 1 выдача
            libraryManager.borrowBook(book1.getIsbn(), reader1.getId(), 5);
            libraryManager.borrowBook(book2.getIsbn(), reader2.getId(), 5); // 2 выдачи

            int limit = 2;

            // 2. Получаем самых активных читателей
            Map<Reader, Integer> activeReaders = libraryManager.getMostActiveReaders(limit);

            // 3. Проверяем, что возвращено не больше читателей, чем указано в лимите
            assertThat(activeReaders).hasSizeLessThanOrEqualTo(limit);

            // 4. Проверяем, что они отсортированы по убыванию количества выдач
            List<Integer> issueCounts = new ArrayList<>(activeReaders.values());
            assertThat(issueCounts)
                    .as("Список должен быть отсортирован по убыванию количества выдач")
                    .isSortedAccordingTo(Comparator.reverseOrder());
        }

        @Test
        @DisplayName("Должен возвращать список читателей с просроченными книгами")
        void shouldReturnReadersWithOverdueBooks() {
            // 1. Устанавливаем вручную даты, чтобы симулировать просрочку
            LocalDate today = LocalDate.now();

            // Читатель 1 — просрочил книгу
            libraryManager.borrowBook(book1.getIsbn(), reader1.getId(), 3); // dueDate = today + 3
            // "отматываем" дату назад
            Borrowing borrowing1 = libraryManager.getBorrowingsByReader(reader1.getId()).getFirst();
            borrowing1.setDueDate(today.minusDays(2)); // просрочена

            // Читатель 2 — всё в порядке
            libraryManager.borrowBook(book2.getIsbn(), reader2.getId(), 5); // ещё не просрочена



            // 2. Получаем список читателей с просроченными книгами
            List<Reader> overdueReaders = libraryManager.getReadersWithOverdueBooks();

            // 3. Проверяем, что только reader1 включен
            assertThat(overdueReaders)
                    .extracting(Reader::getId)
                    .containsExactlyInAnyOrder(reader1.getId());

            // 4. Проверяем, что каждый из них действительно имеет хотя бы одну просрочку
            for (Reader reader : overdueReaders) {
                List<Borrowing> borrowings = libraryManager.getBorrowingsByReader(reader.getId());
                boolean hasOverdue = borrowings.stream()
                        .anyMatch(b -> b.getDueDate().isBefore(today) && !b.isReturned());

                assertThat(hasOverdue)
                        .as("Читатель " + reader.getId() + " должен иметь просроченную книгу")
                        .isTrue();
            }
        }
    }

    @Nested
    @DisplayName("Тесты итераторов")
    class IteratorsTests {
        @Test
        @DisplayName("Должен корректно итерироваться по книгам определенного жанра и года")
        void shouldIterateOverBooksByGenreAndYear() {
            Book bookFantasy2020 = new Book("F-2020", "FantasyBook", 2020, Book.Genre.FANTASY);
            Book bookFantasy2021 = new Book("F-2021", "AnotherFantasy", 2021, Book.Genre.FANTASY);
            Book bookSciFi2020 = new Book("S-2020", "SciFiBook", 2020, Book.Genre.SCIENCE);
            libraryManager.addBook(bookFantasy2020);
            libraryManager.addBook(bookFantasy2021);
            libraryManager.addBook(bookSciFi2020);


            Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FANTASY, 2020);
            List<Book> result = new ArrayList<>();
            while (iterator.hasNext()) {
                result.add(iterator.next());
            }

            assertThat(result).containsExactly(book1,bookFantasy2020);
        }


        @Test
        @DisplayName("Должен корректно итерироваться по книгам с несколькими авторами")
        void shouldIterateOverBooksWithMultipleAuthors() {
            Book oneAuthor = new Book("1", "Single", 2022, Book.Genre.FANTASY);
            Book twoAuthors = new Book("2", "Double", 2022, Book.Genre.FANTASY);
            Book threeAuthors = new Book("3", "Triple", 2022, Book.Genre.FANTASY);
            oneAuthor.addAuthor("One");
            twoAuthors.addAuthor("a");
            twoAuthors.addAuthor("b");
            threeAuthors.addAuthor("author");
            threeAuthors.addAuthor("second");
            threeAuthors.addAuthor("third");

            libraryManager.addBook(oneAuthor);
            libraryManager.addBook(twoAuthors);
            libraryManager.addBook(threeAuthors);

            Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(2);
            List<Book> result = new ArrayList<>();
            while (iterator.hasNext()) {
                result.add(iterator.next());
            }

            assertThat(result).containsExactlyInAnyOrder(twoAuthors, threeAuthors);
        }

        @Test
        @DisplayName("Должен корректно итерироваться по просроченным выдачам")
        void shouldIterateOverOverdueBorrowings() {
            // 1. Устанавливаем вручную даты, чтобы симулировать просрочку
            LocalDate today = LocalDate.now();

            // Читатель 1 — просрочил книгу
            libraryManager.borrowBook(book1.getIsbn(), reader1.getId(), 3); // dueDate = today + 3
            // "отматываем" дату назад
            Borrowing borrowing1 = libraryManager.getBorrowingsByReader(reader1.getId()).getFirst();
            borrowing1.setDueDate(today.minusDays(2)); // просрочена

            // Читатель 2 — всё в порядке
            libraryManager.borrowBook(book2.getIsbn(), reader2.getId(), 5); // ещё не просрочена
            Borrowing borrowing2 = libraryManager.getBorrowingsByReader(reader2.getId()).getFirst();
            borrowing2.setDueDate(today);

            Iterator<Borrowing> iterator = libraryManager.getOverdueBorrowingsIterator();

            List<Borrowing> result = new ArrayList<>();
            while (iterator.hasNext()) {
                result.add(iterator.next());
            }
            assertThat(result).containsExactly(borrowing1);
            assertThat(result).doesNotContain(borrowing2);

        }

        @Test
        @DisplayName("Должен выбрасывать NoSuchElementException при отсутствии следующего элемента")
        void shouldThrowNoSuchElementExceptionWhenNoMoreElements() {
            Book emptyBook = new Book("EMPTY", "None", 2020, Book.Genre.FANTASY);
            libraryManager.addBook(emptyBook);

            Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.SCIENCE, 1999); // ничего не подходит

            assertThat(iterator.hasNext()).isFalse();
            assertThatThrownBy(iterator::next)
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessageContaining("Нет книг указанного жанра и года");
        }
    }
}