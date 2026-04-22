package exceptions;

public class CategoryNotFoundException extends LibraryException {
    public CategoryNotFoundException(int categoryId) {
        super("Category with id #" + categoryId + " not found");
    }
}
