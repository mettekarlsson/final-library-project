package book;

import author.Author;
import author.AuthorRepository;
import category.Category;
import category.CategoryRepository;
import exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    BookRepository bookRepository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    CategoryRepository categoryRepository = new CategoryRepository();

    //lägger till authors och categories info och gör om till dtos via bookmapper
    private BookInfoDTO addInfoAndMap(Book book) {
        List<Author> authors = authorRepository.findAuthorsByBookId(book.getId());
        List<Category> categories = categoryRepository.findCategoriesByBookId(book.getId());
        return BookMapper.mapToDTO(book, authors, categories);
    }

    //case 1
    public List<BookInfoDTO> getAllBooks() {
        List<Book> books = bookRepository.getAllBooks();
        List<BookInfoDTO> dtos = new ArrayList<>();
        for (Book b : books) {
            dtos.add(addInfoAndMap(b));
        }
        return dtos;
    }

    //case 2 - filtrerar ut de som är available
    public List<BookInfoDTO> getAllAvailableBooks() {
        return bookRepository.getAllBooks().stream()
                .filter(b -> b.getAvailableCopies() > 0)
                .map(b -> addInfoAndMap(b))
                .collect(Collectors.toList());
    }

    //case 3
    public List<BookInfoDTO> getPopularBooks() {
        return bookRepository.getPopularBooks().stream()
                .map(b -> addInfoAndMap(b))
                .collect(Collectors.toList());
    }

    //case 4
    public List<BookInfoDTO> searchBook(String search) {
        return bookRepository.searchBook(search).stream()
                .map(b -> addInfoAndMap(b))
                .toList();
    }

    //case 5 - för att visa alla kategorier, och admin case 1, 4
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    //case 5
    public List<BookInfoDTO> filterBooksByCategory(int categoryId) {
        return bookRepository.filterBooksByCategory(categoryId).stream()
                .map(b -> addInfoAndMap(b))
                .collect(Collectors.toList());
    }

    //admin case 1
    public Author getAuthorById(int authorId) {
        Author author = authorRepository.findAuthorById(authorId);
        if (author == null) {
            throw new AuthorNotFoundException(authorId);
        }
        return author;
    }

    //admin case 1
    public Category getCategoryById(int categoryId) {
        Category category = categoryRepository.getCategoryById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException(categoryId);
        }
        return category;
    }

    //admin case 1
    public String addBook(NewBookDTO newBookDTO) {
        if (newBookDTO.getTotalCopies() < 1) {
            throw new ValidationException("Total copies cannot be less than 1.");
        }
        if (newBookDTO.getAvailableCopies() > newBookDTO.getTotalCopies()) {
            throw new ValidationException("Available copies cannot be more than total copies.");
        }
        if (newBookDTO.getAuthors().isEmpty()) {
            throw new ValidationException("Author list cannot be empty.");
        }
        return bookRepository.addBook(newBookDTO);
    }

    //admin case 2
    public BookInfoDTO getBookById(int bookId) {
        Book book = bookRepository.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException(bookId);
        }
        return addInfoAndMap(book);
    }

    //admin case 2
    public void editBook(int bookId, String column, String value) {
        bookRepository.editBook(bookId, column, value);
    }

    //admin case 2
    public void editBook(int bookId, String column, int value) {
        bookRepository.editBook(bookId, column, value);
    }

    //admin case 2
    public void editBookDesc(int bookId, String column, String value) {
        bookRepository.editBookDesc(bookId, column, value);
    }

    //admin case 2
    public void editBookDesc(int bookId, String column, int value) {
        bookRepository.editBookDesc(bookId, column, value);
    }

    //admin case 2
    public String addBookAuthors(int bookId, int authorId) {
        if (bookRepository.existsBookAuthors(bookId, authorId)) {
            throw new OperationFailedException("Author is already linked to this book.");
        }
        return bookRepository.addBookAuthors(bookId, authorId);
    }

    //admin case 2
    public String removeBookAuthors(int bookId, int authorId){
        return bookRepository.removeBookAuthors(bookId, authorId);
    }

    //admin case 2
    public String removeBookCategories(int bookId, int categoryId) {
        return bookRepository.removeBookCategories(bookId, categoryId);
    }

    //admin case 3 - kontrollerar om boken fanns
    public String deleteBook(int bookId) {
        String result = bookRepository.deleteBook(bookId);
        if (result == null) {
            throw new BookNotFoundException(bookId);
        }
        return result;
    }

    //admin case 2, 4
    public String addCategoryToBook(int bookId, int categoryId) {
        if (bookRepository.existsBookCategories(bookId, categoryId)) {
            throw new OperationFailedException("Category is already linked to this book.");
        }
        String result = bookRepository.addCategoryToBook(bookId, categoryId);
        if (result == null) {
            throw new OperationFailedException("Failed to add category to book.");
        }
        return result;
    }

}