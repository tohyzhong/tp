package cpp.logic.commands.assignment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cpp.commons.core.index.Index;
import cpp.commons.util.ToStringBuilder;
import cpp.logic.commands.Command;
import cpp.logic.commands.CommandResult;
import cpp.logic.commands.exceptions.CommandException;
import cpp.logic.parser.CliSyntax;
import cpp.model.Model;
import cpp.model.assignment.AssignmentName;
import cpp.model.contact.Contact;

/**
 * Grades an assignment for a contact. The assignment must have been submitted
 * before it can be graded.
 */
public class GradeAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "gradeass";

    public static final String MESSAGE_USAGE = GradeAssignmentCommand.COMMAND_WORD
            + ": Grades an assignment for a contact with a score. "
            + "If no grading date is provided, the current date and time will be used.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_ASSIGNMENT + "ASSIGNMENT NAME "
            + CliSyntax.PREFIX_CONTACT + "CONTACT INDICES... "
            + CliSyntax.PREFIX_SCORE + "SCORE "
            + "[" + CliSyntax.PREFIX_DATETIME + "GRADING DATE]\n"
            + "Example: " + SubmitAssignmentCommand.COMMAND_WORD + " "
            + CliSyntax.PREFIX_ASSIGNMENT + "Assignment 1 "
            + CliSyntax.PREFIX_CONTACT + "1 2 3 "
            + CliSyntax.PREFIX_SCORE + "85 "
            + CliSyntax.PREFIX_DATETIME + "21-02-2026 23:50";

    public static final String MESSAGE_SUCCESS = """
            Graded assignment: %1$s on %2$s for %3$s contact(s) with score %4$.2f.
            Contacts graded: %5$s""";

    private final AssignmentName assignmentName;
    private final List<Index> contactIndices;
    private final Set<Contact> contactsToGrade = new HashSet<>();
    private final float score;

    private int gradedCount = 0;
    private int alreadyGradedCount = 0;
    private int notSubmittedOrNotAllocatedCount = 0;
    private StringBuilder gradedContacts = new StringBuilder();
    private StringBuilder alreadyGradedContacts = new StringBuilder();
    private StringBuilder notSubmittedOrNotAllocatedContacts = new StringBuilder();

    /**
     * Creates a GradeAssignmentCommand to grade the specified assignment for the
     * specified contacts with the given grade info.
     */
    public GradeAssignmentCommand(AssignmentName assignmentName, List<Index> contactIndices, float score) {
        this.assignmentName = assignmentName;
        this.contactIndices = new ArrayList<>(contactIndices);
        this.score = score;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);

        return new CommandResult(String.format(GradeAssignmentCommand.MESSAGE_SUCCESS, this.assignmentName,
                LocalDateTime.now(), this.contactsToGrade.size(), this.score, this.gradedContacts.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GradeAssignmentCommand)) {
            return false;
        }
        GradeAssignmentCommand o = (GradeAssignmentCommand) other;
        return this.assignmentName.equals(o.assignmentName)
                && this.contactIndices.equals(o.contactIndices)
                && Float.compare(this.score, o.score) == 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignmentName", this.assignmentName)
                .add("contactIndices", this.contactIndices)
                .add("score", this.score)
                .toString();
    }
}
