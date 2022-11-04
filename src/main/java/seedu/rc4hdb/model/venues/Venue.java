package seedu.rc4hdb.model.venues;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import seedu.rc4hdb.model.venues.booking.Booking;
import seedu.rc4hdb.model.venues.booking.BookingDescriptor;
import seedu.rc4hdb.model.venues.booking.exceptions.BookingClashesException;
import seedu.rc4hdb.model.venues.booking.exceptions.BookingNotFoundException;
import seedu.rc4hdb.model.venues.booking.fields.BookingField;

/**
 * Represents a venue in RC4 that can be booked by residents.
 */
public class Venue implements BookingField {

    public static final String IDENTIFIER = "Venue";

    public static final String MESSAGE_CONSTRAINTS =
            "Venue should only consist of: 'meeting' ";

    private List<Booking> bookings = new ArrayList<>();
    private final VenueName venueName;

    /**
     * Constructor for a Venue instance.
     * @param venueName The name of the venue as specified by the venue book.
     */
    public Venue(VenueName venueName) {
        this.venueName = venueName;
    }

    /**
     * Constructs a Venue with pre-existing bookings.
     * @param bookings The list of pre-existing bookings.
     * @param venueName The name of the venue as specified by the venue book.
     */
    public Venue(List<Booking> bookings, VenueName venueName) {
        this(venueName);
        this.bookings = bookings;
    }

    /**
     * Removes expired bookings from the list of bookings.
     */
    public void clearExpiredBookings() {
        this.bookings = FXCollections.observableArrayList(this.bookings.stream()
                .filter(b -> !b.hasExpired())
                .collect(Collectors.toList()));
    }

    /**
     * Adds booking to the list of bookings.
     *
     * @param booking The booking to be added.
     * @throws BookingClashesException if the booking clashes with an existing booking.
     */
    public void addBooking(Booking booking) throws BookingClashesException {
        if (hasClashes(booking)) {
            throw new BookingClashesException();
        }
        this.bookings.add(booking);
    }

    /**
     * Removes the booking that matches the {@code bookingDescriptor}, if it exists.
     * @param bookingDescriptor The booking descriptor to match against.
     * @throws BookingNotFoundException if there is no such booking with the same booked period and day in the list.
     */
    public void removeBooking(BookingDescriptor bookingDescriptor) throws BookingNotFoundException {
        boolean isRemoved = this.bookings.removeIf(booking -> booking.isSameBooking(bookingDescriptor));
        if (!isRemoved) {
            throw new BookingNotFoundException();
        }
    }

    /**
     * Checks whether a given booking has clashes with current bookings.
     *
     * @param booking The given booking to check against.
     * @return whether the given booking clashes with current bookings.
     */
    public boolean hasClashes(Booking booking) {
        requireNonNull(booking);
        for (Booking b : this.bookings) {
            if (b.clashesWith(booking)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the venues are the same.
     */
    public boolean isSameVenue(Venue other) {
        return this.venueName.isSameVenueName(other.venueName);
    }

    public boolean isSameVenue(VenueName otherVenueName) {
        return this.venueName.isSameVenueName(otherVenueName);
    }

    //==================== Start of getters =================================

    //@@author nealetham
    public List<Booking> getReadOnlyBookings() {
        clearExpiredBookings();
        return Collections.unmodifiableList(bookings);
    }

    public VenueName getVenueName() {
        return venueName;
    }

    //==================== End of getters ====================================

    @Override
    public String toString() {
        return this.venueName.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Venue
                && (this.venueName.equals(((Venue) other).venueName)
                && (this.bookings.equals(((Venue) other).bookings))));
    }

    // Todo: Populate venue table with the venues specified in the .txt file and hook up class with storage
}
