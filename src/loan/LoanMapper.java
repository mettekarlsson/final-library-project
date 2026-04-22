package loan;

import book.Book;
import member.Member;

public class LoanMapper {

    public static LoanInfoDTO mapToLoanInfoDTO(Loan loan, Book book) {
        return new LoanInfoDTO(
                loan.getId(),
                loan.getBookId(),
                book.getTitle(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate()
        );
    }

    public static AdminLoanDTO mapToAdminLoanDTO(Loan loan, Member member, Book book) {
        return new AdminLoanDTO(
                loan.getId(),
                book.getTitle(),
                loan.getMemberId(),
                (member.getFirstName() + " " + member.getLastName()),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate()
        );
    }
}
