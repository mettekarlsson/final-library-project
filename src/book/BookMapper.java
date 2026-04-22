package book;

import author.Author;
import category.Category;
import java.util.List;

public class BookMapper {

    //metod som gör om till dto
    public static BookInfoDTO mapToDTO(Book book, List<Author> authors, List<Category> categories) {
        return new BookInfoDTO(
                book.getId(),
                book.getTitle(),
                book.getYearPublished(),
                book.getAvailableCopies(),
                book.getSummary(),
                authors,
                categories
        );
    }
}
