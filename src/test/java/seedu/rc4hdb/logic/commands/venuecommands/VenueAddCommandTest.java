package seedu.rc4hdb.logic.commands.venuecommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.rc4hdb.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.rc4hdb.logic.commands.CommandResult;
import seedu.rc4hdb.model.ModelStub;
import seedu.rc4hdb.model.ReadOnlyVenueBook;
import seedu.rc4hdb.model.VenueBook;
import seedu.rc4hdb.model.venues.Venue;
import seedu.rc4hdb.model.venues.VenueName;

/**
 * Unit tests for {@link VenueAddCommand}.
 */
public class VenueAddCommandTest {

    @Test
    public void constructor_nullVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VenueAddCommand(null));
    }

    @Test
    public void execute_venueAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVenueAdded modelStub = new ModelStubAcceptingVenueAdded();
        VenueName validVenueName = new VenueName("Location");
        Venue validVenue = new Venue(validVenueName);

        CommandResult commandResult = new VenueAddCommand(validVenueName).execute(modelStub);

        assertEquals(String.format(VenueAddCommand.MESSAGE_SUCCESS, validVenueName), commandResult.getFeedbackToUser());
        ArrayList<Venue> expectedArrayList = new ArrayList<>();
        expectedArrayList.add(validVenue);
        assertTrue(expectedArrayList.equals(modelStub.venuesAdded));
    }

    @Test
    public void equals() {
        VenueName diningHall = new VenueName("Dining Hall");
        VenueName mph = new VenueName("Multi Purpose Hall");
        VenueAddCommand addDiningHallCommand = new VenueAddCommand(diningHall);
        VenueAddCommand addMphCommand = new VenueAddCommand(mph);

        // same object -> returns true
        assertTrue(addDiningHallCommand.equals(addDiningHallCommand));

        // same values -> returns true
        VenueAddCommand addAliceCommandCopy = new VenueAddCommand(diningHall);
        assertTrue(addDiningHallCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addDiningHallCommand.equals(1));

        // null -> returns false
        assertFalse(addDiningHallCommand.equals(null));

        // different person -> returns false
        assertFalse(addDiningHallCommand.equals(addMphCommand));
    }

    //======================== Start of model stubs ===============================================

    /**
     * A Model stub that contains a single venue.
     */
    public static class ModelStubWithVenue extends ModelStub {
        private final Venue venue;

        /**
         * Constructs a model stub with a single venue.
         */
        public ModelStubWithVenue(Venue venue) {
            requireNonNull(venue);
            this.venue = venue;
        }

        @Override
        public boolean hasVenue(Venue venue) {
            requireNonNull(venue);
            return this.venue.isSameVenue(venue);
        }
    }

    /**
     * A Model stub that always accept the venue being added.
     */
    public static class ModelStubAcceptingVenueAdded extends ModelStub {
        public final ArrayList<Venue> venuesAdded = new ArrayList<>();

        @Override
        public boolean hasVenue(Venue venue) {
            requireNonNull(venue);
            return venuesAdded.stream().anyMatch(venue::isSameVenue);
        }

        @Override
        public void addVenue(Venue venue) {
            requireNonNull(venue);
            venuesAdded.add(venue);
        }

        @Override
        public ReadOnlyVenueBook getVenueBook() {
            return new VenueBook();
        }
    }

}
