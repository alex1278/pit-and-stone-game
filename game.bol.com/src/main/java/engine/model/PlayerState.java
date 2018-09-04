package engine.model;

import engine.component.Player;

public class PlayerState {
    public final String id;
    public final String name;
    public final int points;

    public PlayerState(Player player, int stoneCount) {
        id = player.id;
        name = player.name;
        points = stoneCount;
    }
}
