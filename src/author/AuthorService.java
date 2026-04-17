package author;

import java.util.ArrayList;

public class AuthorService {
    AuthorRepository authorRepository = new AuthorRepository();

    public ArrayList<Author> getAllAuthors() {
        return authorRepository.getAllAuthors();
    }

    public String editAuthor(int authorId, String column, String value) {
        return authorRepository.editAuthor(authorId, column, value);
    }

    public String editAuthorDesc(int authorId, String column, String value) {
        return authorRepository.editAuthorDesc(authorId, column, value);
    }
}
