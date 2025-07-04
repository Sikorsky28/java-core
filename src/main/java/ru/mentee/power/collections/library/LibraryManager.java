package ru.mentee.power.collections.library;


import ru.mentee.power.collections.library.comparator.BookAvailabilityComparator;
import ru.mentee.power.collections.library.comparator.BookTitleComparator;
import ru.mentee.power.collections.library.comparator.PopularBookComparator;

import java.time.LocalDate;
import java.util.*;

public class LibraryManager {

    private Map<String, Book> library;
    private Map<String, Reader> readers;
    private List<Borrowing> borrowings;
    private Map<Book.Genre, Set<Book>> genreSetMap;
    private Map<String, List<Book>> authorsAndTheirBooks;

    private class BooksByGenreAndYearIterator implements Iterator<Book> {
        private final Iterator<Book> internal;
        private final int year;
        private Book nextBook;
        private boolean hasNextCalculated = false;

        public BooksByGenreAndYearIterator(Book.Genre genre, int year) {
            this.internal = genreSetMap.getOrDefault(genre, Collections.emptySet()).iterator();
            this.year = year;
        }

        @Override
        public boolean hasNext() {
            if (hasNextCalculated) return nextBook != null;
            while (internal.hasNext()) {
                Book b = internal.next();
                if (b.getPublicationYear() == year) {
                    nextBook = b;
                    hasNextCalculated = true;
                    return true;
                }
            }
            nextBook = null;
            hasNextCalculated = true;
            return false;
        }

        @Override
        public Book next() {
            if (!hasNext()) throw new NoSuchElementException("Нет книг указанного жанра и года");
            hasNextCalculated = false;
            return nextBook;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Удаление книг не поддерживается");
        }
    }


    private class BooksWithMultipleAuthorsIterator implements Iterator<Book> {
        private final Iterator<Book> internal;
        private final int minAuthorsCount;
        private Book nextBook;
        private boolean hasNextCalculated = false;

        public BooksWithMultipleAuthorsIterator(int minAuthorsCount) {
            this.internal = library.values().iterator();
            this.minAuthorsCount = minAuthorsCount;
        }

        @Override
        public boolean hasNext() {
            if (hasNextCalculated) return nextBook != null;
            while (internal.hasNext()) {
                Book b = internal.next();
                if (b.getAuthors() != null && b.getAuthors().size() >= minAuthorsCount) {
                    nextBook = b;
                    hasNextCalculated = true;
                    return true;
                }
            }
            nextBook = null;
            hasNextCalculated = true;
            return false;
        }

