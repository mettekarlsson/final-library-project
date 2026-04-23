package loan;

import book.Book;
import book.BookRepository;
import exceptions.*;
import member.Member;
import member.MemberRepository;

import java.util.ArrayList;
import java.util.List;

public class LoanService {
    LoanRepository loanRepository = new LoanRepository();
    BookRepository bookRepository = new BookRepository();
    MemberRepository memberRepository = new MemberRepository();

    //case 1
    public List<LoanInfoDTO> getAllLoansByMemberId(int memberId) {
        List<Loan> loans = loanRepository.getAllLoansByMemberId(memberId);
        List<LoanInfoDTO> dtos = new ArrayList<>();
        for (Loan l : loans) {
            Book book = bookRepository.findBookByLoanId(l.getId());
            dtos.add(LoanMapper.mapToLoanInfoDTO(l, book));
        }
        return dtos;
    }

    //case 2 - filtrerar ut aktiva lån
    public List<LoanInfoDTO> getAllCurrentLoans(int memberId) {
        List<Loan> loans = loanRepository.getAllLoansByMemberId(memberId);
        List<LoanInfoDTO> currentLoans = new ArrayList<>();
        for (Loan l : loans) {
            if (l.getReturnDate() == null) {
                Book book = bookRepository.findBookByLoanId(l.getId());
                currentLoans.add(LoanMapper.mapToLoanInfoDTO(l, book));
            }
        }
        return currentLoans;
    }

    //case 2 - check that it belongs to the user, and is not returned
    public void extendLoan(int loanId, int memberId) {
        Loan loan = loanRepository.getLoanById(loanId);
        if (loan == null) {
            throw new LoanNotFoundException(loanId);
        }
        if (loan.getMemberId() != memberId) {
            throw new ValidationException("This loan does not belong to you.");
        }
        if (loan.getReturnDate() != null) {
            throw new LoanReturnedException(loanId);
        }
        loanRepository.extendLoan(loanId);
    }

    //case 3 and admin case 3 - check that loan isnt already returned, and that it belongs to the user
    public void returnLoan(int loanId, int memberId) {
        Loan loan = loanRepository.getLoanById(loanId);
        if (loan == null) { throw new LoanNotFoundException(loanId); }
        if (loan.getMemberId() != memberId) {
            throw new ValidationException("The loan and the member doesn't match.");
        }
        if (loan.getReturnDate() != null) {
            throw new LoanReturnedException(loanId);
        }
        loanRepository.returnLoan(loanId);
    }

    //case 4 - check that book exists and that availablecopies > 0,
    //check that member exists and that status = active
    public String addNewLoan(int bookId, int memberId) {
        Book book = bookRepository.getBookById(bookId);
        Member member = memberRepository.getMemberById(memberId);
        if (book == null) throw new BookNotFoundException(bookId);
        if (member == null) throw new MemberNotFoundException(memberId);
        if (book.getAvailableCopies() == 0) throw new BookNotAvailableException(bookId);
        if (!member.getStatus().equals("active")) throw new InvalidMemberStatusException(memberId);

        return loanRepository.addNewLoan(bookId, memberId);
    }

    //case 5 - leave review
    public List<LoanInfoDTO> getReturnedLoans(int memberId) {
        List<Loan> loans = loanRepository.getAllLoansByMemberId(memberId);
        List<LoanInfoDTO> returnedLoans = new ArrayList<>();
        for (Loan l : loans) {
            if (l.getReturnDate() != null) {
                Book book = bookRepository.findBookByLoanId(l.getId());
                returnedLoans.add(LoanMapper.mapToLoanInfoDTO(l, book));
            }
        }
        return returnedLoans;
    }

    //case 5
    public String leaveReview(int bookId, int memberId, int rating, String review) {
       List<Loan> loans = loanRepository.getAllLoansByMemberId(memberId);
       boolean foundLoanForBook = false;
       for (Loan l : loans) {
            if (l.getBookId() == bookId) {
                foundLoanForBook = true;
                if (l.getReturnDate() == null) {
                    throw new ValidationException("You must return the book before you can leave a review.");
                }
            }
       }
        if (!foundLoanForBook) {
            throw new ValidationException("You have not borrowed this book.");
        }
        if (rating < 1 || rating > 5) {
            throw new ValidationException("Rating must be between 1 and 5.");
        }
        String result = loanRepository.leaveReview(bookId, memberId, rating, review);
        if (result == null) {
            throw new OperationFailedException("Failed to leave review.");
        }
        return result;
    }

    //admin case 1
    public List<AdminLoanDTO> getAllCurrentLoans() {
        List<Loan> loans = loanRepository.getAllCurrentLoans();
        List<AdminLoanDTO> dtos = new ArrayList<>();
        for (Loan l : loans) {
            Member member = memberRepository.findMemberByLoanId(l.getId());
            Book book = bookRepository.findBookByLoanId(l.getId());
            dtos.add(LoanMapper.mapToAdminLoanDTO(l, member, book));
        }
        return dtos;
    }

    //admin case 2
    public List<AdminLoanDTO> getAllLateLoans() {
        List<Loan> loans = loanRepository.getAllLateLoans();
        List<AdminLoanDTO> dtos = new ArrayList<>();
        for (Loan l : loans) {
            Member member = memberRepository.findMemberByLoanId(l.getId());
            Book book = bookRepository.findBookByLoanId(l.getId());
            dtos.add(LoanMapper.mapToAdminLoanDTO(l, member, book));
        }
        return dtos;
    }
}
