package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class GenreAndTitleComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        int genreCompare = b1.getGenre().name().compareToIgnoreCase(b2.getGenre().name());
        if (genreCompare != 0) {
            return genreCompare;
        }
        return b1.getTitle().compareToIgnoreCase(b2.getTitle());
    }
}