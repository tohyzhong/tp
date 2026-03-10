package cpp.logic.parser;

import org.junit.jupiter.api.Test;

import cpp.logic.commands.ListCommand;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(this.parser, "     ", ListCommand.MESSAGE_TAB_EMPTY);
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand = new ListCommand("contacts");
        CommandParserTestUtil.assertParseSuccess(this.parser, "contacts", expectedListCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(this.parser, " \n contacts \n \t ", expectedListCommand);
    }
}
