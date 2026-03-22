package cpp.model.assignment.exceptions;

public class ContactAssignmentAlreadyGradedException extends RuntimeException {
    public ContactAssignmentAlreadyGradedException() {
        super("This assignment is already graded for the contact");
    }
}
