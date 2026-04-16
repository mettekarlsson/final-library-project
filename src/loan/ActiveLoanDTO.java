package loan;

import java.time.LocalDate;

public class ActiveLoanDTO {

    private int id;
    String bookTitle;
    String memberName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public ActiveLoanDTO(int id, String bookTitle, String memberName, LocalDate loanDate,
                         LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.memberName = memberName;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan-id: " + id +
                ", Book Title: '" + bookTitle + "'" +
                ", Member Name: '" + memberName + "'" +
                ", Loan Date: " + loanDate +
                ", Due Date: " + dueDate;
    }
}
