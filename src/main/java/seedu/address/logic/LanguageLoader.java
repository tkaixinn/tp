package seedu.address.logic;

import java.io.InputStream;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.ui.LanguageEntry;

/**
 * Loads greetings from the JSON file and returns them as a list of {@code LanguageEntry}.
 */
public class LanguageLoader {

    /**
     * Loads all language entries from {@code /greetings.json}.
     *
     * @return an {@code ObservableList} of {@code LanguageEntry} objects; empty if file is missing or empty
     */
    public static ObservableList<LanguageEntry> loadLanguages() {
        InputStream is = LanguageLoader.class.getResourceAsStream("/greetings.json");
        ObservableList<LanguageEntry> list = FXCollections.observableArrayList();
        if (is == null) {
            return list;
        }

        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        String json = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1); // remove {}
        }

        for (String entry : json.split(",")) {
            String[] parts = entry.split(":", 2);
            if (parts.length == 2) {
                String name = parts[0].trim().replaceAll("^\"|\"$", "");
                String greeting = parts[1].trim().replaceAll("^\"|\"$", "");
                list.add(new LanguageEntry(capitalize(name), greeting));
            }
        }
        return list;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
