package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;


public class BookTitleComparator implements Comparator<Book> {

    private static final Comparator<String> TITLE_COMPARATOR =
            Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER); // или nullsLast(...)

    @Override
    public int compare(Book b1, Book b2) {
        return TITLE_COMPARATOR.compare(b1.getTitle(), b2.getTitle());
    }
}