        @Override
        public Book next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Нет книг с " + minAuthorsCount + " или более авторами");
            }
            hasNextCalculated = false;
            return nextBook;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Удаление книг через итератор не поддерживается");
        }
    }

    private class OverdueBorrowingsIterator implements Iterator<Borrowing> {
        private final Iterator<Borrowing> internal;
        private Borrowing nextBorrowing;
        private boolean hasNextCalculated = false;

        public OverdueBorrowingsIterator() {
            this.internal = borrowings.iterator();
        }

        @Override
        public boolean hasNext() {
            if (hasNextCalculated) return nextBorrowing != null;
            while (internal.hasNext()) {
                Borrowing b = internal.next();
                if (b.isOverdue()) {
                    nextBorrowing = b;
                    hasNextCalculated = true;
                    return true;
                }
            }
            nextBorrowing = null;
            hasNextCalculated = true;
            return false;
        }

        @Override
        public Borrowing next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Нет просроченных выдач");
            }
            hasNextCalculated = false;
            return nextBorrowing;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Удаление выдач через итератор не поддерживается");
        }
    }



    public LibraryManager(
            Map<String, Book> library,
            Map<String, Reader> readers,
            List<Borrowing> borrowings,
            Map<String, List<Book>> authorsAndTheirBooks
    ) {
        this.library = library;
        this.readers = readers;
        this.borrowings = new ArrayList<>();
        this.genreSetMap = new HashMap<>();
        this.authorsAndTheirBooks = authorsAndTheirBooks;
    }

    // ============ Методы для работы с книгами ============

    public boolean addBook(Book book) {
        if (library.containsKey(book.getIsbn())) {
            return false; // Книга с таким ISBN уже есть
        }

        library.put(book.getIsbn(), book);

        // Обновляем genreSetMap
        Set<Book> booksOfGenre = genreSetMap.get(book.getGenre());
        if (booksOfGenre == null) {
            booksOfGenre = new HashSet<>();
            genreSetMap.put(book.getGenre(), booksOfGenre);
        }
        booksOfGenre.add(book);

        // Обновляем authorsAndTheirBooks
        for (String author : book.getAuthors()) {
            List<Book> booksByAuthor = authorsAndTheirBooks.get(author);
            if (booksByAuthor == null) {
                booksByAuthor = new ArrayList<>();
                authorsAndTheirBooks.put(author, booksByAuthor);
            }
            booksByAuthor.add(book);
        }

        return true;
    }


    /**
     * Получает книгу по ISBN
     * @param isbn ISBN книги
     * @return книга или null, если книга не найдена
     */
    public Book getBookByIsbn(String isbn) {
        if (isbn == null){
            throw new IllegalArgumentException("Пожалуйста введите isbn  в корректном формате");
        }
        return library.get(isbn);
    }

    /**
     * Удаляет книгу из библиотеки
     * @param isbn ISBN книги для удаления
     * @return true если книга удалена, false если книга не найдена
     */
    public boolean removeBook(String isbn) {
        if (isbn == null){
            throw new IllegalArgumentException("Пожалуйста введите isbn  в корректном формате");
        }
        return library.remove(isbn) != null;
    }

    /**
     * Возвращает список всех книг
     * @return список книг
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(library.values());
    }

    /**
     * Возвращает список книг определенного жанра
     * @param genre жанр
     * @return список книг
     */
    public List<Book> getBooksByGenre(Book.Genre genre) {
        return new ArrayList<>(genreSetMap.getOrDefault(genre, Collections.emptySet()));
    }

    /**
     * Возвращает список книг определенного автора
     * @param author автор
     * @return список книг
     */
    public List<Book> getBooksByAuthor(String author) {
        if (author == null || author.isBlank()) {
            return Collections.emptyList();
        }

        List<Book> books = authorsAndTheirBooks.get(author);
        if (books == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(books); // возвращаем копию, чтобы не дать изменить внутренний список
    }

    /**
     * Поиск книг по названию (частичное совпадение)
     * @param titlePart часть названия
     * @return список книг
     */
    public List<Book> searchBooksByTitle(String titlePart) {
        List<Book> result = new ArrayList<>();
        for(Book book : library.values()){
            if (book.getTitle() != null && book.getTitle().toLowerCase().contains(titlePart.toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Возвращает список доступных книг
     * @return список доступных книг
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : library.values()){
            if (book.isAvailable())
                result.add(book);
        }
        return result;
    }


    /**
     * Сортирует список книг по названию
     * @param books список книг для сортировки
     * @return отсортированный список
     */
    public List<Book> sortBooksByTitle(List<Book> books) {
        if (books == null) {
            return Collections.emptyList();
        }
        List<Book> sorted = new ArrayList<>(books);
        Collections.sort(sorted, new BookTitleComparator());
        return sorted;
    }

    /**
     * Сортирует список книг по году публикации (от новых к старым)
     * @param books список книг для сортировки
     * @return отсортированный список
     */
    public List<Book> sortBooksByPublicationYear(List<Book> books) {
        if(books == null){
            return Collections.emptyList();
        }
        List<Book> sorted = new ArrayList<>(books);
        Collections.sort(sorted, Comparator.comparing(Book::getPublicationYear).reversed());
        return sorted;
    }

    /**
     * Сортирует список книг по доступности (сначала доступные)
     * @param books список книг для сортировки
     * @return отсортированный список
     */
    public List<Book> sortBooksByAvailability(List<Book> books) {
        if (books == null) {
            return Collections.emptyList();
        }

        List<Book> sorted = new ArrayList<>(books);
        Collections.sort(sorted, new BookAvailabilityComparator());
        return sorted;
    }

    // ============ Методы для работы с читателями ============

    /**
     * Добавляет читателя
     * @param reader читатель
     * @return true если читатель добавлен, false если читатель с таким ID уже существует
     */
    public boolean addReader(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("reader cannot be null");
        }
        if (readers.containsKey(reader.getId())){
            return false;
        }
        readers.put(reader.getId(), reader);
        return true;
    }

    /**
     * Получает читателя по ID
     * @param readerId ID читателя
     * @return читатель или null, если читатель не найден
     */
    public Reader getReaderById(String readerId) {
        if (readerId == null || readerId.isBlank() ){
            throw new IllegalArgumentException("Reader's id cannot be null or blank");
        }
        return readers.get(readerId);
    }

    /**
     * Удаляет читателя
     * @param readerId ID читателя
     * @return true если читатель удален, false если читатель не найден
     */
    public boolean removeReader(String readerId) {
        if (readerId == null || readerId.isBlank()){
            throw new IllegalArgumentException("Reader's id cannot be null or blank");
        }
        if (readers.remove(readerId) == null) {
            return false;
        }
        return true;
    }

    /**
     * Возвращает список всех читателей
     * @return список читателей
     */
    public List<Reader> getAllReaders() {
        return new ArrayList<>(readers.values());
    }

    // ============ Методы для выдачи и возврата книг ============

    /**
     * Выдает книгу читателю
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @param borrowDays количество дней, на которое выдается книга
     * @return true если книга выдана, false если книга недоступна или не найдена
     */
    public boolean borrowBook(String isbn, String readerId, int borrowDays) {
        if (isbn == null || isbn.isBlank() || readerId == null || readerId.isBlank()) {
            throw new IllegalArgumentException("ISBN and Reader ID must not be null or blank");
        }
        Book book = library.get(isbn);
        Reader reader = readers.get(readerId);


        if (book == null || reader == null){
            return false;
        }

        for (Borrowing borrowing : borrowings){
            if (borrowing.getIsbn().equals(isbn) && !borrowing.isReturned())
                return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(borrowDays);

        // Создадим новый объект выдачи и добавим его в список Array list

        borrowings.add(new Borrowing(isbn, readerId, today, dueDate));
        book.setAvailable(false);

        return true;
    }

    /**
     * Возвращает книгу в библиотеку
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @return true если книга возвращена, false если запись о выдаче не найдена
     */
    public boolean returnBook(String isbn, String readerId) {
        if (isbn == null || isbn.isBlank() || readerId == null || readerId.isBlank()){
            throw new IllegalArgumentException("ISBN and Reader ID must not be null or blank");
        }

        Book book = library.get(isbn);
        Reader reader = readers.get(readerId);

        if (book == null || reader == null){
            return false;
        }
        for (Borrowing borowwing : borrowings){
            if (borowwing.getIsbn().equals(isbn) && borowwing.getReaderId().equals(readerId) && !borowwing.isReturned()){
                borowwing.returnBook(LocalDate.now());
                book.setAvailable(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Получает список всех выданных книг
     * @return список выдач
     */
    public List<Borrowing> getAllBorrowings() {
        return new ArrayList<>(borrowings);
    }

    /**
     * Получает список просроченных выдач
     * @return список просроченных выдач
     */
    public List<Borrowing> getOverdueBorrowings() {
        List<Borrowing> overDue = new ArrayList<>();
        for(Borrowing b : borrowings){
            if(b.isOverdue()){
                overDue.add(b);
            }
        }
        return overDue;


    }

    /**
     * Получает историю выдач для конкретного читателя
     * @param readerId ID читателя
     * @return список выдач
     */
    public List<Borrowing> getBorrowingsByReader(String readerId) {
        if (readerId == null || readerId.isBlank()){
            throw new IllegalArgumentException(" Reader ID must not be null or blank");
        }

        List<Borrowing>  borrowingsByReader = new ArrayList<>();
        for (Borrowing b : borrowings){
            if (b.getReaderId().equals(readerId)){
                borrowingsByReader.add(b);
            }
        }

        return borrowingsByReader;
    }

    /**
     * Получает историю выдач для конкретной книги
     * @param isbn ISBN книги
     * @return список выдач
     */
    public List<Borrowing> getBorrowingsByBook(String isbn) {
        if (isbn == null || isbn.isBlank()){
            throw new IllegalArgumentException("ISBN and Reader ID must not be null or blank");
        }
        List<Borrowing> borrowingsByBook = new ArrayList<>();
        for (Borrowing b : borrowings){
            if(isbn.equals(b.getIsbn())){
                borrowingsByBook.add(b);
            }
        }
        return borrowingsByBook;
    }

    /**
     * Продлевает срок выдачи книги
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @param additionalDays дополнительные дни
     * @return true если срок продлен, false если запись о выдаче не найдена
     */
    public boolean extendBorrowingPeriod(String isbn, String readerId, int additionalDays) {
        if(isbn == null || isbn.isBlank() || readerId == null || readerId.isBlank()|| additionalDays == 0){
            throw new IllegalArgumentException("ISBN and Reader ID must not be null or blank, additional days must be grater then zero");
        }
        Book book = library.get(isbn);
        Reader reader = readers.get(readerId);

        if (book == null || reader == null) {
            return false;
        }


        for (Borrowing b : borrowings) {
            if (b.getIsbn().equals(isbn) &&
                    b.getReaderId().equals(readerId) &&
                    !b.isReturned()) {

                LocalDate newDueDate = b.getDueDate().plusDays(additionalDays);
                b.setDueDate(newDueDate);
                return true;
            }
        }

        return false;
    }


    // ============ Методы для статистики и отчетов ============

    /**
     * Возвращает статистику по жанрам: количество книг в каждом жанре
     * @return карта "жанр -> количество книг"
     */
    public Map<Book.Genre, Integer> getGenreStatistics() {
        Map<Book.Genre, Integer> genreStats = new HashMap<>();

        for (Map.Entry<Book.Genre, Set<Book>> entry : genreSetMap.entrySet()) {
            Book.Genre genre = entry.getKey();
            int count = entry.getValue().size();
            genreStats.put(genre, count);
        }

        return genreStats;
    }

    /**
     * Возвращает наиболее популярные книги (по количеству выдач)
     * @param limit максимальное количество книг в результате
     * @return список пар "книга -> количество выдач"
     */
    public Map<Book, Integer> getMostPopularBooks(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than zero");
        }

        // Подсчёт количества выдач по ISBN
        Map<String, Integer> countByIsbn = new HashMap<>();
        for (Borrowing borrowing : borrowings) {
            String isbn = borrowing.getIsbn();
            countByIsbn.put(isbn, countByIsbn.getOrDefault(isbn, 0) + 1);
        }

        // Сортировка по убыванию количества выдач
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(countByIsbn.entrySet());
        Collections.sort(sortedEntries, new PopularBookComparator());

        // Построение итоговой мапы: Book -> количество выдач
        Map<Book, Integer> result = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            if (count >= limit) break;

            Book book = library.get(entry.getKey());
            if (book != null) {
                result.put(book, entry.getValue());
                count++;
            }
        }

        return result;
    }

    /**
     * Возвращает наиболее активных читателей (по количеству выдач)
     * @param limit максимальное количество читателей в результате
     * @return список пар "читатель -> количество выдач"
     */
    public Map<Reader, Integer> getMostActiveReaders(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than zero");
        }

        // Подсчёт количества выдач по readerId
        Map<String, Integer> countByReader = new HashMap<>();
        for (Borrowing b : borrowings) {
            String readerId = b.getReaderId();
            countByReader.put(readerId, countByReader.getOrDefault(readerId, 0) + 1);
        }

        // Сортировка по убыванию количества выдач
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(countByReader.entrySet());
        sortedEntries.sort((a, b) -> b.getValue() - a.getValue());

        // Формирование результата
        Map<Reader, Integer> result = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            if (count >= limit) break;
            Reader reader = readers.get(entry.getKey());
            if (reader != null) {
                result.put(reader, entry.getValue());
                count++;
            }
        }

        return result;

    }

    /**
     * Находит читателей, которые не возвращают книги вовремя
     * @return список читателей с просроченными книгами
     */
    public List<Reader> getReadersWithOverdueBooks() {
        Set<String> overdueReaderIds = new HashSet<>();

        for (Borrowing b : borrowings) {
            if (b.isOverdue()) {
                overdueReaderIds.add(b.getReaderId());
            }
        }

        List<Reader> result = new ArrayList<>();
        for (String readerId : overdueReaderIds) {
            Reader reader = readers.get(readerId);
            if (reader != null) {
                result.add(reader);
            }
        }

        return result;
    }

    // ============ Методы для работы с итераторами ============

    /**
     * Создает итератор для просмотра книг определенного жанра и года издания
     * @param genre жанр книг
     * @param year год издания
     * @return итератор
     */
    public Iterator<Book> getBooksByGenreAndYearIterator(Book.Genre genre, int year) {
        return new BooksByGenreAndYearIterator(genre, year);
    }

    /**
     * Создает итератор для просмотра книг с несколькими авторами
     * @param minAuthorsCount минимальное количество авторов
     * @return итератор
     */
    public Iterator<Book> getBooksWithMultipleAuthorsIterator(int minAuthorsCount) {
        return new BooksWithMultipleAuthorsIterator(minAuthorsCount);
    }

    /**
     * Создает итератор для просмотра просроченных выдач
     * @return итератор
     */
    public Iterator<Borrowing> getOverdueBorrowingsIterator() {
        return new OverdueBorrowingsIterator();
    }
}