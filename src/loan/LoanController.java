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
            System.out.println("2. See all my active loans");
            System.out.println("3. Extend a loan");
            System.out.println("4. Return a book");
            System.out.println("5. Loan a book");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    getAllLoansByMemberId();
                    break;
                }
                case 2: {
                    getAllCurrentLoans();
                    break;
                }
                case 3: {
                    extendLoan();
                    break;
                }
                case 4: {
                    //loanService.returnLoan();
                    break;
                }
                case 5: {
                    //loanService.addNewLoan();
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
    public void getAllCurrentLoans() {
        ArrayList<LoanInfoDTO> loans = new ArrayList<>(loanService.getAllCurrentLoans(loggedInUser.getId()));
        for (LoanInfoDTO l : loans) {
            System.out.println(l.toString());
        }
    }

    //case 3
    public void extendLoan() {
        System.out.println("Which loan(id) would you like to extend?");
        int loanId = Integer.parseInt(scanner.nextLine());
        String result = loanService.extendLoan(loanId);
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