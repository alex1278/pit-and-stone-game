package server.model;

import engine.component.Player;

import java.util.List;

public class RoomSummary<T extends Player> {
    public final String id;
    public final String url;
    public final List<Player> players;


    public RoomSummary(String id, String url, List<Player> players) {
        this.id = id;
        this.url = url;
        this.players = players;
    }
}
