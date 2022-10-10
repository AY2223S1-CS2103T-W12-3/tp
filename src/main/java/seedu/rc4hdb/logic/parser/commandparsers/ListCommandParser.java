package seedu.rc4hdb.logic.parser.commandparsers;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.rc4hdb.logic.commands.modelcommands.ListCommand;
import seedu.rc4hdb.logic.parser.Parser;
import seedu.rc4hdb.logic.parser.exceptions.ParseException;
import seedu.rc4hdb.model.person.Fields;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    public static final char INCLUDE_SPECIFIER = 'i';
    public static final char EXCLUDE_SPECIFIER = 'e';

    public static final String NO_SPECIFIER_MESSAGE = "Please include a specifier [/i] or [/e]"
            + " followed by the fields to include or exclude";

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isEmpty()) {
            return new ListCommand();
        }
        if (hasInvalidSpecifier(args)) {
            throw new ParseException(NO_SPECIFIER_MESSAGE);
        }

        // Process global list of fields into lowercase list first
        List<String> allFields = Fields.FIELDS.stream().map(String::toLowerCase).collect(Collectors.toList());

        // Create one list for each specifier
        List<String> fieldsToInclude = new ArrayList<>(allFields);
        List<String> fieldsToExclude = new ArrayList<>();

        String[] specifiedFields = getSpecifiedFields(args);

        populateFieldLists(specifiedFields, fieldsToInclude, fieldsToExclude);

        if (getListSpecifier(args) == INCLUDE_SPECIFIER) {
            return new ListCommand(fieldsToInclude);
        } else {
            return new ListCommand(fieldsToExclude);
        }
    }

    private boolean hasInvalidSpecifier(String args) {
        if (hasInvalidSpecifierLength(args)) {
            return true;
        }
        char specifier = getListSpecifier(args);
        return specifier != INCLUDE_SPECIFIER && specifier != EXCLUDE_SPECIFIER;
    }

    private boolean hasInvalidSpecifierLength(String args) {
        int nextIndex = args.indexOf("/") + 2;
        if (nextIndex < args.length()) {
            return args.charAt(nextIndex) != ' ';
        }
        return false;
    }

    private char getListSpecifier(String args) {
        return args.charAt(args.indexOf("/") + 1);
    }

    private String[] getSpecifiedFields(String args) {
        String fieldsString = args.toLowerCase().substring(args.indexOf("/") + 2).trim();
        return fieldsString.split(" ");
    }

    private void populateFieldLists(String[] specifiedFields,
                                    List<String> fieldsToInclude, List<String> fieldsToExclude) {
        for (String field : specifiedFields) {
            fieldsToInclude.remove(field);
            fieldsToExclude.add(field);
        }
    }
}
