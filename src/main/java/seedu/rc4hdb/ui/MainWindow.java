package seedu.rc4hdb.ui;

import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.rc4hdb.commons.core.GuiSettings;
import seedu.rc4hdb.commons.core.LogsCenter;
import seedu.rc4hdb.logic.Logic;
import seedu.rc4hdb.logic.commands.CommandResult;
import seedu.rc4hdb.logic.commands.exceptions.CommandException;
import seedu.rc4hdb.logic.parser.exceptions.ParseException;
import seedu.rc4hdb.model.venues.booking.Booking;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container

    private ResidentTableView residentTableView;
    private BookingTableView bookingTableView;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private CommandBox commandBoxRegion;
    private CurrentWorkingFileFooter statusBarFooter;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem commandBoxRedirect;

    @FXML
    private TabPane tableViewPane;

    @FXML
    private Tab residentTab;

    @FXML
    private Tab venueTab;

    @FXML
    private VBox venueTabContainer;

    @FXML
    private Label venueName;

    @FXML
    private Label allVenue;

    @FXML
    private StackPane residentTableViewPlaceholder;

    @FXML
    private StackPane bookingTableViewPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Add listeners to fields to be listened to
        this.logic.getObservableFolderPath().addListener(getFileChangeListener());
        this.logic.getObservableBookings().addListener(getBookingChangeListener());
        this.logic.getObservableBookings().addListener(getViewingVenueChangeListener());
        this.logic.getVisibleFields().addListener(updateVisibleFieldsOnChange());
        this.logic.getHiddenFields().addListener(updateHiddenFieldsOnChange());
        this.logic.getObservableFolderPath().addListener(getFileChangeListener());

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setTabLabels();
        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(commandBoxRedirect, KeyCombination.valueOf("F3"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        residentTableView = new ResidentTableView(logic.getFilteredResidentList(),
                logic.getVisibleFields(),
                logic.getHiddenFields());
        residentTableViewPlaceholder.getChildren().add(residentTableView.getRoot());

        bookingTableView = new BookingTableView(logic.getObservableBookings());
        bookingTableViewPlaceholder.getChildren().addAll(bookingTableView.getRoot());

        try {
            venueName.setText("Currently Viewing: " + logic.getObservableBookings().get(0).getVenueName().toString());
        } catch (IndexOutOfBoundsException e) {
            venueName.setText("Currently Viewing: " + logic.getObservableVenues().get(0).toString());
        }

        allVenue.setText("All venues in RC4HDB: " + logic.getObservableVenues().toString());

        residentTab.setContent(residentTableViewPlaceholder);
        venueTab.setContent(venueTabContainer);


        tableViewPane.getTabs().addAll(residentTab, venueTab);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        statusBarFooter = new CurrentWorkingFileFooter(logic.getObservableFolderPath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxRegion = commandBox;
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Redirects the focus onto the text field of the command box.
     */
    public void handleRedirect() {
        commandBoxRegion.focus();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public ResidentTableView getResidentTablePanel() {
        return residentTableView;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.rc4hdb.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Returns a listener which propagates the changes in the visibleFields list in Model
     * (and hence Logic) to the corresponding observable list in ResidentTableView.
     */
    private ListChangeListener<String> updateVisibleFieldsOnChange() {
        return c -> residentTableView.setVisibleFields(logic.getVisibleFields());
    }

    /**
     * Returns a listener which propagates the changes in the hiddenFields list in Model
     * (and hence Logic) to the corresponding observable list in ResidentTableView.
     */
    private ListChangeListener<String> updateHiddenFieldsOnChange() {
        // Update the observable field list within the logic attribute
        return c -> residentTableView.setHiddenFields(logic.getHiddenFields());
    }

    private ChangeListener<Path> getFileChangeListener() {
        return (observableValue, oldValue, newValue) ->
                statusBarFooter.updateFilePath(newValue);
    }

    private ListChangeListener<Booking> getBookingChangeListener() {
        return c -> bookingTableView.updateTable(logic.getObservableBookings());
    }

    private ListChangeListener<Booking> getViewingVenueChangeListener() {
        return c -> venueName.setText(
                "Currently Viewing: "
                        +
                logic.getObservableBookings().get(0).getVenueName().toString());
    }

    private void setTabLabels() {
        this.residentTab.setText("Residents");
        this.venueTab.setText("Bookings");
    }

}
