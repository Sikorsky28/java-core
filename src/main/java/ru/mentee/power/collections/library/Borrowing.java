package ru.mentee.power.collections.library;


import java.time.LocalDate;
import java.util.Objects;


public class Borrowing {
    private String isbn;
    private String readerId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Borrowing(String isbn, String readerId, LocalDate borrowDate, LocalDate dueDate){
        this.isbn = isbn;
        this.readerId = readerId;
        this.borrowDate = LocalDate.now();
        this.dueDate = dueDate;
    }


    public String getIsbn() {
        return isbn;
    }

    public String getReaderId() {
        return readerId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    public boolean isOverdue() {
        if(returnDate == null){
            LocalDate currentDate = LocalDate.now();
            return currentDate.isAfter(dueDate);
        }
        return false;
    }


    public boolean isReturned() {
        return returnDate != null;
    }

    public void returnBook(LocalDate returnDate) {
        if (returnDate == null){
            throw new IllegalArgumentException("Дата возврата не может быть null");
        }
        setReturnDate(returnDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)return true; // сравнение по ссылке
        if (o == null || getClass() != o.getClass()) return false;

        Borrowing borrowing = (Borrowing) o;

        return Objects.equals(isbn, borrowing.isbn) && Objects.equals(readerId, borrowing.readerId) && Objects.equals(borrowDate, borrowing.borrowDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, readerId, borrowDate);
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "isbn='" + isbn + '\'' +
                ", raeader id='" + readerId + '\'' +
                ", borrow date =" + borrowDate +
                '}';

    }
}