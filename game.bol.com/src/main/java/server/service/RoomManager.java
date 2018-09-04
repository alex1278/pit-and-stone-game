package server.service;

import engine.component.Player;
import org.jetbrains.annotations.Nullable;
import server.model.RoomSummary;
import server.playable.GameRoom;

import java.util.List;

public interface RoomManager<Room extends GameRoom> {
    @Nullable
    Room getGameRoom(String id);
    RoomSummary createRoom();
    List<RoomSummary> getRooms();
}
