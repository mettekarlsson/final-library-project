package author;

public class AuthorMapper {

    //metod som gör om authors till dtos
    public static AuthorInfoDTO mapToDTO(Author author) {
        return new AuthorInfoDTO(author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getNationality(),
                author.getBiography());
    }
}
