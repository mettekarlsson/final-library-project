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

    public static LoanInfoDTO mapToLoanInfoDTO(Loan loan) {
        return new LoanInfoDTO(
                loan.getId(),
                loan.getBookId(),
                loan.getBook().getTitle(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate()
        );
    }

    public static AdminLoanDTO mapToAdminLoanDTO(Loan loan) {
        return new AdminLoanDTO(
                loan.getId(),
                loan.getBook().getTitle(),
                loan.getMemberId(),
                (loan.getMember().getFirstName() + " " + loan.getMember().getLastName()),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate()
        );
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
