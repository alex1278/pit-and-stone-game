package engine.utility;

import engine.component.Pit;
import engine.component.Player;
import engine.component.Stone;

public class Messages {

    public static final String GAME_IS_OVER = "Game has ended, play cannot continue";
    public static final String GAME_ALREADY_STARTED = "The game has already started";
    public static final String CANNOT_MOVE_STONE_FROM_OPPONENT_PIT = "You cannot move stones from your opponents pit";

    public static String playerCountInvalid(int requiredCount, int numPlayers) {
        return String.format("Unable to start as the game requires %d players to play, but only %d present", requiredCount, numPlayers);
    }

    public static String playerExists(Player player) {
        return String.format("The playerId %s was already added to the game", player.name);
    }

    public static String stoneDoesNotExist(Stone stone) {
        return String.format("The stone %s is does not belong on the playing surface", stone);
    }

    public static String stoneDoesNotBelongToPit(Stone stone, Pit pit) {
        return String.format("The stone %s does not belong to pit %s", stone, pit);
    }

    public static String stoneDoesNotBelongToPit(String stoneId, Pit pit) {
        return String.format("The stone with id %s does not belong to pit %s", stoneId, pit);
    }

    public static String turnInProgress(Player player) {
        return String.format("Cannot update as the playerId %s is currently in progress with their turn", player.name);
    }

    public static String playerOutOfTurn(String playerId) {
        return String.format("Cannot play move as it is not playerId %s's turn", playerId);
    }

    public static String invalidFromPit(Pit valid, Pit provided) {
        return String.format("Turn has already started using put %s, you cannot move stones from %s", valid, provided);
    }

    public static String invalidTargetPit(Pit valid, Pit provided) {
        return String.format("You cannot move stone to pit %s, the next available pit is %s", provided, valid);
    }

    public static String cannotPlayInOpponentsLargePit(Pit pit) {
        return String.format("You cannot place your stone in pit %s as it is your opponent's large pit", pit);
    }
}
