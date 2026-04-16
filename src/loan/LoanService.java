package loan;

import author.Author;
import book.Book;
import book.BookRepository;
import category.Category;
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
}
