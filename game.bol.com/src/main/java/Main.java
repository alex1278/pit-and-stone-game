import io.javalin.Javalin;
import server.Routes;
import server.controller.BolGamePlayController;
import server.controller.GameRoomController;
import server.controller.SocketController;
import server.playable.BolGameRoom;
import server.service.BolGameRoomManager;
import server.service.RoomManager;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .enableCorsForAllOrigins();

        // TODO: use di framework
        RoomManager<BolGameRoom> roomManager = new BolGameRoomManager();
        SocketController playController = new BolGamePlayController(roomManager);
        GameRoomController gameRoomController = new GameRoomController(roomManager);

        // WEB SOCKET
        app.ws("/games/:room-id", ws -> {
            ws.onConnect(playController);
            ws.onMessage(playController);
            ws.onClose(playController);
        });

        // RESTFUL ROUTES
        app.get(Routes.GAMES, gameRoomController::getRoomList);
        app.post(Routes.GAME, gameRoomController::createRoom);

        app.start(4000);
    }
}

