package loan;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static Main.MainController.loggedInUser;

public class LoanRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    public ArrayList<Loan> getAllLoansByMemberId(int memberId) {
        ArrayList<Loan> loans = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM loans
            WHERE member_id=?
""")) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                Date returnDateSql = rs.getDate("return_date");
                LocalDate returnDate = null;

                if (returnDateSql != null) {
                    returnDate = returnDateSql.toLocalDate();
                }

                loans.add(new Loan(rs.getInt("id"),
                rs.getInt("book_id"),
                rs.getInt("member_id"),
                null,
                null,
                rs.getDate("loan_date").toLocalDate(),
                rs.getDate("due_date").toLocalDate(),
                returnDate));

            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return loans;
    }

    public String extendLoan(int loanId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement("""
                   UPDATE loans
                   SET due_date = DATE_ADD(due_date, INTERVAL 14 DAY) 
                   WHERE id=?
                   """)) {
            stmt.setInt(1, loanId);
            stmt.executeUpdate();

            return "Your loan #" + loanId + " has been extended.";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String returnLoan(int loanId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            UPDATE loans
            SET return_date = CURDATE()
            WHERE id=?
            """)) {
            stmt.setInt(1, loanId);
            stmt.executeUpdate();

            return "Your loan #" + loanId + " has been returned.";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String addNewLoan(int bookId, int memberId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("""
        INSERT INTO loans (book_id, member_id, loan_date, due_date, return_date)
        VALUES (?, ?, CURDATE(), CURDATE() + INTERVAL 14 DAY, null)
        """)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, memberId);
            stmt.executeUpdate();

            PreparedStatement copiesStmt = conn.prepareStatement("""
            UPDATE books
            SET available_copies = available_copies -1
            WHERE id=?
""");
            copiesStmt.setInt(1, bookId);
            copiesStmt.executeUpdate();

            return "You've loaned book #" + bookId +".";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String leaveReview(int bookId, int rating, String comment) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("""
        INSERT INTO reviews (book_id, member_id, rating, comment, review_date)
        VALUES (?, ?, ?, ?, CURDATE())
""")) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, loggedInUser.getId());
            stmt.setInt(3, rating);
            stmt.setString(4, comment);
            stmt.executeUpdate();

            return "You've left a review for book #" + bookId + ".";
        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return null;
    }


    public ArrayList<Loan> getAllCurrentLoans() {
        ArrayList<Loan> loans = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("""
            SELECT * FROM loans
            WHERE return_date IS NULL
            """);

            while (rs.next()) {
                Date returnDateSql = rs.getDate("return_date");
                LocalDate returnDate = null;

                if (returnDateSql != null) {
                    returnDate = returnDateSql.toLocalDate();
                }
                loans.add(new Loan(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        null,
                        null,
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        returnDate
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loans;
    }

    public ArrayList<Loan> getAllLateLoans() {
        ArrayList<Loan> loans = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("""
        SELECT * FROM loans
        WHERE due_date < CURDATE() AND return_date IS NULL
        """);

        while (rs.next()) {
            Date returnDateSql = rs.getDate("return_date");
            LocalDate returnDate = null;

            if (returnDateSql != null) {
                returnDate = returnDateSql.toLocalDate();
            }
            loans.add(new Loan(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("member_id"),
                    null,
                    null,
                    rs.getDate("loan_date").toLocalDate(),
                    rs.getDate("due_date").toLocalDate(),
                    returnDate
            ));
        }
        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return loans;
    }
}
