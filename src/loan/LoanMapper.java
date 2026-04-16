package loan;

import book.Book;
import book.BookRepository;
import member.Member;
import member.MemberRepository;

import java.util.ArrayList;

public class LoanMapper {

    BookRepository bookRepository = new BookRepository();
    MemberRepository memberRepository = new MemberRepository();

    public ArrayList<LoanInfoDTO> mapToLoanInfoDTO(ArrayList<Loan> loans) {
        ArrayList<LoanInfoDTO> dtos = new ArrayList<>();
        for (Loan l : loans) {
            Book book = bookRepository.findBookByLoanId(l.getId());
            Member member = memberRepository.findMemberByLoanId(l.getId());
            LoanInfoDTO loanInfoDTO = new LoanInfoDTO(l.getId(), book.getTitle(), l.getLoanDate(), l.getDueDate(), l.getReturnDate());
            dtos.add(loanInfoDTO);
        }
        return dtos;
    }

    public ArrayList<ActiveLoanDTO> mapToActiveLoanDTO(ArrayList<Loan> loans) {
        ArrayList<ActiveLoanDTO> dtos = new ArrayList<>();
        for (Loan l : loans) {
            Book book = bookRepository.findBookByLoanId(l.getId());
            Member member = memberRepository.findMemberByLoanId(l.getId());
            ActiveLoanDTO activeLoanDTO = new ActiveLoanDTO(l.getId(), book.getTitle(), (member.getFirstName() + " " + member.getLastName()), l.getLoanDate(), l.getDueDate(), l.getReturnDate());
            dtos.add(activeLoanDTO);
        }
        return dtos;
    }
}
