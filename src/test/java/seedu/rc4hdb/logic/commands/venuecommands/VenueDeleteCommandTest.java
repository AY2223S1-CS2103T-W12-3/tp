package seedu.rc4hdb.logic.commands.venuecommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.rc4hdb.logic.commands.ModelCommandTestUtil.assertCommandFailure;
import static seedu.rc4hdb.logic.commands.ModelCommandTestUtil.assertCommandSuccess;
import static seedu.rc4hdb.logic.commands.ModelCommandTestUtil.showResidentAtIndex;
import static seedu.rc4hdb.testutil.TypicalIndexes.INDEX_FIRST_RESIDENT;
import static seedu.rc4hdb.testutil.TypicalIndexes.INDEX_SECOND_RESIDENT;
import static seedu.rc4hdb.testutil.TypicalResidents.getTypicalResidentBook;
import static seedu.rc4hdb.testutil.TypicalVenues.getTypicalVenueBook;

import org.junit.jupiter.api.Test;

import seedu.rc4hdb.commons.core.Messages;
import seedu.rc4hdb.commons.core.index.Index;
import seedu.rc4hdb.logic.commands.residentcommands.DeleteCommand;
import seedu.rc4hdb.model.Model;
import seedu.rc4hdb.model.ModelManager;
import seedu.rc4hdb.model.ResidentBook;
import seedu.rc4hdb.model.UserPrefs;
import seedu.rc4hdb.model.VenueBook;
import seedu.rc4hdb.model.resident.Resident;
import seedu.rc4hdb.testutil.TypicalVenues;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class VenueDeleteCommandTest {

    private Model model = new ModelManager(new ResidentBook(), getTypicalVenueBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Venue residentToDelete = model.getVenueBook().(INDEX_FIRST_RESIDENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESIDENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RESIDENT_SUCCESS, residentToDelete);

        ModelManager expectedModel = new ModelManager(model.getVenueBook(), new VenueBook(), new UserPrefs());
        expectedModel.deleteVenue(residentToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVenueList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RESIDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showVenueAtIndex(model, INDEX_FIRST_RESIDENT);

        Venue residentToDelete = model.getFilteredVenueList().get(INDEX_FIRST_RESIDENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESIDENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_Venue_SUCCESS, residentToDelete);

        Model expectedModel = new ModelManager(model.getVenueBook(), new VenueBook(), new UserPrefs());
        expectedModel.deleteResident(residentToDelete);
        showNoResident(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showResidentAtIndex(model, INDEX_FIRST_RESIDENT);

        Index outOfBoundIndex = INDEX_SECOND_RESIDENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getResidentBook().getResidentList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RESIDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_RESIDENT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_RESIDENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_RESIDENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoResident(Model model) {
        model.updateFilteredResidentList(p -> false);

        assertTrue(model.getFilteredResidentList().isEmpty());
    }
}
