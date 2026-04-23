package book;

import author.Author;
import category.Category;

import java.util.List;

public interface BookServiceInterface {
    List<BookInfoDTO> getAllBooks();
    List<BookInfoDTO> getAllAvailableBooks();
    List<BookInfoDTO> getPopularBooks();
    List<BookInfoDTO> searchBooks(String search);
    List<Category> getAllCategories();
    List<BookInfoDTO> filterBooksByCategory(int categoryId);
    Author getAuthorById(int authorId);
    Category getCategoryById(int categoryId);
    String addBook(NewBookDTO newBookDTO);
    BookInfoDTO getBookById(int bookId);
    void editBook(int bookId, String column, String value);
    void editBook(int bookId, String column, int value);
    void editBookDesc(int bookId, String column, String value);
    void editBookDesc(int bookId, String column, int value);
    String addBookAuthors(int bookId, int authorId);
    String removeBookAuthors(int bookId, int authorId);
    String removeBookCategories(int bookId, int categoryId);
    String deleteBook(int bookId);
    String addCategoryToBook(int bookId, int categoryId);
}
