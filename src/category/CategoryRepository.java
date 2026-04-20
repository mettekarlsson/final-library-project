package category;

import java.sql.*;
import java.util.ArrayList;

public class CategoryRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();

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

        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return categories;
    }

    public Category getCategoryById(int categoryId) {
        Category category = null;

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
            }

        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return category;
    }

    public ArrayList<Category> findCategoriesByBookId(int bookId) {
        ArrayList<Category> categories = new ArrayList<>();

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

        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return categories;
    }

    }





