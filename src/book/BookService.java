package book;

import java.util.ArrayList;

public class BookService {
    BookRepository bookRepository = new BookRepository();

    //lägger till author och category info. gör sen om bok-listan till dtos
    public ArrayList<BookInfoDTO> getAllBooks() {
        ArrayList<BookInfoDTO> dtos = new ArrayList<>();
        ArrayList<Book> books = bookRepository.getAllBooks();
        for (Book b : books) {

        }
    }

}