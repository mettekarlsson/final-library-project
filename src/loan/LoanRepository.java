package loan;

import exceptions.DatabaseException;
import exceptions.LoanNotFoundException;
import exceptions.OperationFailedException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    //get all (old and current) via member id
    public List<Loan> getAllLoansByMemberId(int memberId) {
        List<Loan> loans = new ArrayList<>();

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
            return loans;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //extend loan i.e. update due date
    public void extendLoan(int loanId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement("""
                   UPDATE loans
                   SET due_date = DATE_ADD(due_date, INTERVAL 14 DAY)
                   WHERE id=?
                   """)) {
            stmt.setInt(1, loanId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new LoanNotFoundException(loanId);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //för att kontrollera att lånet inte redan är returnerat när man ska lämna tillbaka det
    public Loan getLoanById(int loanId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("""
        SELECT * FROM loans
        WHERE id=?
        """)) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //för att undvika nullpointreexception på return date
                Date returnDateSql = rs.getDate("return_date");
                LocalDate returnDate = null;

                if (returnDateSql != null) {
                    returnDate = returnDateSql.toLocalDate();
                }
                return new Loan(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        null,
                        null,
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        returnDate
                );
            }

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return null;
    }

    //return loan i.e. update return date
    public void returnLoan(int loanId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            UPDATE loans
            SET return_date = CURDATE()
            WHERE id=?
            """)) {
            stmt.setInt(1, loanId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new OperationFailedException("Could not return loan.");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //add loan - insert i loans-tabellen, minska available copies - kolla felhantering här
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
            throw new DatabaseException(e);
        }
    }

    //leave review - insert into review table
    public String leaveReview(int bookId, int memberId, int rating, String comment) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("""
        INSERT INTO reviews (book_id, member_id, rating, comment, review_date)
        VALUES (?, ?, ?, ?, CURDATE())
""")) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, memberId);
            stmt.setInt(3, rating);
            stmt.setString(4, comment);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "You've left a review for book #" + bookId + ".";
            }

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return null;
    }

    //get all loans that are not returned yet
    public List<Loan> getAllCurrentLoans() {
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("""
            SELECT * FROM loans
            WHERE return_date IS NULL
            """);

            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        null,
                        null,
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        null
                ));
            }
            return loans;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    //get all loans that are overdue - due date has passed
    public List<Loan> getAllLateLoans() {
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("""
        SELECT * FROM loans
        WHERE due_date < CURDATE() AND return_date IS NULL
        """);

        while (rs.next()) {
            loans.add(new Loan(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("member_id"),
                    null,
                    null,
                    rs.getDate("loan_date").toLocalDate(),
                    rs.getDate("due_date").toLocalDate(),
                    null
            ));
        }
        return loans;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
