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

    //case 2
    public String extendLoan(int loanId) {
        String result =  loanRepository.extendLoan(loanId);
        if (result == null) {
            throw new LoanNotFoundException(loanId);
        }
        return result;
    }

    //case 3 - check that loan isnt already returned
    public String returnLoan(int loanId) {
        Loan loan = loanRepository.getLoanById(loanId);
        if (loan.getReturnDate() != null) {
            throw new LoanReturnedException(loanId);
        }
        String result = loanRepository.returnLoan(loanId);
        if (result == null) {
            throw new LoanNotFoundException(loanId);
        }
        return result;
    }

    //case 4 - check that book exists and that availablecopies > 0,
    //check that member exists and that status = active
    public String addNewLoan(int bookId, int memberId) {
        Book book = bookRepository.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException(bookId);
        } else if (book.getAvailableCopies() == 0) {
            throw new BookNotAvailableException(bookId);
        }
        Member member = memberRepository.getMemberById(memberId);
        if (member == null) {
            throw new MemberNotFoundException(memberId);
        } else if (!member.getStatus().equals("active")) {
            throw new InvalidMemberStatusException(memberId);
        }
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

    //case 5 - vilket exception ska throwas här?
    public String leaveReview(int bookId, int memberId, int rating, String review) {
        String result = loanRepository.leaveReview(bookId, memberId, rating, review);
        if (result == null) {
            throw new BookNotFoundException(bookId);
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
