package loan;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import static Main.MainController.loggedInUser;

public class LoanController {
    Scanner scanner = new Scanner(System.in);
    LoanService loanService = new LoanService();

    public void memberLoanMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Loan Menu ----");
            System.out.println("1. See all my loans");
            System.out.println("2. Extend a loan");
            System.out.println("3. Return a book");
            System.out.println("4. Loan a book");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

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
                case 0: {
                    active = false;
                    break;
                }
            }

        }
    }

    //case 1
    public void getAllLoansByMemberId() {
        ArrayList<LoanInfoDTO> loans = new ArrayList<>(loanService.getAllLoansByMemberId(loggedInUser.getId()));
        for (LoanInfoDTO l : loans) {
            System.out.println(l.toString());
        }
    }

    //case 2
    public void extendLoan() {
        ArrayList<LoanInfoDTO> loans = new ArrayList<>(loanService.getAllCurrentLoans(loggedInUser.getId()));
        for (LoanInfoDTO l : loans) {
            System.out.println(l.toString());
        }
        System.out.println("Which loan(id) would you like to extend?");
        int loanId = Integer.parseInt(scanner.nextLine());
        String result = loanService.extendLoan(loanId);
        System.out.println(result);
    }

    //case 3
    public void returnLoan() {
        ArrayList<LoanInfoDTO> loans = new ArrayList<>(loanService.getAllCurrentLoans(loggedInUser.getId()));
        for (LoanInfoDTO l : loans) {
            System.out.println(l.toString());
        }
        System.out.println("Which loan(id) would you like to return?");
        int loanId = Integer.parseInt(scanner.nextLine());
        String result = loanService.returnLoan(loanId);
        System.out.println(result);
    }

    //case 4
    public void addNewLoan() {
        System.out.println("Which book(id) would you like to loan?");
        int bookId = Integer.parseInt(scanner.nextLine());
        int memberId = loggedInUser.getId();
        String result = loanService.addNewLoan(bookId, memberId);
        System.out.println(result);
    }

    public void adminLoanMenu(){
        boolean active = true;
        while (active) {
            System.out.println("---- Loan Menu ----");
            System.out.println("1. See all current loans");
            System.out.println("2. See all returned loans");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    //loanService.getAllCurrentLoans();
                    break;
                }
                case 2: {
                    //loanService.getAllReturnedLoans();
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