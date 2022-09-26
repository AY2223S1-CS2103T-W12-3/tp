package seedu.address.logic.commands.importcommands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_IO_EXCEPTION;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Encapsulates a command that imports data from a .csv file.
 */
public abstract class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    protected static final String MESSAGE_SUCCESS = "File import successful!";

    private static final Pattern CSV_FORMAT = Pattern.compile("(?<name>.+),(?<phoneNo>.+),(?<email>.+),(?<room>.+),"
            + "(?<gender>.+),(?<house>.+),(?<matricNo>.+),(?<tags>.*)");

    protected Path filePath;

    public ImportCommand(String filePathString) {
        filePath = Paths.get("data", filePathString + ".csv");
    }

    protected UniquePersonList readFile() throws CommandException {
        checkValidFile();

        try {
            Scanner rawStringStream = new Scanner(FileUtil.readFromFile(filePath));
            UniquePersonList uniquePersonList = new UniquePersonList();
            while (rawStringStream.hasNext()) {
                uniquePersonList.add(personFromString(rawStringStream.nextLine()));
            }
            return uniquePersonList;
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, filePath.toString()));
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_INVALID_FILE_FORMAT);
        }
    }

    private Person personFromString(String personString) throws ParseException {
        Matcher matcher = CSV_FORMAT.matcher(personString);
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_INVALID_FILE_FORMAT);
        }

        final Name name = ParserUtil.parseName(matcher.group("name"));
        final Phone phoneNo = ParserUtil.parsePhone(matcher.group("phoneNo"));
        final Email email = ParserUtil.parseEmail(matcher.group("email"));
        final Address room = ParserUtil.parseAddress(matcher.group("room"));
        final String gender = matcher.group("gender");
        final String house = matcher.group("house");
        final String matricNo = matcher.group("matricNo");
        final Tag tags = ParserUtil.parseTag(matcher.group("tags"));
        return new Person(name, phoneNo, email, room, Set.of(tags));
    }

    private void checkValidFile() throws CommandException {
        if (!FileUtil.isFileExists(filePath)) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE_NAME, filePath));
        }
    }

}
