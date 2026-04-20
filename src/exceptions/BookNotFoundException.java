package exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(int bookId) {
        super("Book with ID #" + bookId + " not found.");
    }
}
