package exceptions;

public class LoanReturnedException extends LibraryException {
    public LoanReturnedException(int loanId) {
        super("Loan with ID #" + loanId + " is already returned.");
    }
}
