package engine.error;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayableStateInvalidErrorTest {

    private PlayableStateInvalid defaultError;
    private PlayableStateInvalid errorWithReason;


    private final String defaultMessage = "The game cannot be played";
    private final String mockReason = "The players are no longer available";

    private String expectedDefault;
    private String expectedMessage;

    @BeforeEach
    void setUp() {
        defaultError = new PlayableStateInvalid();
        errorWithReason = new PlayableStateInvalid(mockReason);

        expectedDefault = defaultMessage;
        expectedMessage = String.format("%s \nReason: %s", defaultMessage, mockReason);
    }

    @AfterEach
    void tearDown() {
        defaultError = null;
        errorWithReason = null;
    }

    @Test
    void get_message() {
        assertEquals(expectedDefault, defaultError.getMessage());
        assertEquals(expectedMessage, errorWithReason.getMessage());
    }

    @Test
    void to_string_method() {
        assertEquals(expectedDefault, defaultError.toString());
        assertEquals(expectedMessage, errorWithReason.toString());
    }
}