package loan;

import book.Book;
import book.BookRepository;
import member.Member;
import member.MemberRepository;

import java.util.ArrayList;
import java.util.List;

public class LoanMapper {

    BookRepository bookRepository = new BookRepository();
    MemberRepository memberRepository = new MemberRepository();

    public List<LoanInfoDTO> mapToLoanInfoDTO(List<Loan> loans) {
        List<LoanInfoDTO> dtos = new ArrayList<>();
        for (Loan l : loans) {
            Book book = bookRepository.findBookByLoanId(l.getId());
            //Member member = memberRepository.findMemberByLoanId(l.getId());
            LoanInfoDTO loanInfoDTO = new LoanInfoDTO(l.getId(), book.getId(), book.getTitle(), l.getLoanDate(), l.getDueDate(), l.getReturnDate());
            dtos.add(loanInfoDTO);
        }
        return dtos;
    }

    public List<AdminLoanDTO> mapToAdminLoanDTO(List<Loan> loans) {
        List<AdminLoanDTO> dtos = new ArrayList<>();
        for (Loan l : loans) {
            Book book = bookRepository.findBookByLoanId(l.getId());
            Member member = memberRepository.findMemberByLoanId(l.getId());
            AdminLoanDTO activeLoanDTO = new AdminLoanDTO(l.getId(), book.getTitle(), member.getId(), (member.getFirstName() + " " + member.getLastName()), l.getLoanDate(), l.getDueDate(), l.getReturnDate());
            dtos.add(activeLoanDTO);
        }
        return dtos;
    }
}
