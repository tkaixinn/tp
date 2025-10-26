package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PreferredLanguageTest {

    @Test
    public void validLanguages_basic() {
        PreferredLanguage pl = new PreferredLanguage("English");
        assert pl.getPreferredLanguage().equals("English");

        pl = new PreferredLanguage("Mandarin");
        assert pl.getPreferredLanguage().equals("Mandarin");

        pl = new PreferredLanguage("Spanish");
        assert pl.getPreferredLanguage().equals("Spanish");

        pl = new PreferredLanguage("French");
        assert pl.getPreferredLanguage().equals("French");
    }

    @Test
    public void validLanguages_variousCases() {
        String[] languages = {
                "German", "Italian", "Portuguese", "Russian", "Arabic", "Hindi",
                "Japanese", "Korean", "Vietnamese", "Thai", "Bengali", "Tamil",
                "Urdu", "Malay", "Indonesian", "Filipino", "Swahili", "Zulu",
                "Xhosa", "Afrikaans", "Hebrew", "Greek", "Latin", "Norwegian",
                "Swedish", "Danish", "Finnish", "Polish", "Czech", "Slovak",
                "Hungarian", "Romanian", "Serbian", "Croatian", "Bosnian",
                "Bulgarian", "Ukrainian", "Macedonian", "Slovene", "Lithuanian",
                "Latvian", "Estonian", "Icelandic", "Irish", "Welsh", "Scottish Gaelic",
                "Basque", "Catalan", "Galician", "Esperanto", "Luxembourgish",
                "Maltese", "Maori", "Samoan", "Tongan", "Fijian", "Haitian Creole",
                "Hmong White", "Inuktitut", "Quechua", "Aymara", "Chamorro Guam",
                "Chamorro North", "Armenian Eastern", "Armenian Western", "Abkhaz",
                "Adyghe", "Afrikaans", "Akkadian", "Albanian GH", "Albanian TO", "Aleut"
        };

        for (String lang : languages) {
            PreferredLanguage pl = new PreferredLanguage(lang);
            assert pl.getPreferredLanguage().equals(lang);
            assert PreferredLanguage.isValidLanguage(lang);
        }
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
    public void validLanguages_spaces() {
        PreferredLanguage pl = new PreferredLanguage("Hmong White");
        assert pl.getPreferredLanguage().equals("Hmong White");

        pl = new PreferredLanguage("Scottish Gaelic");
        assert pl.getPreferredLanguage().equals("Scottish Gaelic");

        pl = new PreferredLanguage("Albanian TO");
        assert pl.getPreferredLanguage().equals("Albanian TO");
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
    public void mixedLanguages_loopTest() {
        String[] validLanguages = {
                "Zulu", "Xhosa", "Afrikaans", "Hebrew", "Greek", "Latin",
                "Norwegian", "Swedish", "Danish", "Finnish", "Polish", "Czech",
                "Slovak", "Hungarian", "Romanian", "Serbian", "Croatian", "Bosnian",
                "Bulgarian", "Ukrainian", "Macedonian", "Slovene", "Lithuanian",
                "Latvian", "Estonian", "Icelandic", "Irish", "Welsh", "Scottish Gaelic",
                "Basque", "Catalan", "Galician", "Esperanto", "Luxembourgish",
                "Maltese", "Maori", "Samoan", "Tongan", "Fijian", "Haitian Creole",
                "Hmong White", "Inuktitut", "Quechua", "Aymara"
        };

        for (String lang : validLanguages) {
            PreferredLanguage pl = new PreferredLanguage(lang);
            assertTrue(PreferredLanguage.isValidLanguage(lang));
            assert pl.getPreferredLanguage().equals(lang);
        }
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

