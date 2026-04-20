package author;

import java.util.List;

public class AuthorService {
    AuthorRepository authorRepository = new AuthorRepository();

    public List<Author> getAllAuthors() {
        return authorRepository.getAllAuthors();
    }

    public String editAuthor(int authorId, String column, String value) {
        return authorRepository.editAuthor(authorId, column, value);
    }

    public String editAuthorDesc(int authorId, String column, String value) {
        return authorRepository.editAuthorDesc(authorId, column, value);
    }

    public String addAuthor(NewAuthorDTO newAuthorDTO) {
        return authorRepository.addAuthor(newAuthorDTO);
    }
}
