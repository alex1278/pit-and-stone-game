package engine.error;

import engine.behavior.Playable;

/**
 * PlayableStateInvalid extends Error and is intended
 * to be thrown when an implementing instance of {@link Playable} is unable
 * to start
 */
public class PlayableStateInvalid extends Exception {

    private final String defaultMessage = "The game cannot be played";

    public PlayableStateInvalid() { }

    public PlayableStateInvalid(String reason) {
        super(reason);
    }

    @Override
    public String getMessage() {
        return formattedMessage();
    }

    @Override
    public String toString() {
        return formattedMessage();
    }

    private String formattedMessage() {
        String message = super.getMessage();

        if (message == null || message.trim().isEmpty()) {
            return defaultMessage;
        }

        return String.format("%s \nReason: %s", defaultMessage, message);
    }

}
