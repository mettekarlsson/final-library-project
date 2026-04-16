package loan;

import book.Book;
import book.BookRepository;
import member.Member;
import member.MemberRepository;

import java.util.ArrayList;

public class LoanService {
    BookRepository bookRepository = new BookRepository();
    MemberRepository memberRepository = new MemberRepository();
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

    public ArrayList<ActiveLoanDTO> getAllCurrentLoans() {
        return loanMapper.mapToActiveLoanDTO(loanRepository.getAllCurrentLoans());
    }
}
