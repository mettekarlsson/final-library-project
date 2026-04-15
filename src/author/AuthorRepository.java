package author;

import book.Book;

import java.sql.*;
import java.util.ArrayList;

public class AuthorRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    public Author findAuthorById(int authorId) {
        Author author = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM authors a
                JOIN author_descriptions ad ON a.id=ad.author_id
                WHERE a.id = ?
            """)) {
            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                author = new Author(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("nationality"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("biography"),
                        rs.getString("website")
                );
            }

        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return author;
    }

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

    public ArrayList<Author> searchAuthor(String searchTerm) {
        ArrayList<Author> authors = new ArrayList<>();
        String search = "%" + searchTerm + "%";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM authors a JOIN author_descriptions ad ON ad.author_id=a.id WHERE first_name LIKE ? OR last_name LIKE ?

""")){
            stmt.setString(1, search);
            stmt.setString(2, search);
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
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return authors;
    }
}
