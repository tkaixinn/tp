package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a single command entry in the HelpWindow table.
 * Each entry has:
 * - The command action (e.g., "Add", "Delete")
 * - The format and example usage
 */
public class CommandEntry {
    private final SimpleStringProperty action;
    private final SimpleStringProperty format;

    /**
     * Constructs a CommandEntry.
     *
     * @param action The name of the command (e.g., "Add").
     * @param format The format and example usage of the command.
     */
    public CommandEntry(String action, String format) {
        this.action = new SimpleStringProperty(action);
        this.format = new SimpleStringProperty(format);
    }

    /**
     * Returns the action of this command entry.
     *
     * @return command action as a String
     */
    public String getAction() {
        return action.get();
    }


    /**
     * Returns the format and example usage of this command entry.
     *
     * @return format and examples as a String
     */
    public String getFormat() {
        return format.get();
    }
}
