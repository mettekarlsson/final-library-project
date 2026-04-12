package book;

import java.util.ArrayList;
import java.util.Scanner;

public class BookController {
    BookService bookService = new BookService();
    Scanner scanner = new Scanner(System.in);

    public void memberBookMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Book Menu ----");
            System.out.println("1. Show all books");
            System.out.println("2. Show all available books");
            System.out.println("3. Show top ten most popular books");
            System.out.println("4. Search book");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    showAllBooks();
                    break;
                }
                case 2: {
                    showAllAvailableBooks();
                    break;
                }

                case 3: {
                    showPopularBooks();
                    break;
                }

                case 4: {
                    searchBook();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }

        }
    }

    //case 1
    public void showAllBooks() {
    ArrayList<BookInfoDTO> books = new ArrayList<>(bookService.getAllBooks());
    for(BookInfoDTO b :books) {
        System.out.println(b);
    }
}

    //case 2
    public void showAllAvailableBooks() {
        ArrayList<BookInfoDTO> books = new ArrayList<>(bookService.getAllAvailableBooks());
        for (BookInfoDTO b : books) {
            System.out.println(b);
        }
    }

    //case 3
    public void showPopularBooks() {
        ArrayList<BookInfoDTO> books = new ArrayList<>(bookService.getPopularBooks());
        for (BookInfoDTO b : books) {
            System.out.println(b);
        }
    }

    //case 4
    public void searchBook() {
        System.out.println("Search for a book:");
        ArrayList<BookInfoDTO> books = bookService.searchBook(scanner.nextLine());
        for (BookInfoDTO b : books) {
            System.out.println(b);
        }
    }

    public void adminBookMenu() {
        boolean active = true;
        while (active) {
            System.out.println("1. Add book");
            System.out.println("2. Update book");
            System.out.println("3. Delete book");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    //addBook();
                    break;
                }
                case 2: {
                    //editBook();
                    break;
                }
                case 3: {
                    //deleteBook();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    //case 1
//    public void addBook() {
//        System.out.println("Skriv in titeln på boken:");
//        String title = scanner.nextLine();
//        System.out.println("Skriv in isbn för boken:");
//        String isbn = scanner.nextLine();
//        System.out.println("Skriv in vilket år boken publicerades:");
//        int yearPublished = Integer.parseInt(scanner.nextLine());
//        System.out.println("Skriv in totalt antal kopior av boken:");
//        int totalCopies = Integer.parseInt(scanner.nextLine());
//        System.out.println("Skriv in antal tillgängliga kopior av boken:");
//        int availableCopies = Integer.parseInt(scanner.nextLine());
//
//        Book book = new Book(title, isbn, yearPublished, totalCopies, availableCopies);
//
//        bookService.addBook(book);
//    }


}
