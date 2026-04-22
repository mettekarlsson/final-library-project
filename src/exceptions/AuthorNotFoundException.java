package exceptions;

public class AuthorNotFoundException extends LibraryException {
    public AuthorNotFoundException(int authorId) {
        super("Author with id #" + authorId + " not found");
    }
}
