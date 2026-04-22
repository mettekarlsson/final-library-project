package exceptions;

public class BookNotFoundException extends LibraryException {
    public BookNotFoundException(int bookId) {
        super("Book with ID #" + bookId + " not found.");
    }
}
