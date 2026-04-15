package book;

import author.Author;
import author.AuthorInfoDTO;
import author.AuthorMapper;
import author.AuthorRepository;
import category.Category;
import category.CategoryRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookService {
    BookRepository bookRepository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    CategoryRepository categoryRepository = new CategoryRepository();
    AuthorMapper authorMapper = new AuthorMapper();

    //metod som lägger till author, category och gör om till dto
    public ArrayList<BookInfoDTO> mapToDTO(ArrayList<Book> books) {
        ArrayList<BookInfoDTO> dtos = new ArrayList<>();
        for (Book b : books) {
            ArrayList<Author> authors = authorRepository.findAuthorsByBookId(b.getId());
            ArrayList<Category> categories = categoryRepository.findCategoriesByBookId(b.getId());
            BookInfoDTO bookInfoDTO = new BookInfoDTO(b.getId(), b.getTitle(), b.getYearPublished(), b.getAvailableCopies(), b.getSummary(), authors, categories);
            dtos.add(bookInfoDTO);
        }
        return dtos;
    }


    public ArrayList<BookInfoDTO> getAllBooks() {
        return mapToDTO(bookRepository.getAllBooks());
    }

    //filtrerar ut de som är available
    public ArrayList<BookInfoDTO> getAllAvailableBooks() {
        ArrayList<Book> books = bookRepository.getAllBooks();
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book b : books) {
            if (b.getAvailableCopies() > 0) {
                availableBooks.add(b);
            }
        }
        return mapToDTO(availableBooks);
    }

    public ArrayList<BookInfoDTO> getPopularBooks() {
        return mapToDTO(bookRepository.getPopularBooks());
    }

    public ArrayList<BookInfoDTO> searchBook(String search) {
        return mapToDTO(bookRepository.searchBook(search));
    }

    public ArrayList<BookInfoDTO> filterBooksByCategory(int categoryId) {
        return mapToDTO(bookRepository.filterBooksByCategory(categoryId));
    }

    //kallar på sökfunktionen i authorrepository genom mapperklassen - används i addbook
    public ArrayList<AuthorInfoDTO> searchAuthor(String search) {
        return authorMapper.mapToDTO(authorRepository.searchAuthor(search));
    }

    public Author getAuthorById(int authorId) {
        return authorRepository.findAuthorById(authorId);
    }

    public ArrayList<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    public String addBook(NewBookDTO newBookDTO) throws SQLException {
        return bookRepository.addBook(newBookDTO);
    }

    public void editBook(int bookId, String column, String value) {
        bookRepository.editBook(bookId, column, value);
    }

    public void editBook(int bookId, String column, int value) {
        bookRepository.editBook(bookId, column, value);
    }

    //bara ett mellansteg
    public void deleteBook(int bookId) {
        bookRepository.deleteBook(bookId);
    }

}