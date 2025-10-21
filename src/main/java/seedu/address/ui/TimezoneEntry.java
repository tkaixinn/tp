package seedu.address.ui;

/**
 * Represents a time zone entry for display in a UI table.
 * <p>
 * Each entry consists of a {@code region} (typically the city or region name)
 * and its corresponding UTC {@code offset} in the format "UTC±HH:MM".
 * </p>
 */
public class TimezoneEntry {
    private final String region;
    private final String offset;

    /**
     * Constructs a new {@code TimezoneEntry} with the specified region and offset.
     *
     * @param region the name of the city or region for this time zone
     * @param offset the UTC offset in the format "UTC±HH:MM"
     */
    public TimezoneEntry(String region, String offset) {
        this.region = region;
        this.offset = offset;
    }

    public String getRegion() {
        return region;
    }

    public String getOffset() {
        return offset;
    }

}
