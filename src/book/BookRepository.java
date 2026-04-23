package book;

import author.Author;
import category.Category;
import exceptions.DatabaseException;
import exceptions.OperationFailedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    //get all books
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

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
            return books;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //get 10 most borrowed books
    public List<Book> getPopularBooks() {
        List<Book> books = new ArrayList<>();

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
                return books;
            } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //search for book via title/summary
    public List<Book> searchBooks(String searchTerm) {
        List<Book> books = new ArrayList<>();
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
            return books;
            } catch (SQLException e){
                throw new DatabaseException(e);
        }
    }

    //filter books by category
    public List<Book> filterBooksByCategory(int categoryId) {
        List<Book> booksByCategory = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM books b
            JOIN book_descriptions bd ON bd.book_id=b.id
            JOIN book_categories bc ON bc.book_id=b.id
            JOIN categories c ON bc.category_id=c.id
            WHERE c.id = ?
            """)) {
                stmt.setInt(1, categoryId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    booksByCategory.add(new Book(
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
                } return booksByCategory;
            } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    //get book by id
    public Book getBookById(int bookId) {
        Book book = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM books b
            JOIN book_descriptions bd ON bd.book_id=b.id
            WHERE b.id=?
""")) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                book = new Book(
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
                );
            }
            return book;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    //add new book
    public String addBook(NewBookDTO newBookDTO) {

        //insert into books, get the new book-id
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);


            PreparedStatement stmt = conn.prepareStatement("""
        INSERT INTO books (title, isbn, year_published, total_copies, available_copies) VALUES (?, ?, ?, ?, ?)
        """, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, newBookDTO.getTitle());
            stmt.setString(2, newBookDTO.getIsbn());
            stmt.setInt(3, newBookDTO.getYearPublished());
            stmt.setInt(4, newBookDTO.getTotalCopies());
            stmt.setInt(5, newBookDTO.getAvailableCopies());
            stmt.executeUpdate();

            ResultSet generatedBookKey = stmt.getGeneratedKeys();
            int bookId = 0;
            if (generatedBookKey.next()) {
                bookId = generatedBookKey.getInt(1);
            }

            //insert into book_descriptions
            PreparedStatement bdStmt = conn.prepareStatement("""
                INSERT INTO book_descriptions (book_id, summary, language, page_count) VALUES (?, ?, ?, ?)
            """);
            bdStmt.setInt(1, bookId);
            bdStmt.setString(2, newBookDTO.getSummary());
            bdStmt.setString(3, newBookDTO.getLanguage());
            bdStmt.setInt(4, newBookDTO.getPageCount());

            bdStmt.executeUpdate();

            //insert into book_authors info, possibly authors - in that case get the new id and add it
            //lite orent att author-insert finns i bookrepo, men ser det som en transaktion,
            //om något misslyckas så blir det ingen inconsistency
            for (Author a : newBookDTO.getAuthors()) {
                int authorId = a.getId();

                if (authorId == 0) {
                    PreparedStatement authorStmt = conn.prepareStatement("""
                    INSERT INTO authors (first_name, last_name, nationality, birth_date) VALUES (?, ?, ?, ?)
                """, Statement.RETURN_GENERATED_KEYS);
                        authorStmt.setString(1, a.getFirstName());
                        authorStmt.setString(2, a.getLastName());
                        authorStmt.setString(3, a.getNationality());
                        authorStmt.setDate(4, Date.valueOf(a.getBirthDate()));
                        authorStmt.executeUpdate();
                        ResultSet rs = authorStmt.getGeneratedKeys();
                        if (rs.next()) {
                            authorId = rs.getInt(1);
                    }
                        PreparedStatement adStmt = conn.prepareStatement("""
                                INSERT INTO author_descriptions (author_id, biography, website) VALUES (?, ?, ?)
                                """);
                        adStmt.setInt(1, authorId);
                        adStmt.setString(2, a.getBiography());
                        adStmt.setString(3, a.getWebsite());
                        adStmt.executeUpdate();
                }
                //insert into book-categories
                PreparedStatement baStmt = conn.prepareStatement("""
                   INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)
                   """);
                baStmt.setInt(1, bookId);
                baStmt.setInt(2, authorId);
                baStmt.executeUpdate();


            }

            for (Category c : newBookDTO.getCategories()) {
                int categoryId = c.getId();

                PreparedStatement bcStmt = conn.prepareStatement("""
                INSERT INTO book_categories (book_id, category_id) VALUES (?, ?)
                """);
                bcStmt.setInt(1, bookId);
                bcStmt.setInt(2, categoryId);
                bcStmt.executeUpdate();

            }
            return "Bok med id #" + bookId + " har lagts till";

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //edit a book where the column has a string value
    public void editBook(int bookId, String column, String value) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET " + column + " = ? WHERE id = ?")) {

            stmt.setString(1, value);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    //edit a book where the column has an int value
    public void editBook(int bookId, String column, int value) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET " + column + " = ? WHERE id = ?")) {

            stmt.setInt(1, value);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //edit a column in book-descriptions where the column has a string value
    public void editBookDesc(int bookId, String column, String value) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("UPDATE book_descriptions SET " + column + " =? WHERE book_id=?")) {
            stmt.setString(1, value);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //edit a column in book-descriptions where the column has an int value
    public void editBookDesc(int bookId, String column, int value) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE book_descriptions SET " + column + " =? WHERE book_id=?")) {
            stmt.setInt(1, value);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //insert into book_authors
    public String addBookAuthors(int bookId, int authorId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("""
        INSERT INTO book_authors (book_id, author_id)
        VALUES (?,?)
""")) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, authorId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new OperationFailedException("Failed to add author to book.");
            }
            return "Author has been added to book.";

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //remove from book_authors
    public String removeBookAuthors(int bookId, int authorId){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
        DELETE FROM book_authors WHERE book_id = ? AND author_id = ?
""")) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, authorId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new OperationFailedException("Author is not linked to this book.");
            }
            return "Author removed from book.";

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //remove from book_categories
    public String removeBookCategories(int bookId, int categoryId){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
        DELETE FROM book_categories WHERE book_id = ? AND category_id = ?
""")) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, categoryId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new OperationFailedException("Category is not linked to this book.");
            }
            return "Category removed from book.";

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //check if author and book belong together
    public boolean existsBookAuthors(int bookId, int authorId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("""
        SELECT * FROM book_authors
        WHERE book_id=?
        AND author_id=?
        """)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, authorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }

            return false;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //checks if book and category belong together
    public boolean existsBookCategories(int bookId, int categoryId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
        SELECT * FROM book_categories
        WHERE book_id=?
        AND category_id=?
        """)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, categoryId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //delete book
    public String deleteBook(int bookId) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {
            stmt.setInt(1, bookId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Book with ID # " + bookId + " has been deleted.";
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //add a category to a book (insert book_categories)
    public String addCategoryToBook(int bookId, int categoryId) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
                     INSERT INTO book_categories (book_id, category_id) VALUES (?, ?)
                     """)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, categoryId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Category with id #" + categoryId + " has been added to Book with id #" + bookId;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //for loanservice, to add book-info into loan-object
    public Book findBookByLoanId(int loanId) {
        Book book = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM books b
                JOIN loans l ON l.book_id=b.id
                JOIN book_descriptions bd ON bd.book_id=b.id
                     WHERE l.id=?
            """)) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                book = new Book(
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

                );
            }

        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return book;
    }
}
