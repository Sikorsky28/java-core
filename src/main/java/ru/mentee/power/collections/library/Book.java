package ru.mentee.power.collections.library;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book {
    private String isbn;
    private String title;
    private Set<String> authors;
    private Genre genre;
    private int publicationYear;
    private int pageCount;
    private boolean available;

    public enum Genre {
        FICTION, NON_FICTION, SCIENCE, HISTORY, FANTASY, DETECTIVE, ROMANCE, BIOGRAPHY, CHILDREN
    }

    public Book(String isbn, String title, int publicationYear, Genre genre) {
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.authors = new HashSet<>();
        this.available = true;
    }
    // Геттеры/Сеттеры
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    public void addAuthor(String author) {
        if(author == null || author.isBlank()){
            throw new IllegalArgumentException("Author cannot be null or blank");
        }
            authors.add(author.trim());
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)return true;
        if(o == null || getClass() != o.getClass())return false;

        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {

        return "Book{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", year=" + publicationYear +
                '}';

    }
}