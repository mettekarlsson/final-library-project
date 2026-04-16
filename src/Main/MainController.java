package Main;

import book.BookController;
import loan.LoanController;
import member.Member;
import member.MemberController;
import member.MemberService;

import java.sql.SQLException;
import java.util.Scanner;

public class MainController {

    public static Member loggedInUser = null;
    Scanner scanner = new Scanner(System.in);
    BookController bookController = new BookController();
    MemberController memberController = new MemberController();
    MemberService memberService = new MemberService();
    LoanController loanController = new LoanController();


    public void memberLogIn() {
        System.out.println("Enter your email");
        Member member = memberService.getMemberByEmail(scanner.nextLine());
        if (member == null) {
            System.out.println("Email not found");
            return;
        }
        System.out.println("Enter your password");
        String password = scanner.nextLine();

        if(password.equals(member.getPassword())) {
            loggedInUser = member;
            memberMainMenu();
        } else {
            System.out.println("Wrong password");
        }
    }

    public void memberMainMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Welcome to the library ----");
            System.out.println("1. Books");
            System.out.println("2. Loans");
            System.out.println("3. My profile");
            System.out.println("4. Authors? ");
            System.out.println("0. Log out");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    bookController.memberBookMenu();
                    break;
                }
                case 2: {
                    loanController.memberLoanMenu();
                    break;
                }
                case 3: {
                    memberController.memberProfileMenu();
                    break;
                }
                case 4: {
                    // memberAuthorMenu();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    public void adminMainMenu() throws SQLException {
        boolean active = true;
        while (active) {
            System.out.println("---- Welcome admin ----");
            System.out.println("1. Books");
            System.out.println("2. Loans");
            System.out.println("3. Members");
            System.out.println("4. Authors? ");
            System.out.println("0. Log out");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    bookController.adminBookMenu();
                    break;
                }
                case 2: {
                    //adminLoanMenu();
                    break;
                }
                case 3: {
                    memberController.adminMemberMenu();
                    break;
                }
                case 4: {
                    //adminAuthorMenu();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }
}
