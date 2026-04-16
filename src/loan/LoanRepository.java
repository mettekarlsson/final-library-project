package loan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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
}
