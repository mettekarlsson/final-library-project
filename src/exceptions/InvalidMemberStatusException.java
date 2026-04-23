package exceptions;

public class InvalidMemberStatusException extends LibraryException {
    public InvalidMemberStatusException(int memberId) {
        super("Member #" + memberId + " is not allowed to borrow books. Invalid status.");
    }
}
