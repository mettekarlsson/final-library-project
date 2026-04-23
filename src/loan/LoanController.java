package loan;

import base.BaseController;
import exceptions.LibraryException;

import java.util.ArrayList;
import java.util.List;

import static main.MainController.loggedInUser;

public class LoanController extends BaseController {
    LoanService loanService = new LoanService();

    public void memberLoanMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Loan Menu ----");
            System.out.println("1. See all my loans");
            System.out.println("2. Extend a loan");
            System.out.println("3. Return a book");
            System.out.println("4. Borrow a book");
            System.out.println("5. Leave a review");
            System.out.println("0. Return");
            int choice = readInt();

            try {
                switch (choice) {
                    case 1: {
                        getAllLoansByMemberId();
                        break;
                    }
                    case 2: {
                        extendLoan();
                        break;
                    }
                    case 3: {
                        returnLoan();
                        break;
                    }
                    case 4: {
                        addNewLoan();
                        break;
                    }
                    case 5: {
                        leaveReview();
                        break;
                    }
                    case 0: {
                        active = false;
                        break;
                    }
                }
            } catch (LibraryException e) {
                handleException(e);
            }
        }
    }

    //case 1
    public void getAllLoansByMemberId() {
        List<LoanInfoDTO> loans = new ArrayList<>(loanService.getAllLoansByMemberId(loggedInUser.getId()));
        for (LoanInfoDTO l : loans) {
            System.out.println(l.toString());
        }
    }

    //case 2
    public void extendLoan() {
        List<LoanInfoDTO> loans = new ArrayList<>(loanService.getAllCurrentLoans(loggedInUser.getId()));
        for (LoanInfoDTO l : loans) {
            System.out.println(l.toString());
        }
        System.out.println("Which loan(id) would you like to extend?");
        int loanId = readInt();
        loanService.extendLoan(loanId, loggedInUser.getId());
        System.out.println("Loan with ID #" + loanId + " has been extended.");
    }

    //case 3
    public void returnLoan() {
        List<LoanInfoDTO> loans = new ArrayList<>(loanService.getAllCurrentLoans(loggedInUser.getId()));
        for (LoanInfoDTO l : loans) {
            System.out.println(l.toString());
        }
        System.out.println("Which loan(id) would you like to return?");
        int loanId = readInt();
        loanService.returnLoan(loanId, loggedInUser.getId());
        System.out.println("Loan with ID # " + loanId + " has been returned.");
    }

    //case 4
    public void addNewLoan() {
        System.out.println("Which book(id) would you like to loan?");
        int bookId = readInt();
        int memberId = loggedInUser.getId();
        String result = loanService.addNewLoan(bookId, memberId);
        System.out.println(result);
    }

    //case 5
    public void leaveReview() {
        List<LoanInfoDTO> loans = loanService.getReturnedLoans(loggedInUser.getId());
        for (LoanInfoDTO l : loans) {
            System.out.println(l);
        }
        System.out.println("Which book(id) would you like to review?");
        int bookId = readInt();
        System.out.println("Enter your rating (1-5):");
        int rating = readInt();
        while (rating > 5 || rating < 1) {
            System.out.println("Invalid rating. Try again");
            rating = readInt();
        }
        System.out.println("Enter your review:");
        String comment = scanner.nextLine();
        String result = loanService.leaveReview(bookId, loggedInUser.getId(), rating, comment);
        System.out.println(result);
    }

    public void adminLoanMenu(){
        boolean active = true;
        while (active) {
            System.out.println("---- Loan Menu ----");
            System.out.println("1. See all current loans");
            System.out.println("2. See all overdue loans");
            System.out.println("3. Return loan");
            System.out.println("0. Return");
            int choice = readInt();

            try {
                switch (choice) {
                    case 1: {
                        getAllCurrentLoans();
                        break;
                    }
                    case 2: {
                        getAllLateLoans();
                        break;
                    }
                    case 3: {
                        adminReturnLoan();
                        break;
                    }
                    case 0: {
                        active = false;
                        break;
                    }
                }
            } catch (LibraryException e) {
                handleException(e);
            }
        }
    }

    //case 1 admin
    public void getAllCurrentLoans() {
        List<AdminLoanDTO> loans = loanService.getAllCurrentLoans();
        for (AdminLoanDTO l : loans) {
            System.out.println(l.toString());
        }
    }

    //case 2 admin
    public void getAllLateLoans() {
        List<AdminLoanDTO> loans = loanService.getAllLateLoans();
        for (AdminLoanDTO l : loans) {
            System.out.println(l.toString());
        }
    }

    //case 3 admin
    public void adminReturnLoan() {
        List<AdminLoanDTO> loans = loanService.getAllCurrentLoans();
        for (AdminLoanDTO l : loans) {
            System.out.println(l);
        }
        System.out.println("Which loan(id) would you like to return?:");
        int loanId = readInt();
        System.out.println("What user does it belong to?:");
        int memberId = readInt();
        loanService.returnLoan(loanId, memberId);
        System.out.println("Loan with ID # " + loanId + " has been returned.");
    }
}