package author;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    //metod som gör om authors till dtos
    public List<AuthorInfoDTO> mapToDTO(List<Author> authors) {
        return authors.stream()
                .map(a -> new AuthorInfoDTO(
                        a.getId(),
                        a.getFirstName(),
                        a.getLastName(),
                        a.getNationality(),
                        a.getBiography()))
                .collect(Collectors.toList());
    }
}
