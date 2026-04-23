package exceptions;

public class InvalidMemberStatusException extends LibraryException {
    public InvalidMemberStatusException(int memberId) {
        super("You're not allowed to borrow books. Invalid status.");
    }
}
