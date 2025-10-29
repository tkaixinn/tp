package seedu.address.model.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Loads and provides access to supported languages from a JSON file.
 */
public class SupportedLanguages {

    private static final String LANGUAGES_JSON_PATH = "/greetings.json";
    private static final Map<String, String> SUPPORTED_LANGUAGES = loadLanguages();

    private static Map<String, String> loadLanguages() {
        try (InputStream inputStream = SupportedLanguages.class.getResourceAsStream(LANGUAGES_JSON_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("languages.json not found in resources.");
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load supported languages: " + e.getMessage(), e);
        }
    }

    /**
     * Checks whether the given language is supported.
     * <p>
     * The input language string is normalized by converting it to lowercase
     * and replacing spaces with underscores before checking against the
     * {@code SUPPORTED_LANGUAGES} map.
     * </p>
     *
     * @param language the language to check; may be {@code null}
     * @return {@code true} if the normalized language exists in {@code SUPPORTED_LANGUAGES},
     *         {@code false} otherwise or if {@code language} is {@code null}
     */
    public static boolean isSupported(String language) {
        if (language == null) return false;
        String key = language.toLowerCase();
        return SUPPORTED_LANGUAGES.containsKey(key);
    }

    public static Set<String> getAllLanguages() {
        return Collections.unmodifiableSet(SUPPORTED_LANGUAGES.keySet());
    }
}

