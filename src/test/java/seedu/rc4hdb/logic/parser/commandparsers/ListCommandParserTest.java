package seedu.rc4hdb.logic.parser.commandparsers;

import static seedu.rc4hdb.logic.commands.modelcommands.ColumnManipulatorCommand.generateComplementListFrom;
import seedu.rc4hdb.logic.commands.modelcommands.ListCommand;
import static seedu.rc4hdb.logic.commands.modelcommands.ListCommand.EXCLUDE_SPECIFIER;
import static seedu.rc4hdb.logic.commands.modelcommands.ListCommand.INCLUDE_SPECIFIER;
import static seedu.rc4hdb.logic.parser.commandparsers.ColumnManipulatorCommandParser.*;
import static seedu.rc4hdb.logic.parser.commandparsers.CommandParserTestUtil.assertParseFailure;
import static seedu.rc4hdb.logic.parser.commandparsers.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static seedu.rc4hdb.logic.parser.commandparsers.ListCommandParser.INTENDED_SPECIFIER_USAGE_MESSAGE;
import static seedu.rc4hdb.testutil.TypicalColumnManipulatorInputs.*;
import static seedu.rc4hdb.testutil.TypicalFieldLists.*;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();
    public static final String INCLUDE_SPECIFIER_PREFIX = INCLUDE_SPECIFIER + WHITESPACE;
    public static final String EXCLUDE_SPECIFIER_PREFIX = EXCLUDE_SPECIFIER + WHITESPACE;

    // Note that the inputs now are letters representing column names, rather than
    // verbose column names which was used when testing the command classes.

    // Default list command with no specifier returns ListCommand
    @Test
    public void parse_defaultListCommand_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand();
        assertParseSuccess(parser, EMPTY_STRING, expectedCommand);
    }

    // Include valid letters in argument string returns ListCommand
    @Test
    public void parse_includeValidLetters_returnsListCommand() {
        List<String> fieldsToHide = generateComplementListFrom(VALID_FIELDS);
        ListCommand expectedCommand = new ListCommand(VALID_FIELDS, fieldsToHide);
        assertParseSuccess(parser, INCLUDE_SPECIFIER_PREFIX + VALID_LETTERS, expectedCommand);
    }

    // Exclude valid letters in argument string returns ListCommand
    @Test
    public void parse_excludeValidLetters_returnsListCommand() {
        List<String> fieldsToShow = generateComplementListFrom(VALID_FIELDS);
        ListCommand expectedCommand = new ListCommand(fieldsToShow, VALID_FIELDS);
        assertParseSuccess(parser, EXCLUDE_SPECIFIER_PREFIX + VALID_LETTERS, expectedCommand);
    }

    // Include duplicate letters in argument string returns ListCommand
    @Test
    public void parse_includeDuplicateLetters_returnsListCommand() {
        List<String> fieldsToHide = generateComplementListFrom(DUPLICATE_FIELDS);
        ListCommand expectedCommand = new ListCommand(DUPLICATE_FIELDS, fieldsToHide);
        assertParseSuccess(parser, INCLUDE_SPECIFIER_PREFIX + DUPLICATE_LETTERS, expectedCommand);
    }

    // Exclude duplicate letters in argument string returns ListCommand
    @Test
    public void parse_excludeDuplicateLetters_returnsListCommand() {
        List<String> fieldsToShow = generateComplementListFrom(DUPLICATE_FIELDS);
        ListCommand expectedCommand = new ListCommand(fieldsToShow, DUPLICATE_FIELDS);
        assertParseSuccess(parser, EXCLUDE_SPECIFIER_PREFIX + DUPLICATE_LETTERS, expectedCommand);
    }

    // Include mixed case letters in argument string returns ListCommand
    @Test
    public void parse_includeMixedCaseLetters_returnsListCommand() {
        List<String> fieldsToHide = generateComplementListFrom(VALID_FIELDS);
        ListCommand expectedCommand = new ListCommand(VALID_FIELDS, fieldsToHide);
        assertParseSuccess(parser, INCLUDE_SPECIFIER_PREFIX + MIXED_CASE_LETTERS, expectedCommand);
    }

    // Exclude mixed case letters in argument string returns ListCommand
    @Test
    public void parse_excludeMixedCaseLetters_returnsListCommand() {
        List<String> fieldsToShow = generateComplementListFrom(VALID_FIELDS);
        ListCommand expectedCommand = new ListCommand(fieldsToShow, VALID_FIELDS);
        assertParseSuccess(parser, EXCLUDE_SPECIFIER_PREFIX + MIXED_CASE_LETTERS, expectedCommand);
    }

    // Include all letters returns ListCommand (but should throw CommandException during execution)
    @Test
    public void parse_includeAllPossibleLetters_returnsListCommand() {
        List<String> fieldsToHide = generateComplementListFrom(ALL_VALID_FIELDS);
        ListCommand expectedCommand = new ListCommand(ALL_VALID_FIELDS, fieldsToHide);
        assertParseSuccess(parser, INCLUDE_SPECIFIER_PREFIX + ALL_VALID_LETTERS, expectedCommand);
    }

    // Exclude all letters returns ListCommand (but should throw CommandException during execution)
    @Test
    public void parse_excludeAllPossibleLetters_returnsListCommand() {
        List<String> fieldsToShow = generateComplementListFrom(ALL_VALID_FIELDS);
        ListCommand expectedCommand = new ListCommand(fieldsToShow, ALL_VALID_FIELDS);
        assertParseSuccess(parser, EXCLUDE_SPECIFIER_PREFIX + ALL_VALID_LETTERS, expectedCommand);
    }

    // Include empty argument string throws exception
    @Test
    public void parse_includeEmptyArgumentString_throwsParseException() {
        assertParseFailure(parser, INCLUDE_SPECIFIER_PREFIX + EMPTY_STRING, INTENDED_SPECIFIER_USAGE_MESSAGE);
    }

    // Exclude empty argument string throws exception
    @Test
    public void parse_excludeEmptyArgumentString_throwsParseException() {
        assertParseFailure(parser, EXCLUDE_SPECIFIER_PREFIX + EMPTY_STRING, INTENDED_SPECIFIER_USAGE_MESSAGE);
    }

    // Include verbose column names in argument string throws exception
    @Test
    public void parse_includeVerboseColumnNames_throwsParseException() {
        String validColumns = new ArrayList<>(VALID_FIELDS).stream().reduce(EMPTY_STRING, (x, y) -> x + WHITESPACE + y);
        assertParseFailure(parser, INCLUDE_SPECIFIER_PREFIX + validColumns, INVALID_FIELDS_ENTERED);
    }

    // Exclude verbose column names in argument string throws exception
    @Test
    public void parse_excludeVerboseColumnNames_throwsParseException() {
        String validColumns = new ArrayList<>(VALID_FIELDS).stream().reduce(EMPTY_STRING, (x, y) -> x + WHITESPACE + y);
        assertParseFailure(parser, EXCLUDE_SPECIFIER_PREFIX + validColumns, INVALID_FIELDS_ENTERED);
    }

    // Include invalid letters in argument string throws exception
    @Test
    public void parse_includeInvalidLetters_throwsParseException() {
        assertParseFailure(parser, INCLUDE_SPECIFIER_PREFIX + INVALID_LETTERS, INVALID_FIELDS_ENTERED);
    }

    // Exclude invalid letters in argument string throws exception
    @Test
    public void parse_excludeInvalidLetters_throwsParseException() {
        assertParseFailure(parser, EXCLUDE_SPECIFIER_PREFIX + INVALID_LETTERS, INVALID_FIELDS_ENTERED);
    }

}
