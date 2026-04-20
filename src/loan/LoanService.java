package loan;

import book.BookRepository;
import member.MemberRepository;

import java.util.ArrayList;

import static Main.MainController.loggedInUser;

public class LoanService {
    LoanRepository loanRepository = new LoanRepository();
    LoanMapper loanMapper = new LoanMapper();

    public ArrayList<LoanInfoDTO> getAllLoansByMemberId(int memberId) {
        return loanMapper.mapToLoanInfoDTO(loanRepository.getAllLoansByMemberId(memberId));
    }

    public ArrayList<LoanInfoDTO> getAllCurrentLoans(int memberId) {
        ArrayList<Loan> loans = loanRepository.getAllLoansByMemberId(memberId);
        ArrayList<Loan> currentLoans = new ArrayList<>();
        for (Loan l : loans) {
            if (l.getReturnDate() == null) {
                currentLoans.add(l);
            }
        }
        return loanMapper.mapToLoanInfoDTO(currentLoans);
    }

    public String extendLoan(int loanId) {
        return loanRepository.extendLoan(loanId);
    }

    public String returnLoan(int loanId) {
        return loanRepository.returnLoan(loanId);
    }

    public String addNewLoan(int bookId, int memberId) {
        return loanRepository.addNewLoan(bookId, memberId);
    }

    //leave review
    public ArrayList<LoanInfoDTO> getReturnedLoans() {
        ArrayList<Loan> loans = loanRepository.getAllLoansByMemberId(loggedInUser.getId());
        ArrayList<Loan> returnedLoans = new ArrayList<>();
        for (Loan l : loans) {
            if (l.getReturnDate() != null) {
                returnedLoans.add(l);
            }
        }
        return loanMapper.mapToLoanInfoDTO(returnedLoans);
    }

    public String leaveReview(int bookId, int rating, String review) {
        return loanRepository.leaveReview(bookId, rating, review);
    }

    public ArrayList<AdminLoanDTO> getAllCurrentLoans() {
        return loanMapper.mapToAdminLoanDTO(loanRepository.getAllCurrentLoans());
    }

    public ArrayList<AdminLoanDTO> getAllLateLoans() {
        return loanMapper.mapToAdminLoanDTO(loanRepository.getAllLateLoans());
    }
}
