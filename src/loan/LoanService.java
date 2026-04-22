package loan;

import java.util.ArrayList;
import java.util.List;

public class LoanService {
    LoanRepository loanRepository = new LoanRepository();
    LoanMapper loanMapper = new LoanMapper();

    //case 1
    public List<LoanInfoDTO> getAllLoansByMemberId(int memberId) {
        return loanMapper.mapToLoanInfoDTO(loanRepository.getAllLoansByMemberId(memberId));
    }

    public List<LoanInfoDTO> getAllCurrentLoans(int memberId) {
        List<Loan> loans = loanRepository.getAllLoansByMemberId(memberId);
        List<Loan> currentLoans = new ArrayList<>();
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
    public List<LoanInfoDTO> getReturnedLoans(int memberId) {
        List<Loan> loans = loanRepository.getAllLoansByMemberId(memberId);
        List<Loan> returnedLoans = new ArrayList<>();
        for (Loan l : loans) {
            if (l.getReturnDate() != null) {
                returnedLoans.add(l);
            }
        }
        return loanMapper.mapToLoanInfoDTO(returnedLoans);
    }

    public String leaveReview(int bookId, int memberId, int rating, String review) {
        return loanRepository.leaveReview(bookId, memberId, rating, review);
    }

    public List<AdminLoanDTO> getAllCurrentLoans() {
        return loanMapper.mapToAdminLoanDTO(loanRepository.getAllCurrentLoans());
    }

    public List<AdminLoanDTO> getAllLateLoans() {
        return loanMapper.mapToAdminLoanDTO(loanRepository.getAllLateLoans());
    }
}
