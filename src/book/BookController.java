package book;
import author.Author;
import base.BaseController;
import category.Category;
import exceptions.LibraryException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookController extends BaseController {
    BookService bookService = new BookService();

    public void memberBookMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Book Menu ----");
            System.out.println("1. View all books");
            System.out.println("2. View all available books");
            System.out.println("3. View top ten most popular books");
            System.out.println("4. Search books");
            System.out.println("5. Filter books by category");
            System.out.println("0. Return");
            int choice = readInt();

            try {
                switch (choice) {
                    case 1: {
                        getAllBooks();
                        break;
                    }
                    case 2: {
                        getAllAvailableBooks();
                        break;
                    }

                    case 3: {
                        showPopularBooks();
                        break;
                    }

                    case 4: {
                        searchBooks();
                        break;
                    }

                    case 5: {
                        filterBooksByCategory();
                        break;
                    }
                    case 0: {
                        active = false;
                        break;
                    }
                }
            } catch (LibraryException e) {
                handleException(e);
            }

        }
    }

    //case 1
    public void getAllBooks() {
    List<BookInfoDTO> books = new ArrayList<>(bookService.getAllBooks());
    for(BookInfoDTO b :books) {
        System.out.println(b);
    }
}

    //case 2
    public void getAllAvailableBooks() {
        List<BookInfoDTO> books = new ArrayList<>(bookService.getAllAvailableBooks());
            for (BookInfoDTO b : books) {
                System.out.println(b);
            }
    }

    //case 3
    public void showPopularBooks() {
        List<BookInfoDTO> books = new ArrayList<>(bookService.getPopularBooks());
        for (BookInfoDTO b : books) {
            System.out.println(b);
        }
    }

    //case 4
    public void searchBooks() {
        System.out.println("Search books:");
        List<BookInfoDTO> books = bookService.searchBooks(scanner.nextLine());
        if(books.isEmpty()) {
            System.out.println("No matching books.");
        } else {
            for (BookInfoDTO b : books) {
                System.out.println(b);
            }
        }
    }

    //case 5
    public void filterBooksByCategory() {
        System.out.println("--- All categories ---");
        List<Category> categories = bookService.getAllCategories();
        for (Category c : categories) {
            System.out.println(c);
        }
        System.out.println("Choose which category(id) to look at:");
        int categoryId = readInt();
        List<BookInfoDTO> books = bookService.filterBooksByCategory(categoryId);
        for (BookInfoDTO b : books) {
            System.out.println(b);
        }
    }

    public void adminBookMenu() {
        boolean active = true;
        while (active) {
            System.out.println("---- Book Menu ----");
            System.out.println("1. Add book");
            System.out.println("2. Update book");
            System.out.println("3. Delete book");
            System.out.println("4. Add category to book");
            System.out.println("0. Return");
            int choice = readInt();

            try {
                switch (choice) {
                    case 1: {
                        addBook();
                        break;
                    }
                    case 2: {
                        editBook();
                        break;
                    }
                    case 3: {
                        deleteBook();
                        break;
                    }
                    case 4: {
                        addCategoryToBook();
                        break;
                    }
                    case 0: {
                        active = false;
                        break;
                    }
                }
            } catch (LibraryException e) {
                handleException(e);
            }
        }
    }

    //case 1 admin
    public void addBook() {
        List<Author> bookAuthors = new ArrayList<>();
        List<Category> bookCategories = new ArrayList<>();

        System.out.println("Title:");
        String title = scanner.nextLine();
        while (title.isBlank()) {
            System.out.println("Title cannot be empty. Try again.");
            title = scanner.nextLine();
        }
        System.out.println("ISBN:");
        String isbn = scanner.nextLine();
        while (isbn.isBlank()) {
            System.out.println("ISBN cannot be empty. Try again.");
            isbn = scanner.nextLine();
        }
        System.out.println("Publishing year:");
        int yearPublished = readInt();
        System.out.println("Total number of copies:");
        int totalCopies = readInt();
        System.out.println("Available number of copies:");
        int availableCopies = readInt();
        System.out.println("Summary:");
        String summary = scanner.nextLine();
        System.out.println("Language:");
        String language = scanner.nextLine();
        System.out.println("Page count:");
        int pageCount = readInt();


        boolean addAuthor = true;
        while (addAuthor) {
            System.out.println("1. Choose existing author");
            System.out.println("2. Add new author");

            int choice = readInt();
            switch (choice) {
                case 1: {
                    System.out.println("Enter the author-id:");
                    int authorId = readInt();
                    Author author = bookService.getAuthorById(authorId);
                    bookAuthors.add(author);
                    break;
                }
                case 2: {
                    System.out.println("First name:");
                    String firstName = scanner.nextLine();
                    while (firstName.isBlank()) {
                        System.out.println("First name cannot be empty. Try again.");
                        firstName = scanner.nextLine();
                    }
                    System.out.println("Last name:");
                    String lastName = scanner.nextLine();
                    while (lastName.isBlank()) {
                        System.out.println("Last name cannot be empty. Try again.");
                        lastName = scanner.nextLine();
                    }
                    System.out.println("Nationality:");
                    String nationality = scanner.nextLine();
                    System.out.println("Birth date:");
                    LocalDate birthDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Biography:");
                    String biography = scanner.nextLine();
                    System.out.println("Website:");
                    String website = scanner.nextLine();
                    Author author = new Author(0, firstName, lastName, nationality, birthDate, biography, website);
                    bookAuthors.add(author);
                    break;
                }
            }
            System.out.println("Do you want to add another author? Y/N");
            String again = scanner.nextLine();
            if (!again.equalsIgnoreCase("Y")) {
                addAuthor = false;
            }

        }
        System.out.println("--- All categories ---");
        List<Category> categories = bookService.getAllCategories();
        for (Category c : categories) {
            System.out.println(c);
        }
        boolean addCategory = true;
        while (addCategory) {
            System.out.println("Enter the category-id:");
            int categoryId = readInt();

            Category category = bookService.getCategoryById(categoryId);
            bookCategories.add(category);


            System.out.println("Add another category? (Y/N)");
            String again = scanner.nextLine();
            if (!again.equalsIgnoreCase("Y")) {
                addCategory = false;
            }
        }

        NewBookDTO newBookDTO = new NewBookDTO(title, isbn, yearPublished, totalCopies, availableCopies,
                summary, language, pageCount, bookAuthors, bookCategories);
        String result = bookService.addBook(newBookDTO);
        System.out.println(result);
    }


    //case 2 admin
    public void editBook() {
        System.out.println("Enter the book ID:");
        int bookId = readInt();
        BookInfoDTO bookInfoDTO = bookService.getBookById(bookId);
            System.out.println(bookInfoDTO.toString());

        boolean active = true;

        while (active) {
            System.out.println("Choose what to update");
            System.out.println("1. Title");
            System.out.println("2. ISBN");
            System.out.println("3. Year Published");
            System.out.println("4. Total copies");
            System.out.println("5. Available copies");
            System.out.println("6. Summary");
            System.out.println("7. Language");
            System.out.println("8. Page count");
            System.out.println("9. Authors");
            System.out.println("10. Categories");
            System.out.println("0. Return");

            int choice = readInt();

            switch (choice) {
                case 1: {
                    System.out.println("New title:");
                    String bookTitle = scanner.nextLine();
                    while (bookTitle.isBlank()) {
                        System.out.println("Title cannot be empty. Try again.");
                        bookTitle = scanner.nextLine();
                    }
                    bookService.editBook(bookId, "title", bookTitle);
                    break;
                }
                case 2: {
                    System.out.println("New ISBN:");
                    String isbn = scanner.nextLine();
                    while (isbn.isBlank()) {
                        System.out.println("ISBN cannot be empty. Try again.");
                        isbn = scanner.nextLine();
                    }
                    bookService.editBook(bookId, "isbn", isbn);
                    break;
                }
                case 3: {
                    System.out.println("New publishing year:");
                    int yearPublished = readInt();
                    bookService.editBook(bookId, "year_published", yearPublished);
                    break;
                }
                case 4: {
                    System.out.println("New number of total copies:");
                    int totalCopies = readInt();
                    bookService.editBook(bookId, "total_copies", totalCopies);
                    break;
                }
                case 5: {
                    System.out.println("New number of available copies:");
                    int availableCopies = readInt();
                    bookService.editBook(bookId, "available_copies", availableCopies);
                    break;
                }
                case 6: {
                    System.out.println("New summary:");
                    String summary = scanner.nextLine();
                    bookService.editBookDesc(bookId, "summary", summary);
                    break;
                }
                case 7: {
                    System.out.println("New language:");
                    String language = scanner.nextLine();
                    bookService.editBookDesc(bookId, "language", language);
                    break;
                }
                case 8: {
                    System.out.println("New page count:");
                    int pageCount = readInt();
                    bookService.editBookDesc(bookId, "page_count", pageCount);
                    break;
                }
                case 9: {
                    System.out.println("1. Add author");
                    System.out.println("2. Remove author");
                    int authorChoice = readInt();
                    if (authorChoice == 1) {
                        System.out.println("Which author(id) would you like to add?");
                        int authorId = readInt();
                        System.out.println(bookService.addBookAuthors(bookId, authorId));
                    } else if (authorChoice == 2) {
                        System.out.println("Which author(id) would you like to remove?");
                        int authorId = readInt();
                        System.out.println(bookService.removeBookAuthors(bookId, authorId));
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                }
                case 10: {
                    System.out.println("1. Add category");
                    System.out.println("2. Remove category");
                    int categoryChoice = readInt();
                    if (categoryChoice == 1) {
                        System.out.println("Which category(id) would you like to add?");
                        int categoryId = readInt();
                        System.out.println(bookService.addCategoryToBook(bookId, categoryId));
                    } else if (categoryChoice == 2) {
                        System.out.println("Which category(id) would you like to remove?");
                        int categoryId = readInt();
                        System.out.println(bookService.removeBookCategories(bookId, categoryId));
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    //case 3 admin
    public void deleteBook() {
        System.out.println("Enter the book ID:");
        int bookId = readInt();
        String result = bookService.deleteBook(bookId);
        System.out.println(result);
    }

    //case 4 admin
    public void addCategoryToBook() {
        System.out.println("Enter the book-id:");
        int bookId = readInt();
        BookInfoDTO bookInfoDTO = bookService.getBookById(bookId);
        System.out.println(bookInfoDTO.toString());
        //denna metoden finns i bookservice oavsett, för att en låntagare ska kunna filtrera efter kategorier
        List<Category> categories = bookService.getAllCategories();
        for (Category c : categories) {
            System.out.println(c);
        }
        System.out.println("Enter the category-id:");
        int categoryId = readInt();
        String result = bookService.addCategoryToBook(bookId, categoryId);
        System.out.println(result);
    }
}


