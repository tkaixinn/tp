package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;


/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TableView<CommandEntry> commandTableView;

    @FXML
    private TableColumn<CommandEntry, String> actionColumn;

    @FXML
    private TableColumn<CommandEntry, String> formatColumn;
    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Initializes the HelpWindow after its FXML is loaded.
     * Sets up the table columns and populates the command reference table.
     */
    @FXML
    public void initialize() {
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        formatColumn.setCellValueFactory(new PropertyValueFactory<>("format"));

        formatColumn.setCellFactory(column -> {
            return new javafx.scene.control.TableCell<CommandEntry, String>() {
                private final javafx.scene.text.Text text = new javafx.scene.text.Text();

                {
                    text.wrappingWidthProperty().bind(formatColumn.widthProperty().subtract(10));
                    setGraphic(text);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText(null);
                    } else {
                        text.setText(item);
                    }
                }
            };
        });

        commandTableView.setFixedCellSize(-1);

        commandTableView.getItems().addAll(
                new CommandEntry("Add", "add name: NAME phone: PHONE email: EMAIL address: ADDRESS "
                        + "channel: CHANNEL "
                        + "[country: COUNTRY] [tag: TAG]…\n"
                        + "e.g., add name: James Ho phone: 22224444 email: jamesho@example.com "
                        + "address: 123, Clementi Rd, 1234665 country: Singapore tag: friend tag: colleague"),
                new CommandEntry("Clear", "clear"),
                new CommandEntry("Delete", "delete INDEX\n e.g., delete 3"),
                new CommandEntry("Edit", "edit INDEX [name: NAME] [phone: PHONE] [email: EMAIL] "
                        +  "[address: ADDRESS] [channel: CHANNEL] [country: COUNTRY] [tag: TAG]…\n"
                        + "e.g., edit 2 name: James Lee email: jameslee@example.com"),
                new CommandEntry("Find", "find KEYWORD [MORE_KEYWORDS]\n e.g., find James Jake"),
                new CommandEntry("List", "list"),
                new CommandEntry("Help", "help")
        );
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
