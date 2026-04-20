package member;

import java.sql.*;
import java.util.ArrayList;

import static Main.MainController.loggedInUser;

public class MemberRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

    public Member getMemberById(int id) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Member(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getString("membership_type"),
                        rs.getString("status"),
                        rs.getString("password"));
            }
        }
        catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }


    public Member getMemberByEmail(String email) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE email = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new Member(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getString("membership_type"),
                        rs.getString("status"),
                        rs.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

    public String updateMemberInfo(String column, String newValue) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE members SET " + column + " = ? WHERE id = ?")) {
            stmt.setString(1, newValue);
            stmt.setInt(2, loggedInUser.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return "Your profile has been updated.";
    }

    public ArrayList<Member> getAllMembers() {
        ArrayList<Member> members = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM members");

            while (rs.next()) {
                members.add(new Member(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getString("membership_type"),
                        rs.getString("status"),
                        rs.getString("password")));
            }
            return members;

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

    public String addNewMember(NewMemberDTO member) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO members (first_name, last_name, email, membership_date, membership_type, status, password)  VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setString(3, member.getEmail());
            stmt.setDate(4, java.sql.Date.valueOf(member.getMembershipDate()));
            stmt.setString(5, member.getMembershipType());
            stmt.setString(6, member.getStatus());
            stmt.setString(7, member.getPassword());

            stmt.executeUpdate();
            ResultSet generatedMemberKey = stmt.getGeneratedKeys();

            int memberId = 0;
            if (generatedMemberKey.next()) {
                memberId = generatedMemberKey.getInt(1);
            }

            return "Member with ID #" + memberId + " has been added.";

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

    public String suspendMember(int memberId){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE members SET status = ? WHERE id = ?")) {
            stmt.setString(1, "suspended");
            stmt.setInt(2, memberId);

            stmt.executeUpdate();
            return "Member with ID #" + memberId + " has been suspended.";

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

    public String removeMember(int memberId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM members WHERE id = ?")) {
            stmt.setInt(1, memberId);

            stmt.executeUpdate();
            return "Member with ID #" + memberId + " has been removed.";

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

    //for loanservice, to add member-info into loan-object
    public Member findMemberByLoanId(int loanId) {
        Member member = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * FROM members m
                JOIN loans l ON l.member_id=m.id
                     WHERE l.id=?
            """)) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                member = new Member
                        (rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getString("membership_type"),
                        rs.getString("status"),
                        rs.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println("SQL-FEL: " + e.getMessage());
        }
        return member;
    }
}
