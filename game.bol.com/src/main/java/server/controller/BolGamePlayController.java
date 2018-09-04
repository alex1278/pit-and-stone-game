package server.controller;

import com.github.javafaker.Faker;
import engine.event.Move;
import engine.model.GameState;
import io.javalin.websocket.WsSession;
import server.Routes;
import server.model.Message;
import server.model.SendablePlayer;
import server.playable.BolGameRoom;
import server.playable.GameRoom;
import server.service.RoomManager;
import server.util.JsonHandler;

public class BolGamePlayController extends JsonHandler implements SocketController {

    private final RoomManager<BolGameRoom> manager;
    private final Faker faker;

    public BolGamePlayController(RoomManager<BolGameRoom> manager) {
        super();
        this.manager = manager;
        this.faker = new Faker();
    }

    /**
     * ConnectionHandler
     *
     * @param wsSession
     * @throws Exception
     */
    @Override
    public void handle(WsSession wsSession) {
        try {
            GameRoom<SendablePlayer, Move, GameState> room = getRoom(wsSession);
            room.add(new SendablePlayer(generateName(), wsSession));
            if (room.canStart()) {
                messagePlayers(room, new Message<>(room.start()));
            } else {
                messagePlayers(room, new Message<>(room.currentState()));
            }
        } catch (Exception ex) {
            wsSession.send(errorMessage(ex));
        }
    }

    /**
     * MessageHandler
     *
     * @param wsSession
     * @throws Exception
     */
    @Override
    public void handle(WsSession wsSession, int status, String message) throws Exception {
        try {
            GameRoom<SendablePlayer, Move, GameState> room = getRoom(wsSession);
            Move move = deserialize(message, Move.class);
            messagePlayers(room, new Message<>(room.play(move)));
        } catch (Exception ex) {
            wsSession.send(errorMessage(ex));
        }
    }


    /**
     * CloseHandler will end the game if player leaves
     *
     * @param wsSession
     * @throws Exception
     */
    @Override
    public void handle(WsSession wsSession, String message) throws Exception {
        try {
            GameRoom<SendablePlayer, Move, GameState> room = getRoom(wsSession);
            messagePlayers(room, new Message<>(room.end()));
        } catch (Exception ex) {
            wsSession.send(errorMessage(ex));
        }
    }

    private String extractRoomId(WsSession session) {
        return session.pathParam(Routes.ROOM_ID_PATH_PARAM);
    }

    private GameRoom<SendablePlayer, Move, GameState> getRoom(WsSession session) throws Exception {
        String roomId = extractRoomId(session);

        GameRoom<SendablePlayer, Move, GameState> room = manager.getGameRoom(roomId);
        if (room == null) {
            throw new Exception(String.format("Game with id %s does not exist", roomId));
        }
        return room;
    }

    private void messagePlayers(GameRoom<SendablePlayer, Move, GameState> room, Message message) {
        String msg = serialize(message);
        room.getPlayers()
            .forEach(p -> p.send(msg));
    }

    private String errorMessage(Exception ex) {
        Message msg = new Message(true, ex.getMessage());
        return serialize(msg);
    }

    private String generateName() {
        return faker.funnyName().name();
    }
}
