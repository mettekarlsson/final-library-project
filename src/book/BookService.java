package book;

import author.Author;
import author.AuthorRepository;
import category.Category;
import category.CategoryRepository;

import java.util.ArrayList;

public class BookService {
    BookRepository bookRepository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    CategoryRepository categoryRepository = new CategoryRepository();

    //lägger till author och category info. gör sen om bok-listan till dtos
    public ArrayList<BookInfoDTO> getAllBooks() {
        ArrayList<BookInfoDTO> dtos = new ArrayList<>();
        ArrayList<Book> books = bookRepository.getAllBooks();
        for (Book b : books) {
            ArrayList<Author> authors = authorRepository.findAuthorsByBookId(b.getId());
            ArrayList<Category> categories = categoryRepository.findCategoriesByBookId(b.getId());
            BookInfoDTO bookInfoDTO = new BookInfoDTO(b.getId(), b.getTitle(), b.getYearPublished(), b.getAvailableCopies(), b.getSummary(), authors, categories);
            dtos.add(bookInfoDTO);
        }
        return dtos;
    }

    //samma som ovan samt filtrerar ut de som är available
    public ArrayList<BookInfoDTO> getAllAvailableBooks() {
        ArrayList<BookInfoDTO> dtos = new ArrayList<>();
        ArrayList<Book> books = bookRepository.getAllBooks();
        for (Book b : books) {
            if (b.getAvailableCopies() > 0) {
                ArrayList<Author> authors = authorRepository.findAuthorsByBookId(b.getId());
                ArrayList<Category> categories = categoryRepository.findCategoriesByBookId(b.getId());
                BookInfoDTO bookInfoDTO = new BookInfoDTO(b.getId(), b.getTitle(), b.getYearPublished(), b.getAvailableCopies(), b.getSummary(), authors, categories);
                dtos.add(bookInfoDTO);
            }
        }
        return dtos;
    }

    //lägger till author och category samt gör om till dtos.
    public ArrayList<BookInfoDTO> getPopularBooks() {
        ArrayList<BookInfoDTO> dtos = new ArrayList<>();
        ArrayList<Book> books = bookRepository.getPopularBooks();
        for (Book b : books) {
            ArrayList<Author> authors = authorRepository.findAuthorsByBookId(b.getId());
            ArrayList<Category> categories = categoryRepository.findCategoriesByBookId(b.getId());
            BookInfoDTO bookInfoDTO = new BookInfoDTO(b.getId(), b.getTitle(), b.getYearPublished(), b.getAvailableCopies(), b.getSummary(), authors, categories);
            dtos.add(bookInfoDTO);
        }
        return dtos;
    }

    //lägger till author och category samt gör om till dtos.
    public ArrayList<BookInfoDTO> searchBook(String search) {
        ArrayList<BookInfoDTO> dtos = new ArrayList<>();
        ArrayList<Book> books = bookRepository.searchBook(search);
        for (Book b : books) {
            ArrayList<Author> authors = authorRepository.findAuthorsByBookId(b.getId());
            ArrayList<Category> categories = categoryRepository.findCategoriesByBookId(b.getId());
            BookInfoDTO bookInfoDTO = new BookInfoDTO(b.getId(), b.getTitle(), b.getYearPublished(), b.getAvailableCopies(), b.getSummary(), authors, categories);
            dtos.add(bookInfoDTO);
        }
        return dtos;
    }

}