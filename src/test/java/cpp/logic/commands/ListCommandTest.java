package cpp.logic.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpp.model.Model;
import cpp.model.ModelManager;
import cpp.model.UserPrefs;
import cpp.testutil.TypicalContacts;
import cpp.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());
        this.expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandTestUtil.assertCommandSuccess(new ListCommand("contacts"), this.model, ListCommand.MESSAGE_SUCCESS,
                this.expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showContactAtIndex(this.model, TypicalIndexes.INDEX_FIRST_CONTACT);
        CommandTestUtil.assertCommandSuccess(new ListCommand("contacts"), this.model, ListCommand.MESSAGE_SUCCESS,
                this.expectedModel);
    }

    @Test
    public void execute_listAssignments_returnsAssignmentMessage() {
        CommandTestUtil.assertCommandSuccess(new ListCommand("assignments"), this.model,
                ListCommand.MESSAGE_ASSIGNMENTS,
                this.expectedModel);
    }

    @Test
    public void execute_listClasses_returnsClassesMessage() {
        CommandTestUtil.assertCommandSuccess(new ListCommand("classes"), this.model, ListCommand.MESSAGE_CLASSES,
                this.expectedModel);
    }

    @Test
    public void equals_sameTab_returnsTrue() {
        ListCommand command1 = new ListCommand("contacts");
        ListCommand command2 = new ListCommand("contacts");
        Assertions.assertEquals(command1, command2);
    }

    @Test
    public void equals_differentTab_returnsFalse() {
        ListCommand command1 = new ListCommand("contacts");
        ListCommand command2 = new ListCommand("assignments");
        Assertions.assertNotEquals(command1, command2);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        ListCommand command = new ListCommand("contacts");
        Assertions.assertEquals(command, command);
    }

    @Test
    public void equals_notListCommand_returnsFalse() {
        ListCommand command = new ListCommand("contacts");
        Assertions.assertNotEquals(command, new Object());
    }

    @Test
    public void equals_null_returnsFalse() {
        ListCommand command = new ListCommand("contacts");
        Assertions.assertNotEquals(command, null);
    }
}
