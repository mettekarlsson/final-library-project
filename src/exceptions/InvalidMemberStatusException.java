package exceptions;

public class InvalidMemberStatusException extends LibraryException {
    public InvalidMemberStatusException(int memberId) {
        super("Member with ID #" + memberId + "'s status is not active.");
    }
}
