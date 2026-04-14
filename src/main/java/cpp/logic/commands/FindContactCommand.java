package cpp.logic.commands;

import java.util.Objects;

import cpp.commons.util.ToStringBuilder;
import cpp.logic.Messages;
import cpp.logic.parser.CliSyntax;
import cpp.model.Model;
import cpp.model.contact.ContactSearchPredicate;

/**
 * Finds and lists all contacts in address book using the {@code findcontact}
 * ({@code findct}) command.
 *
 * Supports three search modes:
 *
 * - Name search: finds contacts whose names contain the specified string
 * (case-insensitive).
 * - Phone search: finds contacts whose phone number contains the specified
 * value.
 * - Email search: finds contacts whose email address contains the specified
 * value (case-insensitive).
 */
public class FindContactCommand extends Command {

    public static final String COMMAND_WORD = "findcontact";
    public static final String COMMAND_WORD_ALIAS = "findct";

    public static final String MESSAGE_USAGE = FindContactCommand.COMMAND_WORD
            + ": Finds all contacts whose names, phones, or emails contain the specified search string "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Alias: " + FindContactCommand.COMMAND_WORD_ALIAS + "\n"
            + "Parameters: ["
            + CliSyntax.PREFIX_NAME + "CONTACT_NAME_SEARCH_STRING] ["
            + CliSyntax.PREFIX_PHONE + "PHONE_NUMBER_SEARCH_STRING] ["
            + CliSyntax.PREFIX_EMAIL + "EMAIL_SEARCH_STRING]\n"
            + "Exactly one of ["
            + CliSyntax.PREFIX_NAME + "CONTACT_NAME_SEARCH_STRING], ["
            + CliSyntax.PREFIX_PHONE + "PHONE_NUMBER_SEARCH_STRING], or ["
            + CliSyntax.PREFIX_EMAIL + "EMAIL_SEARCH_STRING] must be provided.\n"
            + "Example: " + FindContactCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "tony tan\n"
            + "Example: " + FindContactCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_PHONE + "91234567\n"
            + "Example: " + FindContactCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_EMAIL + "alice@gmail.com";

    private final ContactSearchPredicate predicate;

    public FindContactCommand(ContactSearchPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        Objects.requireNonNull(model);
        model.updateFilteredContactList(this.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getFilteredContactList().size()),
                CommandResult.ListView.CONTACTS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindContactCommand)) {
            return false;
        }

        FindContactCommand otherFindContactCommand = (FindContactCommand) other;
        return this.predicate.equals(otherFindContactCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", this.predicate)
                .toString();
    }
}
