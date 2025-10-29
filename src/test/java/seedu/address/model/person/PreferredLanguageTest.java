package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import seedu.address.model.util.SupportedLanguages;

import org.junit.jupiter.api.Test;

public class PreferredLanguageTest {

    @Test
    public void validLanguages_basic() {
        PreferredLanguage pl = new PreferredLanguage("english");
        assert pl.getPreferredLanguage().equals("english");

        pl = new PreferredLanguage("spanish");
        assert pl.getPreferredLanguage().equals("spanish");

        pl = new PreferredLanguage("mandarin");
        assert pl.getPreferredLanguage().equals("mandarin");

        pl = new PreferredLanguage("french");
        assert pl.getPreferredLanguage().equals("french");
    }

    @Test
    public void invalidLanguages_basic() {
        String[] invalids = {"", "English123", "Fr@nçais!", "123", "Mandarin!"};

        for (String lang : invalids) {
            boolean exceptionThrown = false;
            try {
                new PreferredLanguage(lang);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assert exceptionThrown;
        }
    }

    @Test
    public void equalsMethod_tests() {
        PreferredLanguage pl1 = new PreferredLanguage("English");
        PreferredLanguage pl2 = new PreferredLanguage("english");
        PreferredLanguage pl3 = new PreferredLanguage("Spanish");

        assert pl1.equals(pl2);
        assert !pl1.equals(pl3);
        assert !pl1.equals(null);
        assert !pl1.equals("English");
    }

    @Test
    public void hashCode_tests() {
        PreferredLanguage pl1 = new PreferredLanguage("English");
        PreferredLanguage pl2 = new PreferredLanguage("english");

        assert pl1.hashCode() == pl2.hashCode();
        PreferredLanguage pl3 = new PreferredLanguage("Spanish");
        assert pl1.hashCode() != pl3.hashCode();
    }

    @Test
    public void isValidLanguage_variousCases() {
        assertTrue(PreferredLanguage.isValidLanguage("English"));
        assertTrue(PreferredLanguage.isValidLanguage("Mandarin"));
        assertTrue(PreferredLanguage.isValidLanguage("Hmong White"));
        assertFalse(PreferredLanguage.isValidLanguage(""));
        assertFalse(PreferredLanguage.isValidLanguage("123"));
        assertFalse(PreferredLanguage.isValidLanguage("English123"));
        assertFalse(PreferredLanguage.isValidLanguage("Fr@nçais!"));
        assertFalse(PreferredLanguage.isValidLanguage("Mandarin!"));
        assertTrue(PreferredLanguage.isValidLanguage("Scottish Gaelic"));
    }

    @Test
    public void invalidLanguages_loopTest() {
        String[] invalids = {
            "English123", "French!", "@Spanish", "123", "Mandarin1", "Zulu#", "Xhosa$",
            "Afr1kaans", "Hebrew123", "Greek!", "Latin@", "Norwegian1", "Swedish#",
            "Danish$", "Finnish123", "Polish!", "Czech@", "Slovak1", "Hungarian#", "Romanian$",
            "Serbian1", "Croatian@", "Bosnian!", "Bulgarian123", "Ukrainian#", "Macedonian$",
            "Slovene1", "Lithuanian@", "Latvian!", "Estonian123", "Icelandic#", "Irish$",
            "Welsh1", "Scottish Gaelic@", "Basque!", "Catalan123", "Galician#", "Esperanto$",
            "Luxembourgish1", "Maltese@", "Maori!", "Samoan123", "Tongan#", "Fijian$"
        };

        for (String lang : invalids) {
            boolean exceptionThrown = false;
            try {
                new PreferredLanguage(lang);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assertFalse(!exceptionThrown);
        }
    }
}

