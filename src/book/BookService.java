package book;

import author.Author;
import author.AuthorInfoDTO;
import author.AuthorMapper;
import author.AuthorRepository;
import category.Category;
import category.CategoryRepository;
import exceptions.BookNotFoundException;
import exceptions.CategoryNotFoundException;

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
        List<Book> books = bookRepository.searchBook(search);
        List<BookInfoDTO> dtos = new ArrayList<>();
        for (Book b : books) {
            dtos.add(addInfoAndMap(b));
        }
        return dtos;
    }

    //case 5 - för att visa alla kategorier, och admin case 4
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    //case 5
    public List<BookInfoDTO> filterBooksByCategory(int categoryId) {
        return bookRepository.filterBooksByCategory(categoryId).stream()
                .map(b -> addInfoAndMap(b))
                .collect(Collectors.toList());
    }




    public Author getAuthorById(int authorId) {
        return authorRepository.findAuthorById(authorId);
    }

    public Category getCategoryById(int categoryId) {
        Category category = categoryRepository.getCategoryById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException(categoryId);
        }
        return category;
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
        List<Author> authors = authorRepository.getAllAuthors();
        List<AuthorInfoDTO> dtos = new ArrayList<>();
        for(Author a : authors) {
            dtos.add(AuthorMapper.mapToDTO(a));
        }
        return dtos;
    }

    public List<AuthorInfoDTO> getAuthorsByBookId(int bookId) {
        List<Author> authors = authorRepository.findAuthorsByBookId(bookId);
        List<AuthorInfoDTO> dtos = new ArrayList<>();
        for (Author a : authors) {
            dtos.add(AuthorMapper.mapToDTO(a));
        }
        return dtos;
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

    //admin case 3 - kontrollerar om boken fanns
    public String deleteBook(int bookId) {
        String result = bookRepository.deleteBook(bookId);
        if (result == null) {
            throw new BookNotFoundException(bookId);
        }
        return result;
    }

    //admin case 4 - kontrollerar om det lyckades...? vilket exception ska egentligen skickas med här?
    public String addCategoryToBook(int bookId, int categoryId) {
        String result = bookRepository.addCategoryToBook(bookId, categoryId);
        if (result == null) {
            throw new BookNotFoundException(bookId);
        }
        return result;
    }

    public BookInfoDTO getBookById(int bookId) {
        Book book = bookRepository.getBookById(bookId);
        return addInfoAndMap(book);
    }

}