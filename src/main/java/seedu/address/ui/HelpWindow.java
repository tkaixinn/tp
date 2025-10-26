package seedu.address.ui;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
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

    @FXML
    private TableView<CountryEntry> countryTableView;

    @FXML
    private TableColumn<CountryEntry, String> countryNameColumn;

    @FXML
    private TableColumn<CountryEntry, String> countryCodeColumn;

    @FXML
    private TableView<TimezoneEntry> timezoneTable;

    @FXML
    private TableColumn<TimezoneEntry, String> regionColumn;

    @FXML
    private TableColumn<TimezoneEntry, String> offsetColumn;

    @FXML
    private TextField countrySearchField;

    @FXML
    private TableView<LanguageEntry> languageTableView;

    @FXML
    private TableColumn<LanguageEntry, String> languageNameColumn;

    @FXML
    private TableColumn<LanguageEntry, String> languageCodeColumn;

    @FXML
    private TableColumn<LanguageEntry, String> languageCountriesColumn;

    @FXML
    private TextField languageSearchField;

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

    private void initializeTimezones() {
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offset"));

        List<TimezoneEntry> zones = getAllZones();
        timezoneTable.getItems().addAll(zones);
    }

    /**
     * Generates a list of all available time zones with their corresponding city/region
     * and UTC offset, sorted by offset (from lowest to highest) and then by city name.
     * <p>
     * The UTC offset is displayed in the format "UTC±HH:MM". The special "Z" offset
     * (representing UTC) is normalized to "+00:00" for display purposes.
     * </p>
     *
     * @return a {@link List} of {@link TimezoneEntry} objects, each representing
     *         a time zone with its city/region and formatted UTC offset.
     */
    private List<TimezoneEntry> getAllZones() {
        List<TimezoneEntry> list = new ArrayList<>();
        ZonedDateTime now = ZonedDateTime.now();

        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            ZoneId zone = ZoneId.of(zoneId);
            ZoneOffset offset = now.withZoneSameInstant(zone).getOffset();

            String offsetString = offset.getId().equals("Z") ? "+00:00" : offset.getId();

            String [] parts = zoneId.split("/");
            String city = parts.length > 1 ? parts[parts.length - 1].replace("_", " ") : zoneId;
            list.add(new TimezoneEntry(city, "UTC" + offsetString));
        }

        list.sort(Comparator
                .comparingInt((TimezoneEntry entry) -> {
                    String offsetStr = entry.getOffset().substring(3);
                    if (offsetStr.equals("Z")) {
                        offsetStr = "00:00";
                    }
                    String [] hms = offsetStr.split(":");
                    int hours = Integer.parseInt(hms[0]);
                    int minutes = Integer.parseInt(hms[1]);
                    return hours * 60 + (hours < 0 ? -minutes : minutes);
                }).thenComparing(TimezoneEntry::getRegion));
        return list;
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
                new CommandEntry("Add", "add name:NAME phone:PHONE email:EMAIL address:ADDRESS "
                        + "country:COUNTRY channel:CHANNEL offset:OFFSET " + "[note:NOTE] [tag:TAG] [lang:LANG]…\n"
                        + "e.g., add name:James Ho phone:22224444 email:jamesho@example.com "
                        + "address:123, Clementi Rd, 1234665 country:Singapore tag:friend tag:colleague offset:+08:00"
                        + "lang:english"),
                new CommandEntry("Clear", "clear"),
                new CommandEntry("Delete", "delete INDEX\n e.g., delete 3"),
                new CommandEntry("Edit", "edit INDEX [name:NAME] [phone:PHONE] [email:EMAIL] "
                        + "[address:ADDRESS] [country:COUNTRY] [channel:CHANNEL] [tag:TAG]… [offset: OFFSET]\n"
                        + "e.g., edit 2 name:James Lee email:jameslee@example.com"),
                new CommandEntry("Add cultural note",
                        "addnote name:NAME note:NOTE\n e.g. addnote name:John note:does not eat beef"),
                new CommandEntry("Find", "find KEYWORD [MORE_KEYWORDS]\n e.g., find James Jake"),
                new CommandEntry("Find tag", "findtag TAG\n e.g. findtag friends"),
                new CommandEntry("Find country", "findcountry COUNTRY\n e.g. findcountry Singapore"),
                new CommandEntry("Sort by country", "sortcountry"),
                new CommandEntry("Sort by name", "sortname"),
                new CommandEntry("Sort by date added", "sortdate"),
                new CommandEntry("List", "list"),
                new CommandEntry("Archive", "archive INDEX"),
                new CommandEntry("Archive list", "archivelist"),
                new CommandEntry("Unarchive", "unarchive INDEX"),
                new CommandEntry("Help", "help"));

        countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        List<CountryEntry> countries = new ArrayList<>();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        for (String regionCode : phoneUtil.getSupportedRegions()) {
            String countryName = new Locale("", regionCode).getDisplayCountry(Locale.ENGLISH);
            if (!countryName.isEmpty()) {
                String phoneCode = "+" + phoneUtil.getCountryCodeForRegion(regionCode);
                countries.add(new CountryEntry(countryName, phoneCode));
            }
        }

        countries.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
        FilteredList<CountryEntry> filteredCountries = new FilteredList<>(FXCollections.observableArrayList(countries),
                p -> true);
        countryTableView.setItems(filteredCountries);

        countrySearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String lower = newValue.toLowerCase();
            filteredCountries.setPredicate(country -> {
                if (lower == null || lower.isBlank()) {
                    return true;
                }
                return country.getName().toLowerCase().contains(lower)
                        || country.getCode().toLowerCase().contains(lower);
            });
        });

        initializeTimezones();

        Map<String, Set<String>> languageToCountries = new HashMap<>();

        for (Locale locale : Locale.getAvailableLocales()) {
            String langCode = locale.getLanguage();
            String langName = locale.getDisplayLanguage(Locale.ENGLISH);
            String country = locale.getDisplayCountry(Locale.ENGLISH);

            if (langCode == null || langCode.isEmpty() || langName == null || langName.isEmpty()) {
                continue;
            }

            if (country != null && !country.isEmpty()) {
                languageToCountries.computeIfAbsent(langCode, k -> new TreeSet<>()).add(country);
            }
        }

        List<LanguageEntry> languages = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : languageToCountries.entrySet()) {
            String code = entry.getKey();
            String name = new Locale(code).getDisplayLanguage(Locale.ENGLISH);
            String countriesUsed = String.join(", ", entry.getValue());
            if (name != null && !name.isEmpty() && code != null && !code.isEmpty()) {
                languages.add(new LanguageEntry(name, code, countriesUsed));
            }
        }

        languages.sort(Comparator.comparing(LanguageEntry::getName));

        languageNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        languageCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        languageCountriesColumn.setCellValueFactory(new PropertyValueFactory<>("countriesUsed"));

        languageCountriesColumn.setCellFactory(col ->
                new TableCell<LanguageEntry, String>() {
                    private final StackPane pane = new StackPane();
                    private final Label label = new Label();

                    {
                        label.setWrapText(true);
                        pane.getChildren().add(label);
                        pane.setPrefHeight(80);
                    }

                    @Override
                    protected void updateItem(String countries, boolean empty) {
                        super.updateItem(countries, empty);
                        if (empty || countries == null) {
                            setGraphic(null);
                        } else {
                            label.setText(countries);
                            ScrollPane sp = new ScrollPane(pane);
                            sp.setPrefHeight(80);
                            sp.setFitToWidth(true);
                            sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                            sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                            setGraphic(sp);
                        }
                    }
                });


        FilteredList<LanguageEntry> filteredLanguages = new FilteredList<>(FXCollections.observableArrayList(languages),
                p -> true);
        languageTableView.setItems(filteredLanguages);

        languageSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String lower = newValue == null ? "" : newValue.toLowerCase();
            filteredLanguages.setPredicate(lang -> lang.getName().toLowerCase().contains(lower)
                    || lang.getCode().toLowerCase().contains(lower)
                    || lang.getCountriesUsed().toLowerCase().contains(lower));
        });
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException
     *                               <ul>
     *                               <li>
     *                               if this method is called on a thread other than
     *                               the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or
     *                               layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
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
