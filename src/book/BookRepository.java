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

    public ArrayList<Book> getPopularBooks() {
        ArrayList<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(
                            """
            SELECT b.id, b.title, b.isbn, b.year_published, b.total_copies, b.available_copies, bd.summary, bd.language, bd.page_count, COUNT(loans.book_id) AS loan_count FROM books b
            JOIN book_descriptions bd ON bd.book_id=b.id
            JOIN loans ON loans.book_id=b.id
            GROUP BY b.id ORDER BY loan_count DESC LIMIT 10
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
            System.out.println("SQL-fel: " + e.getMessage());
        }
        return books;
    }

    //sök efter en bok via boktitel/beskrivning
    public ArrayList<Book> searchBook(String searchTerm) {
        ArrayList<Book> books = new ArrayList<>();
        String search = "%" + searchTerm + "%";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM books b JOIN book_descriptions bd ON bd.book_id=b.id WHERE title LIKE ? OR summary LIKE ?

""")){
            stmt.setString(1, search);
            stmt.setString(2, search);
            ResultSet rs = stmt.executeQuery();

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
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        return books;
        }

        //redigera en bok där kolumnen du redigerar är ett string värde
    public void editBook(int bookId, String column, String value) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET " + column + " = ? WHERE id = ?")) {

            stmt.setString(1, value);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //redigera en bok där kolumnen du redigerar är ett int värde
    public void editBook(int bookId, String column, int value) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET " + column + " = ? WHERE id = ?")) {

            stmt.setInt(1, value);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
        //ta bort en bok
    public void deleteBook(int bookId) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
    }
        }
