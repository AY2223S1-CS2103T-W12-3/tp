package seedu.rc4hdb.model;

import static java.util.Objects.requireNonNull;
import static seedu.rc4hdb.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.rc4hdb.commons.core.GuiSettings;
import seedu.rc4hdb.commons.core.LogsCenter;
import seedu.rc4hdb.model.resident.Resident;
import seedu.rc4hdb.model.venues.Venue;
import seedu.rc4hdb.model.venues.VenueName;
import seedu.rc4hdb.model.venues.booking.Booking;
import seedu.rc4hdb.model.venues.booking.exceptions.BookingClashesException;
import seedu.rc4hdb.model.venues.booking.exceptions.BookingNotFoundException;
import seedu.rc4hdb.model.venues.booking.fields.Day;
import seedu.rc4hdb.model.venues.booking.fields.HourPeriod;
import seedu.rc4hdb.model.venues.exceptions.VenueNotFoundException;

/**
 * Represents the in-memory model of the resident book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ResidentBook residentBook;
    private final VenueBook venueBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Resident> filteredResidents;

    private final ObservableList<String> observableFieldList;
    private final ObservableList<Venue> observableVenueList;


    /**
     * Initializes a ModelManager with the given residentBook and userPrefs.
     */
    public ModelManager(ReadOnlyResidentBook residentBook, ReadOnlyVenueBook venueBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(residentBook, venueBook, userPrefs);

        logger.fine("Initializing with resident book: " + residentBook + ", user prefs " + userPrefs
                + ", venue book: " + venueBook);
        this.residentBook = new ResidentBook(residentBook);
        this.venueBook = new VenueBook(venueBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredResidents = new FilteredList<>(this.residentBook.getResidentList());

        this.observableFieldList = FXCollections.observableArrayList();
        this.observableVenueList = venueBook.getVenueList();
    }

    public ModelManager() {
        this(new ResidentBook(), new VenueBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getUserPrefDataFilePath() {
        return userPrefs.getDataStorageFilePath();
    }

    @Override
    public void setUserPrefDataFilePath(Path residentBookFilePath) {
        requireNonNull(residentBookFilePath);
        userPrefs.setDataStorageFilePath(residentBookFilePath);
    }

    //=========== ResidentBook ===============================================================================

    public void setResidentBook(ReadOnlyResidentBook residentBook) {
        this.residentBook.resetData(residentBook);
    }

    @Override
    public ReadOnlyResidentBook getResidentBook() {
        return residentBook;
    }

    @Override
    public boolean hasResident(Resident person) {
        requireNonNull(person);
        return residentBook.hasResident(person);
    }

    @Override
    public void deleteResident(Resident target) {
        residentBook.removeResident(target);
    }

    @Override
    public void addResident(Resident person) {
        residentBook.addResident(person);
        updateFilteredResidentList(PREDICATE_SHOW_ALL_RESIDENTS);
    }

    @Override
    public void setResident(Resident target, Resident editedResident) {
        requireAllNonNull(target, editedResident);
        residentBook.setResident(target, editedResident);
    }

    //=========== Filtered Resident List Accessors ===========================================================

    /**
     * Returns an unmodifiable view of the list of {@code Resident} backed by the internal list of
     * {@code versionedResidentBook}
     */
    @Override
    public ObservableList<Resident> getFilteredResidentList() {
        return filteredResidents;
    }

    @Override
    public void updateFilteredResidentList(Predicate<Resident> predicate) {
        requireNonNull(predicate);
        filteredResidents.setPredicate(predicate);
    }

    //=========== Venue Book ==================================================================================

    public void setVenueBook(ReadOnlyVenueBook venueBook) {
        this.venueBook.resetData(venueBook);
    }

    @Override
    public ReadOnlyVenueBook getVenueBook() {
        return venueBook;
    }

    @Override
    public boolean hasVenue(Venue venue) {
        requireNonNull(venue);
        return venueBook.hasVenue(venue);
    }

    @Override
    public void deleteVenue(Venue target) {
        requireNonNull(target);
        venueBook.removeVenue(target);
    }

    @Override
    public void addVenue(Venue venue) {
        requireNonNull(venue);
        venueBook.addVenue(venue);
    }

    @Override
    public void addBooking(VenueName venueName, Booking booking)
            throws VenueNotFoundException, BookingClashesException {
        requireAllNonNull(venueName, booking);
        venueBook.addBooking(venueName, booking);
    }

    @Override
    public void removeBooking(VenueName venueName, HourPeriod bookedPeriod, Day bookedDay)
            throws VenueNotFoundException, BookingNotFoundException {
        requireAllNonNull(venueName, bookedPeriod, bookedDay);
        venueBook.removeBooking(venueName, bookedPeriod, bookedDay);
    }

    //=========== End of venue book methods =============================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return residentBook.equals(other.residentBook)
                && venueBook.equals(other.venueBook)
                && userPrefs.equals(other.userPrefs)
                && filteredResidents.equals(other.filteredResidents);
    }

    //=========== Observable Field List Accessors =============================================================

    @Override
    public ObservableList<String> getObservableFields() {
        return this.observableFieldList;
    }

    @Override
    public void setObservableFields(List<String> modifiableFields) {
        this.observableFieldList.setAll(modifiableFields);
    }

    //=========== Observable Venue List Accessors =============================================================

    @Override
    public ObservableList<Venue> getObservableVenues() {
        return this.observableVenueList;
    }

    @Override
    public void setObservableVenues(List<Venue> modifiableFields) {
        this.observableVenueList.setAll(modifiableFields);
    }


}
