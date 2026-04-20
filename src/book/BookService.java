package book;

import author.Author;
import author.AuthorInfoDTO;
import author.AuthorMapper;
import author.AuthorRepository;
import category.Category;
import category.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    BookRepository bookRepository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    CategoryRepository categoryRepository = new CategoryRepository();

    //metod som lägger till author, category och gör om till dto
    public List<BookInfoDTO> mapToDTO(List<Book> books) {
        List<BookInfoDTO> dtos = new ArrayList<>();
        for (Book b : books) {
            List<Author> authors = authorRepository.findAuthorsByBookId(b.getId());
            List<Category> categories = categoryRepository.findCategoriesByBookId(b.getId());
            BookInfoDTO bookInfoDTO = new BookInfoDTO(b.getId(), b.getTitle(), b.getYearPublished(), b.getAvailableCopies(), b.getSummary(), authors, categories);
            dtos.add(bookInfoDTO);
        }
        return dtos;
    }

    //gör om till dtos
    public List<BookInfoDTO> getAllBooks() {
        return mapToDTO(bookRepository.getAllBooks());
    }

    //filtrerar ut de som är available
    public List<BookInfoDTO> getAllAvailableBooks() {
        List<Book> books = bookRepository.getAllBooks();
        List<Book> availableBooks = new ArrayList<>();
        for (Book b : books) {
            if (b.getAvailableCopies() > 0) {
                availableBooks.add(b);
            }
        }
        return mapToDTO(availableBooks);
    }

    public List<BookInfoDTO> getPopularBooks() {
        return mapToDTO(bookRepository.getPopularBooks());
    }

    public List<BookInfoDTO> searchBook(String search) {
        return mapToDTO(bookRepository.searchBook(search));
    }

    public List<BookInfoDTO> filterBooksByCategory(int categoryId) {
        return mapToDTO(bookRepository.filterBooksByCategory(categoryId));
    }

    //kallar på sökfunktionen i authorrepository genom mapperklassen - används i addbook
    public List<AuthorInfoDTO> searchAuthor(String search) {
        return AuthorMapper.mapToDTO(authorRepository.searchAuthor(search));
    }

    public Author getAuthorById(int authorId) {
        return authorRepository.findAuthorById(authorId);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    public String addBook(NewBookDTO newBookDTO) {
        return bookRepository.addBook(newBookDTO);
    }

    public void editBook(int bookId, String column, String value) {
        bookRepository.editBook(bookId, column, value);
    }

    public void editBook(int bookId, String column, int value) {
        bookRepository.editBook(bookId, column, value);
    }

    public void editBookDesc(int bookId, String column, String value) {
        bookRepository.editBookDesc(bookId, column, value);
    }

    public void editBookDesc(int bookId, String column, int value) {
        bookRepository.editBookDesc(bookId, column, value);
    }

    public void addBookAuthors(int bookId, int authorId) {
        bookRepository.addBookAuthors(bookId, authorId);
    }

    //för att bookcontroller inte ska behöva kalla på authorservice, denna o nästa
    public List<AuthorInfoDTO> getAllAuthors() {
        return AuthorMapper.mapToDTO(authorRepository.getAllAuthors());
    }

    public List<AuthorInfoDTO> getAuthorsByBookId(int bookId) {
        return AuthorMapper.mapToDTO(authorRepository.findAuthorsByBookId(bookId));
    }

    public void removeBookAuthors(int bookId, int authorId){
        bookRepository.removeBookAuthors(bookId, authorId);
    }

    public List<Category> getCategoriesByBookId(int bookId) {
        return categoryRepository.findCategoriesByBookId(bookId);
    }

    public void removeBookCategories(int bookId, int categoryId) {
        bookRepository.removeBookCategories(bookId, categoryId);
    }

    //bara ett mellansteg
    public void deleteBook(int bookId) {
        bookRepository.deleteBook(bookId);
    }

    public String addCategoryToBook(int bookId, int categoryId) {
        return bookRepository.addCategoryToBook(bookId, categoryId);
    }

    public List<BookInfoDTO> getBookById(int bookId) {
        return mapToDTO(bookRepository.getBookById(bookId));
    }

}