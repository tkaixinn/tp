package seedu.address.model.util;

import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A utility class that loads a mapping of language codes to greeting messages
 * from the {@code greetings.json} resource file. The greetings are stored in a
 * static map and can be retrieved using their language code.
 *
 * If a greeting for the requested language is not found, a default
 * greeting {@code "Hello!"} is returned.</p>
 */
public class GreetingMap {
    /**
     * Returns the greeting message associated with the given language code.
     *
     * @param language The language code used to look up the greeting. It is case-insensitive.
     * @return The greeting message for the given language, or {@code "Hello!"} if the language is not found.
     */
    private static Map<String, String> greetings;

    static {
        try (InputStream is = GreetingMap.class.getResourceAsStream("/greetings.json")) {
            ObjectMapper mapper = new ObjectMapper();
            greetings = mapper.readValue(is, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getGreeting(String language) {
        return greetings.getOrDefault(language.toLowerCase(), "Hello!");
    }
}
