package engine.event;

public class Move {
    public final String playerId;
    public final String stoneId;
    public final String fromPitId;
    public final String toPitId;


    public Move(String playerId, String stoneId, String fromPitId, String toPitId) {
        this.playerId = playerId;
        this.stoneId = stoneId;
        this.fromPitId = fromPitId;
        this.toPitId = toPitId;
    }
}
