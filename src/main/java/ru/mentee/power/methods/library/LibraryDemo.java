package ru.mentee.power.methods.library;

public class LibraryDemo {
    public static void main(String[] args) {

        Library library = new Library(10);

        library.addBook(new Book("Война и мир", "Лев Толстой", 1869));
        library.addBook(new Book("1984", "Джордж Оруэлл", 1949));
        library.addBook(new Book("Преступление и наказание", "Федор Достоевский", 1866));
        System.out.println("Доступные книги:");
        for (Book book : library.listAvailableBooks()) {
            System.out.println(book);
        }
        library.checkoutBook("1984");
        System.out.println("\nДоступные книги после выдачи:");
        for (Book book : library.listAvailableBooks()) {
            System.out.println(book);
        }

        System.out.println("\nВыданные книги:");
        for (Book book : library.listCheckedOutBooks()) {
            System.out.println(book);
        }
        library.returnBook("1984");
        System.out.println("\nДоступные книги после возврата:");
        for (Book book : library.listAvailableBooks()) {
            System.out.println(book);
        }

        System.out.println("\nВыданные книги:");
        for (Book book : library.listCheckedOutBooks()) {
            System.out.println(book);
        }
    }
}