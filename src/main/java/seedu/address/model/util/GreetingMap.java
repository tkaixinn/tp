package seedu.address.model.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

public class GreetingMap {

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
