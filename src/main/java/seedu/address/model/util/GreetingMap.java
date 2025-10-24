package seedu.address.model.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public class GreetingMap {

    private static final Map<String, String> greetings;

    static {
        Map<String, String> tempMap;
        try (InputStream is = GreetingMap.class.getResourceAsStream("/greetings.json")) {
            ObjectMapper mapper = new ObjectMapper();
            tempMap = mapper.readValue(is, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            tempMap = Collections.emptyMap();
        }
        greetings = tempMap;
    }

    public static String getGreeting(String language) {
        if (language == null) {
            return "Hello!";
        }
        return greetings.getOrDefault(language.toLowerCase(), "Hello!");
    }
}

