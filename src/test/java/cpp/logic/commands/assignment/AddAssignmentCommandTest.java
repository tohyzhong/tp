package cpp.logic.commands.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.logic.Messages;
import cpp.logic.commands.CommandResult;
import cpp.logic.commands.exceptions.CommandException;
import cpp.model.AddressBook;
import cpp.model.ReadOnlyAddressBook;
import cpp.model.assignment.Assignment;
import cpp.model.contact.Contact;
import cpp.testutil.Assert;
import cpp.testutil.AssignmentBuilder;
import cpp.testutil.ModelStub;
import cpp.testutil.TypicalAssignments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddAssignmentCommandTest {

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddAssignmentCommand(null, List.of()));
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAssignmentAdded modelStub = new ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = TypicalAssignments.ASSIGNMENT_ONE;

        CommandResult commandResult = new AddAssignmentCommand(validAssignment, List.of()).execute(modelStub);

        Assertions.assertEquals(String.format(AddAssignmentCommand.MESSAGE_SUCCESS, Messages.format(validAssignment)),
                commandResult.getFeedbackToUser());
        Assertions.assertEquals(1, modelStub.assignmentsAdded.size());
        Assertions.assertEquals(validAssignment, modelStub.assignmentsAdded.get(0));
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Assignment validAssignment = TypicalAssignments.ASSIGNMENT_ONE;
        AddAssignmentCommand addContactCommand = new AddAssignmentCommand(validAssignment, List.of());
        ModelStub modelStub = new ModelStubWithAssignment(validAssignment);

        Assert.assertThrows(CommandException.class, AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT,
                () -> addContactCommand.execute(modelStub));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Assignment assignment = TypicalAssignments.ASSIGNMENT_ONE;
        AddAssignmentCommand addContactCommand = new AddAssignmentCommand(assignment, List.of());
        AddAssignmentCommand addContactCommandCopy = new AddAssignmentCommand(assignment, List.of());

        // same object -> true
        Assertions.assertTrue(addContactCommand.equals(addContactCommand));

        // same values -> true
        Assertions.assertTrue(addContactCommand.equals(addContactCommandCopy));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Assignment assignment = TypicalAssignments.ASSIGNMENT_ONE;
        AddAssignmentCommand addContactCommand = new AddAssignmentCommand(assignment, List.of());

        // different types -> false
        Assertions.assertFalse(addContactCommand.equals(1));

        // null -> false
        Assertions.assertFalse(addContactCommand.equals(null));

        // different assignment -> false
        Assignment different = new AssignmentBuilder(TypicalAssignments.ASSIGNMENT_ONE)
                .withName("Different").build();
        AddAssignmentCommand differentCommand = new AddAssignmentCommand(different, List.of());
        Assertions.assertFalse(addContactCommand.equals(differentCommand));
    }

    @Test
    public void toString_typicalValue_correctOutput() {
        AddAssignmentCommand addContactCommand = new AddAssignmentCommand(TypicalAssignments.ASSIGNMENT_ONE, List.of());
        String expected = AddAssignmentCommand.class.getCanonicalName() + "{toAddAssignment="
                + TypicalAssignments.ASSIGNMENT_ONE
                + ", contactIndices=[]"
                + ", classGroupName=null"
                + "}";
        Assertions.assertEquals(expected, addContactCommand.toString());
    }

    /**
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithAssignment extends ModelStub {
        private final Assignment assignment;

        ModelStubWithAssignment(Assignment assignment) {
            Objects.requireNonNull(assignment);
            this.assignment = assignment;
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            Objects.requireNonNull(assignment);
            return this.assignment.getName().equals(assignment.getName());
        }
    }

    /**
     * A Model stub that always accept the assignment being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends ModelStub {
        final ArrayList<Assignment> assignmentsAdded = new ArrayList<>();

        @Override
        public boolean hasAssignment(Assignment assignment) {
            Objects.requireNonNull(assignment);
            return this.assignmentsAdded.stream().anyMatch(a -> a.getName().equals(assignment.getName()));
        }

        @Override
        public void addAssignment(Assignment assignment) {
            Objects.requireNonNull(assignment);
            this.assignmentsAdded.add(assignment);
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
