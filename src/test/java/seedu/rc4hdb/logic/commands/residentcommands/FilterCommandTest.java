package seedu.rc4hdb.logic.commands.residentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.rc4hdb.commons.core.Messages.MESSAGE_RESIDENTS_LISTED_OVERVIEW;
import static seedu.rc4hdb.logic.commands.residentcommands.ModelCommandTestUtil.assertCommandSuccess;
import static seedu.rc4hdb.testutil.TypicalResidents.ALICE;
import static seedu.rc4hdb.testutil.TypicalResidents.AMY;
import static seedu.rc4hdb.testutil.TypicalResidents.BENSON;
import static seedu.rc4hdb.testutil.TypicalResidents.DANIEL;
import static seedu.rc4hdb.testutil.TypicalResidents.ELLE;
import static seedu.rc4hdb.testutil.TypicalResidents.FIONA;
import static seedu.rc4hdb.testutil.TypicalResidents.getTypicalResidentBook;
import static seedu.rc4hdb.testutil.TypicalSpecifiers.ALL_SPECIFIER;
import static seedu.rc4hdb.testutil.TypicalSpecifiers.ANY_SPECIFIER;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.rc4hdb.model.Model;
import seedu.rc4hdb.model.ModelManager;
import seedu.rc4hdb.model.UserPrefs;
import seedu.rc4hdb.model.VenueBook;
import seedu.rc4hdb.model.resident.ResidentDescriptor;
import seedu.rc4hdb.model.resident.predicates.AttributesMatchAllKeywordsPredicate;
import seedu.rc4hdb.model.resident.predicates.AttributesMatchAnyKeywordPredicate;
import seedu.rc4hdb.testutil.ResidentDescriptorBuilder;

public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalResidentBook(), new VenueBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalResidentBook(), new VenueBook(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredListForAll_success() {
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(new ResidentDescriptorBuilder(ALICE).build());
        FilterCommand command = new FilterCommand(new ResidentDescriptorBuilder(ALICE).build(), ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder().withName(ALICE.getName())
                .withPhone(ALICE.getPhone()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_nameSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder().withName(ALICE.getName()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_phoneSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder().withPhone(ALICE.getPhone()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_emailSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder().withEmail(ALICE.getEmail()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }


    @Test
    public void execute_matricNumberSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withMatricNumber(ALICE.getMatricNumber()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_houseSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withHouse(ALICE.getHouse()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 2);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, FIONA), model.getFilteredResidentList());
    }

    @Test
    public void execute_genderSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withGender(ALICE.getGender()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 3);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE, FIONA), model.getFilteredResidentList());
    }

    @Test
    public void execute_tagsSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withTags(ALICE.getTags()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 3);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredResidentList());
    }

    @Test
    public void execute_roomSpecifiedUnfilteredListForAll_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withRoom(ALICE.getRoom()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAllKeywordsPredicate predicate =
                new AttributesMatchAllKeywordsPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ALL_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredListForAny_success() {
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 5);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(new ResidentDescriptorBuilder(ALICE).build());
        FilterCommand command = new FilterCommand(new ResidentDescriptorBuilder(ALICE).build(), ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, ELLE, FIONA), model.getFilteredResidentList());
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder().withName(ALICE.getName())
                .withPhone(ALICE.getPhone()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), expectedModel.getFilteredResidentList());
    }

    @Test
    public void execute_nameSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder().withName(ALICE.getName()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_phoneSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder().withPhone(ALICE.getPhone()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_emailSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder().withEmail(ALICE.getEmail()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }


    @Test
    public void execute_matricNumberSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withMatricNumber(ALICE.getMatricNumber()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }

    @Test
    public void execute_houseSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withHouse(ALICE.getHouse()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 2);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, FIONA), model.getFilteredResidentList());
    }

    @Test
    public void execute_genderSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withGender(ALICE.getGender()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 3);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE, FIONA), model.getFilteredResidentList());
    }

    @Test
    public void execute_tagsSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withTags(ALICE.getTags()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 3);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredResidentList());
    }

    @Test
    public void execute_roomSpecifiedUnfilteredListForAny_success() {
        ResidentDescriptor descriptor = new ResidentDescriptorBuilder()
                .withRoom(ALICE.getRoom()).build();
        String expectedMessage = String.format(MESSAGE_RESIDENTS_LISTED_OVERVIEW, 1);
        AttributesMatchAnyKeywordPredicate predicate =
                new AttributesMatchAnyKeywordPredicate(descriptor);
        FilterCommand command = new FilterCommand(descriptor, ANY_SPECIFIER);
        expectedModel.updateFilteredResidentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredResidentList());
    }


    @Test
    public void equals() {
        ResidentDescriptor firstDescriptor = new ResidentDescriptorBuilder(AMY).build();
        ResidentDescriptor secondDescriptor = new ResidentDescriptorBuilder(ALICE).build();

        FilterCommand filterFirstCommand = new FilterCommand(firstDescriptor, ALL_SPECIFIER);
        FilterCommand filterSecondCommand = new FilterCommand(secondDescriptor, ALL_SPECIFIER);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstDescriptor, ALL_SPECIFIER);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

}
