package exceptions;

public class AuthorNotFoundException extends AuthorException {
    public AuthorNotFoundException(int authorId) {
        super("Author with id #" + authorId + " not found");
    }
}
