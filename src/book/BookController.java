package book;
import author.Author;
import author.AuthorInfoDTO;
import category.Category;

import java.sql.SQLException;
import java.time.LocalDate;
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
            System.out.println("5. Filter books by category");
            System.out.println("6. Leave a review");
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

                case 5: {
                    filterBooksByCategory();
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

    //case 5
    public void filterBooksByCategory() {
        System.out.println("--- All categories ---");
        ArrayList<Category> categories = bookService.getAllCategories();
        for (Category c : categories) {
            System.out.println(c);
        }
        System.out.println("Choose which category(id) to look at:");
        int categoryId = Integer.parseInt(scanner.nextLine());
        ArrayList<BookInfoDTO> books = bookService.filterBooksByCategory(categoryId);
        for (BookInfoDTO b : books) {
            System.out.println(b);
        }
    }

    public void adminBookMenu() throws SQLException {
        boolean active = true;
        while (active) {
            System.out.println("---- Book Menu ----");
            System.out.println("1. Add book");
            System.out.println("2. Update book");
            System.out.println("3. Delete book");
            System.out.println("4. Add category to book");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

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
        }
    }

    //case 1 admin
    public void addBook() throws SQLException {
        ArrayList<Author> bookAuthors = new ArrayList<>();
        ArrayList<Category> bookCategories = new ArrayList<>();

        System.out.println("Enter the book-title:");
        String title = scanner.nextLine();
        System.out.println("Enter the ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Enter the publishing year:");
        int yearPublished = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the total number of copies:");
        int totalCopies = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the available number of copies:");
        int availableCopies = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the summary:");
        String summary = scanner.nextLine();
        System.out.println("Enter the language:");
        String language = scanner.nextLine();
        System.out.println("Enter the page count:");
        int pageCount = Integer.parseInt(scanner.nextLine());


        boolean addAuthor = true;
        while (addAuthor) {
            System.out.println("1. Choose existing author");
            System.out.println("2. Add new author");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: {
                    System.out.println("Search for an author:");
                    String authorSearch = scanner.nextLine();
                    ArrayList<AuthorInfoDTO> authors = bookService.searchAuthor(authorSearch);
                    for (AuthorInfoDTO a : authors) {
                        System.out.println(a);
                    }
                    System.out.println("Enter the author-id:");
                    int authorId = Integer.parseInt(scanner.nextLine());

                    Author author = bookService.getAuthorById(authorId);
                    bookAuthors.add(author);
                    break;
                }
                case 2: {
                    System.out.println("Enter their first name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter their last name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter their nationality:");
                    String nationality = scanner.nextLine();
                    System.out.println("Enter their birth date:");
                    LocalDate birthDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter their biography:");
                    String biography = scanner.nextLine();
                    System.out.println("Enter their website:");
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
        System.out.println("See all categories:");
        ArrayList<Category> categories = bookService.getAllCategories();
        for (Category c : categories) {
            System.out.println(c);
        }
        boolean addCategory = true;
        while (addCategory) {
            System.out.println("Enter the category-id:");
            int categoryId = Integer.parseInt(scanner.nextLine());

            Category category = bookService.getCategoryById(categoryId);
            if (category != null) {
                bookCategories.add(category);
            } else {
                System.out.println("Category not found: " + categoryId);
            }

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
        ArrayList<Author> bookAuthors = new ArrayList<>();
        System.out.println("Enter the book ID:");
        int bookId = Integer.parseInt(scanner.nextLine());
        ArrayList<BookInfoDTO> book = bookService.getBookById(bookId);
        for (BookInfoDTO b : book) {
            System.out.println(b);
            bookAuthors = b.getAuthors();
        }

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

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    System.out.println("Enter the new title:");
                    String bookTitle = scanner.nextLine();
                    bookService.editBook(bookId, "title", bookTitle);
                    break;
                }
                case 2: {
                    System.out.println("Enter the new ISBN:");
                    String isbn = scanner.nextLine();
                    bookService.editBook(bookId, "isbn", isbn);
                    break;
                }
                case 3: {
                    System.out.println("Enter the new publishing year:");
                    int yearPublished = Integer.parseInt(scanner.nextLine());
                    bookService.editBook(bookId, "year_published", yearPublished);
                    break;
                }
                case 4: {
                    System.out.println("Enter the new number of total copies:");
                    int totalCopies = Integer.parseInt(scanner.nextLine());
                    bookService.editBook(bookId, "total_copies", totalCopies);
                    break;
                }
                case 5: {
                    System.out.println("Enter the new number of available copies:");
                    int availableCopies = Integer.parseInt(scanner.nextLine());
                    bookService.editBook(bookId, "available_copies", availableCopies);
                    break;
                }
                case 6: {
                    System.out.println("Enter the new summary:");
                    String summary = scanner.nextLine();
                    bookService.editBookDesc(bookId, "summary", summary);
                    break;
                }
                case 7: {
                    System.out.println("Enter the new language:");
                    String language = scanner.nextLine();
                    bookService.editBookDesc(bookId, "language", language);
                    break;
                }
                case 8: {
                    System.out.println("Enter the new page count:");
                    int pageCount = Integer.parseInt(scanner.nextLine());
                    bookService.editBookDesc(bookId, "page_count", pageCount);
                    break;
                }
                case 9: {
                    for (Author a : bookAuthors) {
                        System.out.println(a);
                    }
                    System.out.println("1. Add author");
                    System.out.println("2. Remove author");
                    int authorChoice = Integer.parseInt(scanner.nextLine());
                    if (authorChoice == 1) {
                        ArrayList<AuthorInfoDTO> authors = bookService.getAllAuthors();
                        for (AuthorInfoDTO a : authors) {
                            System.out.println(a);
                        }
                        System.out.println("Which author(id) would you like to add?");
                        int authorId = Integer.parseInt(scanner.nextLine());
                        bookService.addBookAuthors(bookId, authorId);
                    } else if (authorChoice == 2) {
                        ArrayList<AuthorInfoDTO> currentAuthors = bookService.getAuthorsByBookId(bookId);
                        for (AuthorInfoDTO a : currentAuthors) {
                            System.out.println(a);
                        }
                        System.out.println("Which author(id) would you like to remove?");
                        int authorId = Integer.parseInt(scanner.nextLine());
                        bookService.removeBookAuthors(bookId, authorId);
                    } else {
                        System.out.println("Invalid choice.");
                    }
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
        int bookId = Integer.parseInt(scanner.nextLine());
        bookService.deleteBook(bookId);

        System.out.println("Book #" + bookId + " has been deleted.");
    }

    //case 4 admin
    public void addCategoryToBook() {
        System.out.println("Enter the book-id:");
        int bookId = Integer.parseInt(scanner.nextLine());
        ArrayList<BookInfoDTO> books = bookService.getBookById(bookId);
        for (BookInfoDTO b : books) {
            System.out.println(b);
        }
        ArrayList<Category> categories = bookService.getAllCategories();
        for (Category c : categories) {
            System.out.println(c);
        }
        System.out.println("Enter the category-id:");
        int categoryId = Integer.parseInt(scanner.nextLine());
        String result = bookService.addCategoryToBook(bookId, categoryId);
        System.out.println(result);
    }
}


