package server.playable;

import engine.behavior.Playable;
import engine.component.Player;

import java.util.List;

public interface GameRoom<PlayerType extends Player, MoveType, GameState> extends Playable<MoveType, GameState> {
    void add(PlayerType player) throws Exception;
    List<PlayerType> getPlayers();
    boolean canStart();

}
