package book;

import author.Author;
import category.Category;

import java.sql.*;
import java.util.ArrayList;

public class BookRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    //hämta alla böcker från biblioteket
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("""
            SELECT * FROM books b JOIN book_descriptions bd ON b.id = bd.book_id
            """);

            while (rs.next()) {
                books.add(new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("isbn"),
                rs.getInt("year_published"),
                rs.getInt("total_copies"),
                rs.getInt("available_copies"),
                rs.getString("summary"),
                rs.getString("language"),
                rs.getInt("page_count"),
                new ArrayList<>(),
                new ArrayList<>()
                ));

            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
    }
}