package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommunicationChannelTest {

    @Test
    public void testEnumValuesExist() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            assertNotNull(channel);
        }
    }

    @Test
    public void testValueOfStringConversion() {
        assertEquals(Person.CommunicationChannel.EMAIL, Person.CommunicationChannel.valueOf("EMAIL"));
        assertEquals(Person.CommunicationChannel.SMS, Person.CommunicationChannel.valueOf("SMS"));
        assertEquals(Person.CommunicationChannel.WHATSAPP, Person.CommunicationChannel.valueOf("WHATSAPP"));
        assertThrows(IllegalArgumentException.class, () -> {
            Person.CommunicationChannel.valueOf("INVALID_CHANNEL");
        });
    }

    @Test
    public void testToStringOutput() {
        assertEquals("Phone", Person.CommunicationChannel.PHONE.toString());
        assertEquals("Email", Person.CommunicationChannel.EMAIL.toString());
        assertEquals("Sms", Person.CommunicationChannel.SMS.toString());
        assertEquals("Whatsapp", Person.CommunicationChannel.WHATSAPP.toString());
        assertEquals("Telegram", Person.CommunicationChannel.TELEGRAM.toString());
    }

    @Test
    public void testSwitchLogic() {
        Person.CommunicationChannel channel = Person.CommunicationChannel.SMS;
        String message;
        switch (channel) {
        case PHONE -> message = "Call user";
        case EMAIL -> message = "Send email";
        case SMS -> message = "Send text";
        case WHATSAPP -> message = "Send WhatsApp message";
        case TELEGRAM -> message = "Send Telegram message";
        default -> message = "Unknown channel";
        }
        assertEquals("Send text", message);
    }

    @Test
    public void testAllChannelsLoop() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            assertTrue(channel.name().length() > 0);
        }
    }

    @Test
    public void testOrdinalValues() {
        assertEquals(0, Person.CommunicationChannel.PHONE.ordinal());
        assertEquals(1, Person.CommunicationChannel.EMAIL.ordinal());
        assertEquals(2, Person.CommunicationChannel.SMS.ordinal());
        assertEquals(3, Person.CommunicationChannel.WHATSAPP.ordinal());
        assertEquals(4, Person.CommunicationChannel.TELEGRAM.ordinal());
    }

    @Test
    public void testCompareTo() {
        assertTrue(Person.CommunicationChannel.PHONE.compareTo(Person.CommunicationChannel.EMAIL) < 0);
        assertTrue(Person.CommunicationChannel.TELEGRAM.compareTo(Person.CommunicationChannel.SMS) > 0);
        assertEquals(0, Person.CommunicationChannel.WHATSAPP.compareTo(Person.CommunicationChannel.WHATSAPP));
    }

    @Test
    public void testEquals() {
        Person.CommunicationChannel channel1 = Person.CommunicationChannel.SMS;
        Person.CommunicationChannel channel2 = Person.CommunicationChannel.SMS;
        Person.CommunicationChannel channel3 = Person.CommunicationChannel.EMAIL;
        assertTrue(channel1.equals(channel2));
        assertFalse(channel1.equals(channel3));
        assertFalse(channel1.equals(null));
        assertFalse(channel1.equals("SMS"));
    }

    @Test
    public void testNameMethod() {
        assertEquals("PHONE", Person.CommunicationChannel.PHONE.name());
        assertEquals("EMAIL", Person.CommunicationChannel.EMAIL.name());
        assertEquals("SMS", Person.CommunicationChannel.SMS.name());
        assertEquals("WHATSAPP", Person.CommunicationChannel.WHATSAPP.name());
        assertEquals("TELEGRAM", Person.CommunicationChannel.TELEGRAM.name());
    }

    @Test
    public void testValuesIteration() {
        Person.CommunicationChannel[] channels = Person.CommunicationChannel.values();
        assertEquals(5, channels.length);
        boolean phoneFound = false;
        boolean emailFound = false;
        boolean smsFound = false;
        boolean whatsappFound = false;
        boolean telegramFound = false;
        for (Person.CommunicationChannel channel : channels) {
            switch (channel) {
            case PHONE -> phoneFound = true;
            case EMAIL -> emailFound = true;
            case SMS -> smsFound = true;
            case WHATSAPP -> whatsappFound = true;
            case TELEGRAM -> telegramFound = true;
            default -> throw new AssertionError("Unknown communication channel: " + channel);
            }
        }
        assertTrue(phoneFound && emailFound && smsFound && whatsappFound && telegramFound);
    }

    @Test
    public void testInvalidOrdinalAccess() {
        Person.CommunicationChannel[] channels = Person.CommunicationChannel.values();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Person.CommunicationChannel invalid = channels[5];
        });
    }

    @Test
    public void testSwitchAllChannels() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            String action;
            switch (channel) {
            case PHONE -> action = "Call";
            case EMAIL -> action = "Email";
            case SMS -> action = "Text";
            case WHATSAPP -> action = "WhatsApp";
            case TELEGRAM -> action = "Telegram";
            default -> action = "Unknown";
            }
            assertNotNull(action);
            assertTrue(action.length() > 0);
        }
    }

    @Test
    public void testHashCodeConsistency() {
        Person.CommunicationChannel channel1 = Person.CommunicationChannel.WHATSAPP;
        Person.CommunicationChannel channel2 = Person.CommunicationChannel.WHATSAPP;
        Person.CommunicationChannel channel3 = Person.CommunicationChannel.SMS;
        assertEquals(channel1.hashCode(), channel2.hashCode());
        assertFalse(channel1.hashCode() == channel3.hashCode());
    }

    @Test
    public void testValueOfAllNames() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            assertEquals(channel, Person.CommunicationChannel.valueOf(channel.name()));
        }
    }

    @Test
    public void testEqualsProperties() {
        Person.CommunicationChannel a = Person.CommunicationChannel.PHONE;
        Person.CommunicationChannel b = Person.CommunicationChannel.PHONE;
        Person.CommunicationChannel c = Person.CommunicationChannel.PHONE;
        assertTrue(a.equals(a));
        assertTrue(a.equals(b) && b.equals(a));
        assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
    }

    @Test
    public void testUniqueOrdinals() {
        Person.CommunicationChannel[] channels = Person.CommunicationChannel.values();
        for (int i = 0; i < channels.length; i++) {
            for (int j = i + 1; j < channels.length; j++) {
                assertFalse(channels[i].ordinal() == channels[j].ordinal());
            }
        }
    }

    @Test
    public void testToStringMatchesTitleCase() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            String expected = channel.name().charAt(0) + channel.name().substring(1).toLowerCase();
            assertEquals(expected, channel.toString());
        }
    }


    @Test
    public void testEnhancedForLoopIteration() {
        int count = 0;
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            assertNotNull(channel);
            count++;
        }
        assertEquals(5, count);
    }

    @Test
    public void testSwitchDefaultNeverTriggered() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            String result;
            switch (channel) {
            case PHONE -> result = "phone";
            case EMAIL -> result = "email";
            case SMS -> result = "sms";
            case WHATSAPP -> result = "whatsapp";
            case TELEGRAM -> result = "telegram";
            default -> result = "default";
            }
            assertFalse(result.equals("default"));
        }
    }

    @Test
    public void testCompareToSelfIsZero() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            assertEquals(0, channel.compareTo(channel));
        }
    }

    @Test
    public void testHashCodeUnique() {
        Person.CommunicationChannel[] channels = Person.CommunicationChannel.values();
        for (int i = 0; i < channels.length; i++) {
            for (int j = i + 1; j < channels.length; j++) {
                assertFalse(channels[i].hashCode() == channels[j].hashCode());
            }
        }
    }

    @Test
    public void testOrdinalAscending() {
        Person.CommunicationChannel[] channels = Person.CommunicationChannel.values();
        for (int i = 1; i < channels.length; i++) {
            assertTrue(channels[i - 1].ordinal() < channels[i].ordinal());
        }
    }

    @Test
    public void testValueOfEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> {
            Person.CommunicationChannel.valueOf("");
        });
    }

    @Test
    public void testValueOfLowerCase() {
        assertThrows(IllegalArgumentException.class, () -> {
            Person.CommunicationChannel.valueOf("email");
        });
    }

    @Test
    public void testRepeatedNameConsistency() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            String first = channel.name();
            String second = channel.name();
            assertEquals(first, second);
        }
    }

    @Test
    public void testRepeatedToStringConsistency() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            String first = channel.toString();
            String second = channel.toString();
            assertEquals(first, second);
        }
    }

    @Test
    public void testReverseOrderIteration() {
        Person.CommunicationChannel[] channels = Person.CommunicationChannel.values();
        for (int i = channels.length - 1; i >= 0; i--) {
            assertNotNull(channels[i]);
        }
    }

    @Test
    public void testHashCodeConsistencyRepeated() {
        for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
            int first = channel.hashCode();
            int second = channel.hashCode();
            assertEquals(first, second);
        }
    }

    @Test
    public void testSwitchMultipleTimes() {
        for (int i = 0; i < 3; i++) {
            for (Person.CommunicationChannel channel : Person.CommunicationChannel.values()) {
                String result;
                switch (channel) {
                case PHONE -> result = "call";
                case EMAIL -> result = "email";
                case SMS -> result = "sms";
                case WHATSAPP -> result = "whatsapp";
                case TELEGRAM -> result = "telegram";
                default -> result = "default";
                }
                assertNotNull(result);
                assertFalse(result.equals("default"));
            }
        }
    }

    @Test
    public void testOrdinalNameMapping() {
        Person.CommunicationChannel[] channels = Person.CommunicationChannel.values();
        for (Person.CommunicationChannel channel : channels) {
            assertEquals(channel, Person.CommunicationChannel.values()[channel.ordinal()]);
            String expected = channel.name().charAt(0) + channel.name().substring(1).toLowerCase();
            assertEquals(expected, channel.toString());
        }
    }
}



