package author;
import java.util.ArrayList;

public class AuthorMapper {

    //metod som gör om authors till dtos
    public ArrayList<AuthorInfoDTO> mapToDTO(ArrayList<Author> authors) {
        ArrayList<AuthorInfoDTO> dtos = new ArrayList<>();
        for (Author a : authors) {
           AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO(a.getId(), a.getFirstName(), a.getLastName(), a.getNationality(), a.getBiography());
            dtos.add(authorInfoDTO);
        }
        return dtos;
    }
}
