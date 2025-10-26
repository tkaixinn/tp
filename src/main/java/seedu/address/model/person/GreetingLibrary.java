package seedu.address.model.person;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class GreetingLibrary {
    private static Map<String, String> greetings;

    static {
        try (InputStream in = GreetingLibrary.class.getResourceAsStream("/data/greetings.json")) {
            ObjectMapper mapper = new ObjectMapper();
            // Specify the generic type safely
            greetings = mapper.readValue(in, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            System.err.println("Failed to load greetings.json: " + e.getMessage());
            greetings = Map.of(); // fallback to empty map
        }
    }

    public static String getGreeting(String language) {
        if (language == null) return "(No greeting available)";
        String greeting = greetings.get(language.toLowerCase());
        return greeting != null ? greeting : "(No greeting available)";
    }
}


