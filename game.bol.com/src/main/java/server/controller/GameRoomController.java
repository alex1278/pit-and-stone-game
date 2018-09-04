package server.controller;

import io.javalin.Context;
import server.playable.BolGameRoom;
import server.service.RoomManager;
import server.util.JsonHandler;

public class GameRoomController extends JsonHandler {

    private final RoomManager<BolGameRoom> manager;

    public GameRoomController(RoomManager<BolGameRoom> manager) {
        super();
        this.manager = manager;
    }

    public void getRoomList(Context ctx) {
        ctx.result(serialize(manager.getRooms()));
    }

    public void createRoom(Context ctx) {
        ctx.result(serialize(manager.createRoom()));
    }
}
