package seedu.rc4hdb.ui;

import static seedu.rc4hdb.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.rc4hdb.model.venues.Venue;
import seedu.rc4hdb.model.venues.booking.Booking;

/**
 * Controller for the venue tab ui component.
 */
public class VenueTabView extends UiPart<Region> {

    private static final String FXML = "VenueTabView.fxml";

    private Label label;

    private BookingTableView bookingTableView;
    private VenueListView venueListView;

    @FXML
    private Label currentVenueName;
    @FXML
    private HBox venueListTableContainer;
    @FXML
    private StackPane bookingTableViewPlaceholder;
    @FXML
    private StackPane venueListViewPlaceholder;

    private ObservableList<Booking> bookings;

    /**
     * Constructor for a VenueTabView instance.
     * @param venueList The list of venues to be displayed.
     * @param bookingList The list of bookings to be displayed.
     */
    public VenueTabView(ObservableList<Venue> venueList, ObservableList<Booking> bookingList) {
        super(FXML);
        requireAllNonNull(venueList, bookingList);

        this.bookings = bookingList;
        bookingList.addListener((ListChangeListener<? super Booking>) c -> updateVenueName());
        fillInnerParts(venueList, bookingList);
    }

    void fillInnerParts(ObservableList<Venue> venues, ObservableList<Booking> bookings) {
        updateVenueName();
        bookingTableView = new BookingTableView(bookings);
        venueListView = new VenueListView(venues);

        bookingTableViewPlaceholder.getChildren().add(bookingTableView.getRoot());
        venueListViewPlaceholder.getChildren().add(venueListView.getRoot());
    }

    void updateVenueName() {
        currentVenueName.setText("Viewing bookings for venue: " + bookings.get(0).getVenueName());
    }

}
