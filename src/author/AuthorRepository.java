package author;

import java.sql.*;
import java.util.ArrayList;

public class AuthorRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    public ArrayList<Author> findAuthorsByBookId(int bookId) {
        ArrayList<Author> authors = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM authors a
                JOIN author_descriptions ad ON a.id=ad.author_id
                JOIN book_authors ba ON ba.author_id=a.id
                     WHERE ba.book_id = ?
            """)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("nationality"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("biography"),
                        rs.getString("website")
                ));
            }

        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return authors;
    }
}
