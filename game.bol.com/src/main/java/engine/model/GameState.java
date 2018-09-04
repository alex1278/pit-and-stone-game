package engine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * GameState is a simple readonly class
 * that is used to reflect the current state of an active game
 */
public class GameState {

    public final List<PlayerState> players;
    public final String currentPlayerId;
    public final int roundNumber;
    public final List<PitState> pits;
    public final String winnerId;
    public final ActivityState activityState;

    public GameState(List<PlayerState> players, String currentPlayerId, int roundNumber, List<PitState> pits, String winnerId, ActivityState activityState) {
        this.players = players;
        this.currentPlayerId = currentPlayerId;
        this.roundNumber = roundNumber;
        this.pits = pits;
        this.winnerId = winnerId;
        this.activityState = activityState;
    }

    /**
     * Initialize game state with players and otherwise default "zero state"
     * @param players
     */
    public GameState(List<PlayerState> players) {
        this.players = players;
        this.activityState = ActivityState.NOT_STARTED;
        this.roundNumber = 0;
        this.pits = new ArrayList<>();
        this.winnerId = null;
        this.currentPlayerId = null;
    }
}
