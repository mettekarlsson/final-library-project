package category;

import exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM categories");

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }

            return categories;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Category getCategoryById(int categoryId) {
        Category category;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM categories
            WHERE id = ?
            """)) {
            stmt.setInt(1, categoryId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
            } else {
                return null;
            }

            return category;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public List<Category> findCategoriesByBookId(int bookId) {
        List<Category> categories = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM categories c
                JOIN book_categories bc ON c.id=bc.category_id
                WHERE bc.book_id = ?
            """)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }

            return categories;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}





