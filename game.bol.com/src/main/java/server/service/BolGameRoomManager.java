package server.service;

import org.hashids.Hashids;
import server.Routes;
import server.model.RoomSummary;
import server.playable.BolGameRoom;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BolGameRoomManager implements RoomManager<BolGameRoom> {

    private final Map<String, BolGameRoom> rooms = new ConcurrentHashMap<>();
    private final String salt = "bolgame.mngr";

    @Override
    public BolGameRoom getGameRoom(String id) {
       return rooms.get(id);
    }

    @Override
    public RoomSummary createRoom() {
        String roomId = createId();
        BolGameRoom room = new BolGameRoom(roomId);
        rooms.putIfAbsent(roomId, room);
        return summary(room);
    }

    @Override
    public List<RoomSummary> getRooms() {
        return rooms.values()
                .stream()
                .map(this::summary)
                .collect(Collectors.toList());
    }

    private String createId() {
        return new Hashids(salt, 11).encode(rooms.size() + 1).toLowerCase();
    }

    private RoomSummary summary(BolGameRoom room) {
        return new RoomSummary(room.id, Routes.GAME + "/" + room.id, room.getPlayers());
    }
}
