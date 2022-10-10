package seedu.rc4hdb.logic.parser;

import static seedu.rc4hdb.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.EMAIL_DESC_AMY;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.INVALID_NAME_DESC;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.INVALID_PHONE_DESC;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.INVALID_TAG_DESC;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.NAME_DESC_AMY;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.PHONE_DESC_AMY;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.PHONE_DESC_BOB;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.TAG_DESC_FRIEND;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.VALID_EMAIL_AMY;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.VALID_NAME_AMY;
import static seedu.rc4hdb.logic.commands.modelcommands.ModelCommandTestUtil.VALID_PHONE_AMY;
import static seedu.rc4hdb.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.rc4hdb.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.rc4hdb.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.rc4hdb.logic.commands.modelcommands.FilterCommand;
import seedu.rc4hdb.logic.commands.modelcommands.FilterCommand.FilterPersonDescriptor;
import seedu.rc4hdb.logic.parser.commandparsers.FilterCommandParser;
import seedu.rc4hdb.model.person.Address;
import seedu.rc4hdb.model.person.Email;
import seedu.rc4hdb.model.person.Name;
import seedu.rc4hdb.model.person.Phone;
import seedu.rc4hdb.model.tag.Tag;
import seedu.rc4hdb.testutil.FilterPersonDescriptorBuilder;

public class FilterCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "filter", FilterCommand.MESSAGE_NOT_FILTERED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "something else", FilterCommand.MESSAGE_NOT_FILTERED);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "l/ string", FilterCommand.MESSAGE_NOT_FILTERED);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being Filtered,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY;

        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder().withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY)).withEmail(new Email(VALID_EMAIL_AMY))
                .withAddress(new Address(VALID_ADDRESS_AMY)).build();
        FilterCommand expectedCommand = new FilterCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = PHONE_DESC_AMY + EMAIL_DESC_AMY;

        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder().withPhone(new Phone(VALID_PHONE_AMY))
                .withEmail(new Email(VALID_EMAIL_AMY)).build();
        FilterCommand expectedCommand = new FilterCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}