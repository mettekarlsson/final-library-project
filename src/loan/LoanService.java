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

    public ArrayList<LoanInfoDTO> mapToLoanDTO(ArrayList<Loan> loans) {
        ArrayList<LoanInfoDTO> dtos = new ArrayList<>();
        for (Loan l : loans) {
            Book book = bookRepository.findBookByLoanId(l.getId());
            Member member = memberRepository.findMemberByLoanId(l.getId());
            LoanInfoDTO loanInfoDTO = new LoanInfoDTO(l.getId(), book.getTitle(), l.getLoanDate(), l.getDueDate(), l.getReturnDate());
            dtos.add(loanInfoDTO);
        }
        return dtos;
    }

    public ArrayList<LoanInfoDTO> getAllLoansByMemberId(int memberId) {
        return mapToLoanDTO(loanRepository.getAllLoansByMemberId(memberId));
    }

    public ArrayList<LoanInfoDTO> getAllCurrentLoans(int memberId) {
        ArrayList<Loan> loans = loanRepository.getAllLoansByMemberId(memberId);
        ArrayList<Loan> currentLoans = new ArrayList<>();
        for (Loan l : loans) {
            if (l.getReturnDate() == null) {
                currentLoans.add(l);
            }
        }
        return mapToLoanDTO(currentLoans);
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
}
