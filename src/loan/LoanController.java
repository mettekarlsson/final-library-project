package loan;

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
            System.out.println("2. See all active loans");
            System.out.println("3. Loan a book");
            System.out.println("4. Extend a loan");
            System.out.println("5. Return a book");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    getAllLoansByMemberId();
                    break;
                }
                case 2: {
                    //loanService.getAllCurrentLoans(loggedInUser.getId());
                    break;
                }
                case 3: {
                    //loanService.addNewLoan();
                    break;
                }
                case 4: {
                    //loanService.extendLoan();
                    break;
                }
                case 5: {
                    //loanService.returnLoan();
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