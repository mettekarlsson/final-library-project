package exceptions;

public class LoanNotFoundException extends LibraryException {
    public LoanNotFoundException(int loanId) {
        super("Loan with ID #" + loanId + " not found.");
    }
}
