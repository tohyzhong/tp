package cpp.logic.commands.classgroup;

import java.util.List;
import java.util.Objects;

import cpp.commons.core.index.Index;
import cpp.commons.util.ToStringBuilder;
import cpp.logic.commands.Command;
import cpp.logic.commands.CommandResult;
import cpp.logic.commands.exceptions.CommandException;
import cpp.logic.parser.CliSyntax;
import cpp.model.Model;
import cpp.model.classgroup.ClassGroupName;

/**
 * Unallocates a contact from a class group by their displayed indices.
 */
public class UnallocateClassGroupCommand extends Command {

    public static final String COMMAND_WORD = "unallocclass";

    public static final String MESSAGE_USAGE = UnallocateClassGroupCommand.COMMAND_WORD
            + ": Unallocates a contact from a class group. "
            + "Parameters: "
            + CliSyntax.PREFIX_CLASS + "CLASS NAME "
            + CliSyntax.PREFIX_CONTACT + "CONTACT INDICES...\n"
            + "Example: " + UnallocateClassGroupCommand.COMMAND_WORD + " "
            + CliSyntax.PREFIX_CLASS + "CS2103T10 "
            + CliSyntax.PREFIX_CONTACT + "1 2 3";

    private final ClassGroupName classGroupName;
    private final List<Index> contactIndices;

    /**
     * Creates an UnallocateClassGroupCommand with the specified class group name
     * and contact indices.
     */
    public UnallocateClassGroupCommand(ClassGroupName classGroupName, List<Index> contactIndices) {
        Objects.requireNonNull(classGroupName);
        Objects.requireNonNull(contactIndices);
        this.classGroupName = classGroupName;
        this.contactIndices = contactIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Implementation in progress");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnallocateClassGroupCommand)) {
            return false;
        }
        UnallocateClassGroupCommand o = (UnallocateClassGroupCommand) other;
        return this.classGroupName.equals(o.classGroupName)
                && this.contactIndices.equals(o.contactIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("classGroupName", this.classGroupName)
                .add("contactIndices", this.contactIndices)
                .toString();
    }
}
