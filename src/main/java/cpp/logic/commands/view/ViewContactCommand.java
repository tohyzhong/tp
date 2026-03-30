package cpp.logic.commands.view;

import java.util.List;
import java.util.Objects;

import cpp.commons.core.index.Index;
import cpp.commons.util.ToStringBuilder;
import cpp.logic.Messages;
import cpp.logic.commands.CommandResult;
import cpp.logic.commands.exceptions.CommandException;
import cpp.model.Model;
import cpp.model.ReadOnlyAddressBook;
import cpp.model.assignment.ContactAssignment;
import cpp.model.contact.Contact;

/**
 * Views a contact identified using its displayed index from the address book.
 * Displays full detailed information including name, phone, email, classes, and
 * assignments.
 */
public class ViewContactCommand extends ViewCommand {

    public static final String MESSAGE_VIEW_CONTACT_SUCCESS = "Viewed Contact:\n%1$s";

    private final Index targetIndex;

    public ViewContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (this.targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToView = lastShownList.get(this.targetIndex.getZeroBased());

        /**
         * ReadOnlyAddressBook addressBook = model.getAddressBook();
         * List<ClassGroup> relevantClassGroups = addressBook.getClassGroupList()
         * .stream()
         * .filter(classGroup ->
         * classGroup.getContactIdSet().contains(contactToView.getId()))
         * .toList();
         * List<ContactAssignment> relevantContactAssignments =
         * model.getContactAssignmentsForContact(contactToView);
         * 
         * String contactDetails = contactToView.toString() + "\nClasses:\n"
         * + (relevantClassGroups.isEmpty() ? "None"
         * : relevantClassGroups.stream()
         * .map(cg -> Messages.format(cg))
         * .collect(Collectors.joining("\n")))
         * + "\nAssignments:\n"
         * + (relevantContactAssignments.isEmpty() ? "None"
         * : relevantContactAssignments.stream()
         * .map(ca -> this.formatContactAssignment(addressBook, ca))
         * .collect(Collectors.joining("\n")));
         */

        model.viewContact(contactToView);

        return new CommandResult(
                String.format(ViewAssignmentCommand.MESSAGE_SUCCESS, Messages.format(
                        contactToView)),
                CommandResult.ViewType.CONTACT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewContactCommand)) {
            return false;
        }

        ViewContactCommand otherViewContactCommand = (ViewContactCommand) other;
        return this.targetIndex.equals(otherViewContactCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", this.targetIndex)
                .toString();
    }

    private String formatContactAssignment(ReadOnlyAddressBook addressBook, ContactAssignment contactAssignment) {
        String assignmentText = addressBook.getAssignmentList().stream()
                .filter(assignment -> assignment.getId().equals(contactAssignment.getAssignmentId()))
                .findFirst()
                .map(Messages::format)
                .orElse(contactAssignment.getAssignmentId());

        return assignmentText + "; Submitted: " + contactAssignment.isSubmitted()
                + "; Graded: " + contactAssignment.isGraded();
    }
}
