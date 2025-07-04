package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class BookPublicationYearComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return Integer.compare(b2.getPublicationYear(), b1.getPublicationYear());
    }
}