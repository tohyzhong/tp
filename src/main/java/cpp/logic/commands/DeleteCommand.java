package cpp.logic.commands;

/**
 * Represents a delete command with hidden internal logic and the ability to be executed.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a contact identified by index, or an assignment identified by name.\n"
            + "Parameters: ct/INDEX (must be a positive integer) or ass/ASSIGNMENT_NAME\n"
            + "Examples: " + COMMAND_WORD + " ct/1, " + COMMAND_WORD + " ass/Assignment 1";

}
