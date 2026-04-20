package author;

import exceptions.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorService {
    AuthorRepository authorRepository = new AuthorRepository();

    public List<AuthorInfoDTO> getAllAuthors() {
        return authorRepository.getAllAuthors().stream()
                .map(AuthorMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public AuthorInfoDTO findAuthorById(int authorId) {
        return AuthorMapper.mapToDTO(authorRepository.findAuthorById(authorId));
    }

    public String editAuthor(int authorId, String column, String value) {
        return authorRepository.editAuthor(authorId, column, value);
    }

    public String editAuthorDesc(int authorId, String column, String value) {
        return authorRepository.editAuthorDesc(authorId, column, value);
    }

    public String addAuthor(NewAuthorDTO newAuthorDTO) {
        if (newAuthorDTO.getFirstName() == null || newAuthorDTO.getFirstName().isBlank()) {
            throw new ValidationException("First name cannot be empty.");
        }
        if (newAuthorDTO.getLastName() == null || newAuthorDTO.getLastName().isBlank()) {
            throw new ValidationException("Last name cannot be empty.");
        }
        return authorRepository.addAuthor(newAuthorDTO);
    }
}
