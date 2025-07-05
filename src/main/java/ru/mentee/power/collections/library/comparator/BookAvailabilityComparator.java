package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class BookAvailabilityComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return Boolean.compare(!b1.isAvailable(), !b2.isAvailable());
    }
}